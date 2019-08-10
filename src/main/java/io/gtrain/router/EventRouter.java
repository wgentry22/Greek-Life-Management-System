package io.gtrain.router;

import io.gtrain.handler.EventHandler;
import io.gtrain.security.annotations.CanCreateEvents;
import io.gtrain.security.annotations.CanListEvents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author William Gentry
 */
@Configuration
public class EventRouter {

	@Bean
	public RouterFunction<ServerResponse> getEventRouterBean(EventHandler handler) {
		return RouterFunctions
				.route()
					.add(getCreateEventRouterEndpoint(handler))
					.add(getListEventsByLocationRouterEndpoint(handler))
					.add(getListEventsRouterEndpoint(handler))
				.build();
	}

	@CanListEvents
	public RouterFunction<ServerResponse> getListEventsRouterEndpoint(EventHandler handler) {
		return RouterFunctions.route(GET("/api/events").and(contentType(MediaType.APPLICATION_JSON_UTF8)), handler::getAllEvents);
	}

	@CanListEvents
	public RouterFunction<ServerResponse> getListEventsByLocationRouterEndpoint(EventHandler handler) {
		return RouterFunctions.route(GET("/api/events/{location}").and(contentType(MediaType.APPLICATION_JSON_UTF8)), handler::getAllEventsByLocation);
	}

	@CanCreateEvents
	public RouterFunction<ServerResponse> getCreateEventRouterEndpoint(EventHandler handler) {
		return RouterFunctions.route(POST("/api/events").and(contentType(MediaType.APPLICATION_JSON_UTF8)), handler::createEvent);
	}
}
