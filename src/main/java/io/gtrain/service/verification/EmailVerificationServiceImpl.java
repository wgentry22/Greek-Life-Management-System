package io.gtrain.service.verification;

import io.gtrain.domain.interfaces.RegistrationRepository;
import io.gtrain.domain.model.Verification;
import io.gtrain.domain.model.dto.message.EmailValidatedMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

	private final VerificationService verificationService;
	private final RegistrationRepository registrationRepository;

	public EmailVerificationServiceImpl(VerificationService verificationService, RegistrationRepository registrationRepository) {
		this.verificationService = verificationService;
		this.registrationRepository = registrationRepository;
	}

	@Override
	public Mono<EmailValidatedMessage> verifyEmail(String verification) {
		return verificationService.getUsernameFromId(verification)
					.flatMap(registrationRepository::finalizeEmailVerification);
	}
}
