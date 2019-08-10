package io.gtrain.router;

import io.gtrain.handler.AuthenticationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author William Gentry
 */
@Configuration
public class AuthenticationRouter {

	private final AuthenticationHandler handler;

	public AuthenticationRouter(AuthenticationHandler handler) {
		this.handler = handler;
	}

	@Bean
	public RouterFunction<ServerResponse> getAuthenticationRouterBean() {
		return RouterFunctions.route(POST("/login").and(accept(MediaType.APPLICATION_JSON_UTF8)), handler::attemptAuthentication);
	}
}
