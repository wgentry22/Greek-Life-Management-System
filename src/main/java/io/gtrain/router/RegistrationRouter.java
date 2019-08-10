package io.gtrain.router;

import io.gtrain.handler.RegistrationHandler;
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
public class RegistrationRouter {

	private final RegistrationHandler registrationHandler;

	public RegistrationRouter(RegistrationHandler registrationHandler) {
		this.registrationHandler = registrationHandler;
	}

	@Bean
	public RouterFunction<ServerResponse> getRegistrationRouterBean() {
		return RouterFunctions.route(POST("/register").and(accept(MediaType.APPLICATION_JSON_UTF8)).and(contentType(MediaType.APPLICATION_JSON_UTF8)), registrationHandler::attemptRegistration);
	}
}
