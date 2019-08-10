package io.gtrain.service.verification;

import io.gtrain.domain.interfaces.VerificationRepository;
import io.gtrain.domain.model.Verification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class VerificationServiceImpl implements VerificationService {

	private final VerificationRepository verificationRepository;

	public VerificationServiceImpl(VerificationRepository verificationRepository) {
		this.verificationRepository = verificationRepository;
	}

	@Override
	public Mono<String> getVerificationIdByUsername(String username) {
		return verificationRepository.findVerificationByUsername(username)
				.filter(verification -> verification != null)
				.map(verification -> verification.getId());
	}

	@Override
	public Mono<String> createVerification(String username) {
		return verificationRepository.createVerification(new Verification(username))
					.filter(verification -> verification != null && verification.getId() != null)
					.map(verification -> verification.getId());
	}

	@Override
	public Mono<String> getUsernameFromId(String id) {
		return verificationRepository.findVerificationById(id)
				.filter(verification -> verification != null && verification.getUsername() != null)
				.map(verification -> verification.getUsername());
	}
}
