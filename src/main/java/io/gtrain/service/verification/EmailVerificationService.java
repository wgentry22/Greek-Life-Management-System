package io.gtrain.service.verification;

import io.gtrain.domain.model.Verification;
import io.gtrain.domain.model.dto.message.EmailValidatedMessage;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface EmailVerificationService {

	Mono<EmailValidatedMessage> verifyEmail(String verification);
}
