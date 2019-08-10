package io.gtrain.handler;

import io.gtrain.domain.model.Verification;
import io.gtrain.domain.model.dto.message.EmailValidatedMessage;
import io.gtrain.service.verification.EmailVerificationService;
import io.gtrain.service.verification.VerificationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * @author William Gentry
 */
@Service
public class EmailVerificationHandler {

	private final EmailVerificationService emailVerificationService;

	public EmailVerificationHandler(EmailVerificationService emailVerificationService) {
		this.emailVerificationService = emailVerificationService;
	}

	public Mono<ServerResponse> handleEmailVerification(ServerRequest request) {
		return Mono.just(request.queryParam("id").get())
					.filter(StringUtils::hasText)
					.flatMap(id -> {
						return ServerResponse.ok().body(emailVerificationService.verifyEmail(id), EmailValidatedMessage.class);
					})
					.switchIfEmpty(ServerResponse.badRequest().build());
	}
}
