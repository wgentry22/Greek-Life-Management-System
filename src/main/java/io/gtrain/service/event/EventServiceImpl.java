package io.gtrain.service.event;

import io.gtrain.domain.interfaces.EventRepository;
import io.gtrain.domain.model.Event;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

/**
 * @author William Gentry
 */
@Service
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Mono<List<Event>> getAllEvents() {
		return eventRepository.getAllEvents();
	}

	@Override
	public Mono<List<Event>> getAllEventsByLocation(String location) {
		return eventRepository.getAllEventsByLocation(location);
	}

	@Override
	public Mono<Event> createEvent(Principal principal, Event event) {
		event.setCreatedBy(principal.getName());
		return eventRepository.createEvent(event);
	}
}
