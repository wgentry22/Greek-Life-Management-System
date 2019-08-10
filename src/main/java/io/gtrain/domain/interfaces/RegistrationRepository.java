package io.gtrain.domain.interfaces;

import io.gtrain.domain.model.dto.message.EmailValidatedMessage;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface RegistrationRepository {

	Mono<EmailValidatedMessage> finalizeEmailVerification(String username);
}
