package io.gtrain.domain.interfaces;

import io.gtrain.domain.model.Verification;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface VerificationRepository {

	Mono<Verification> findVerificationByUsername(String username);
	Mono<Verification> findVerificationById(String id);
	Mono<Verification> createVerification(Verification verification);
}
