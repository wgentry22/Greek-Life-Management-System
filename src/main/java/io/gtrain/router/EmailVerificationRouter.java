package io.gtrain.router;

import io.gtrain.handler.EmailVerificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * @author William Gentry
 */
@Configuration
public class EmailVerificationRouter {

	private final EmailVerificationHandler emailVerificationHandler;

	public EmailVerificationRouter(EmailVerificationHandler emailVerificationHandler) {
		this.emailVerificationHandler = emailVerificationHandler;
	}

	@Bean
	public RouterFunction<ServerResponse> emailVerificationRouterBean() {
		return RouterFunctions.route(GET("/verify/email"), emailVerificationHandler::handleEmailVerification);
	}
}
