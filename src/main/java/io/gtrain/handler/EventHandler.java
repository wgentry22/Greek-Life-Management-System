package io.gtrain.handler;

import io.gtrain.domain.model.Event;
import io.gtrain.service.event.EventService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author William Gentry
 */
@Service
public class EventHandler {

	private final EventService eventService;

	public EventHandler(EventService eventService) {
		this.eventService = eventService;
	}

	public Mono<ServerResponse> getAllEvents(ServerRequest request) {
		return ServerResponse.ok().body(eventService.getAllEvents(), new ParameterizedTypeReference<List<Event>>() {});
	}

	public Mono<ServerResponse> createEvent(ServerRequest request) {
		return request.principal().flatMap(user -> {
			return request.bodyToMono(Event.class).flatMap(event -> {
				return ServerResponse.ok().body(eventService.createEvent(user, event), Event.class);
			})
			.switchIfEmpty(ServerResponse.unprocessableEntity().build());
		})
		.switchIfEmpty(ServerResponse.badRequest().build());
	}

	public Mono<ServerResponse> getAllEventsByLocation(ServerRequest request) {
		final String location = request.pathVariable("location");
		return ServerResponse.ok().body(eventService.getAllEventsByLocation(location), new ParameterizedTypeReference<List<Event>>() {});
	}
}
