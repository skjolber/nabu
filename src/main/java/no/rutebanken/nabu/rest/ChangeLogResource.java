/*
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *   https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package no.rutebanken.nabu.rest;

import io.swagger.annotations.Api;
import no.rutebanken.nabu.domain.event.CrudEventSearch;
import no.rutebanken.nabu.repository.EventRepository;
import no.rutebanken.nabu.rest.domain.ApiCrudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Produces("application/json")
@Path("change_log")
@Api(tags = {"Change log resource"}, produces = "application/json")
public class ChangeLogResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventRepository eventRepository;

    @GET
    public List<ApiCrudEvent> find(@BeanParam CrudEventSearch search) {
        return eventRepository.findCrudEvents(search).stream().map(crudEvent -> ApiCrudEvent.fromCrudEvent(crudEvent)).collect(Collectors.toList());
    }
}
