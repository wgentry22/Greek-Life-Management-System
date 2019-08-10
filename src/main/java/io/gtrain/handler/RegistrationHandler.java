package io.gtrain.handler;

import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.dto.message.SuccessfulRegistrationMessage;
import io.gtrain.domain.model.dto.message.ValidationMessage;
import io.gtrain.exception.RegistrationFormValidationException;
import io.gtrain.service.registration.GlmsRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * @author William Gentry
 */
@Service
public class RegistrationHandler {

	private final GlmsRegistrationService registrationService;
	private final Logger logger = Loggers.getLogger(getClass());

	public RegistrationHandler(GlmsRegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public Mono<ServerResponse> attemptRegistration(ServerRequest request) {
		logger.debug("Inside Registration Handler!");
		return request.body(BodyExtractors.toMono(RegistrationForm.class))
				.flatMap(form -> {
					return registrationService.attemptRegistration(form);
				})
				.flatMap(member -> {
					return ServerResponse.ok().body(Mono.just(new SuccessfulRegistrationMessage(member.getEmail())), SuccessfulRegistrationMessage.class);
				})
				.onErrorResume(RegistrationFormValidationException.class, err -> ServerResponse.status(err.getHttpStatus()).body(err.getValidationMessages(), ValidationMessage.class));
	}

}
