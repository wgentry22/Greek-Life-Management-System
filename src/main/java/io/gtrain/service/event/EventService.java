package io.gtrain.service.event;

import io.gtrain.domain.model.Event;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

/**
 * @author William Gentry
 */
public interface EventService {

	Mono<List<Event>> getAllEvents();

	Mono<List<Event>> getAllEventsByLocation(String location);

	Mono<Event> createEvent(Principal principal, Event event);
}
