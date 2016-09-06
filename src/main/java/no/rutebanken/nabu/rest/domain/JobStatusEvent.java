package no.rutebanken.nabu.rest.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import no.rutebanken.nabu.domain.Status;
import no.rutebanken.nabu.rest.domain.JobStatus.Action;
import no.rutebanken.nabu.rest.domain.JobStatus.State;

public class JobStatusEvent {

    @JsonProperty("state")
    public State state;

    @JsonProperty("date")
    public Date date;

	@JsonProperty("action")
	public Action action;

	public JobStatusEvent(Action action, State state, Date date) {
       this.action = action;
        this.state = state;
        this.date = date;
    }

    public static JobStatusEvent createFromStatus(Status e) {
    	return new JobStatusEvent(JobStatus.Action.valueOf(e.action.name()),JobStatus.State.valueOf(e.state.name()),e.date);
    }
}