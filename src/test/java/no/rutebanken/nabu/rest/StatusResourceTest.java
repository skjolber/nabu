package no.rutebanken.nabu.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import no.rutebanken.nabu.domain.Status;
import no.rutebanken.nabu.domain.Status.Action;
import no.rutebanken.nabu.domain.Status.State;
import no.rutebanken.nabu.rest.domain.JobStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusResourceTest {

	@Test
	public void testGetStatusForProvider() throws Exception {
		long now = System.currentTimeMillis();
		List<Status> rawEvents = new ArrayList<>();
		// Job "b" -> OK
		rawEvents.add(new Status("filename2", 2l, null, Action.VALIDATION_LEVEL_1, State.PENDING, "b", new Date(now + 4), "ost"));
		rawEvents.add(new Status("filename2", 2l, 1l, Action.VALIDATION_LEVEL_1, State.STARTED, "b", new Date(now + 5), "pb"));
		rawEvents.add(new Status("filename2", 2l, 1l, Action.VALIDATION_LEVEL_1, State.OK, "b", new Date(now + 6), "pb"));

		// Job "a" -> FAILED
		rawEvents.add(new Status("filename1", 2l, null, Action.IMPORT, State.PENDING, "a", new Date(now + 1), "ost"));
		rawEvents.add(new Status("filename1", 2l, 2l, Action.IMPORT, State.STARTED, "a", new Date(now + 2), "ost"));
		rawEvents.add(new Status("filename1", 2l, 2l, Action.IMPORT, State.FAILED, "a", new Date(now + 3), "ost"));


		List<JobStatus> listStatus = new StatusResource().convert(rawEvents);

		Assert.assertNotNull(listStatus);
		Assert.assertEquals(2, listStatus.size());

		JobStatus a = listStatus.get(0);

		Assert.assertEquals("a", a.getCorrelationId());
		Assert.assertEquals(JobStatus.Action.IMPORT, a.getEvents().get(0).action);
		Assert.assertEquals(JobStatus.State.FAILED, a.getEndStatus());
		Assert.assertEquals(3, a.getEvents().size());
		Assert.assertEquals(new Date(now + 1), a.getFirstEvent());
		Assert.assertEquals(new Date(now + 3), a.getLastEvent());

		Assert.assertEquals(Long.valueOf(2), a.getEvents().get(1).chouetteJobId);

		JobStatus b = listStatus.get(1);

		Assert.assertEquals("b", b.getCorrelationId());
		Assert.assertEquals(JobStatus.Action.VALIDATION_LEVEL_1, b.getEvents().get(0).action);
		Assert.assertEquals(JobStatus.State.OK, b.getEndStatus());
		Assert.assertEquals(3, b.getEvents().size());
		Assert.assertEquals(new Date(now + 4), b.getFirstEvent());
		Assert.assertEquals(new Date(now + 6), b.getLastEvent());

		Assert.assertEquals("ost", b.getEvents().get(0).referential);
		Assert.assertEquals(Long.valueOf(1), b.getEvents().get(1).chouetteJobId);
		Assert.assertEquals("pb", b.getEvents().get(1).referential);
		Assert.assertEquals(Long.valueOf(1), b.getEvents().get(2).chouetteJobId);
		Assert.assertEquals("pb", b.getEvents().get(2).referential);
	}

}