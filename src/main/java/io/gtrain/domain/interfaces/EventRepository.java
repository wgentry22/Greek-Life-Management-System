package io.gtrain.domain.interfaces;

import io.gtrain.domain.model.Event;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author William Gentry
 */
public interface EventRepository {

	Mono<List<Event>> getAllEvents();
	Mono<List<Event>> getAllEventsByLocation(String location);
	Mono<Event> getEventById(String objectId);

	Mono<Event> createEvent(Event event);

	Mono<Event> updateEvent(Event event);

	Mono<Boolean> deleteEvent(Event event);
}
