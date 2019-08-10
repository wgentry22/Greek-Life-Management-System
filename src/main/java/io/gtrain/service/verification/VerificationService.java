package io.gtrain.service.verification;

import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface VerificationService {

	Mono<String> getVerificationIdByUsername(String username);

	Mono<String> createVerification(String username);

	Mono<String> getUsernameFromId(String id);
}
