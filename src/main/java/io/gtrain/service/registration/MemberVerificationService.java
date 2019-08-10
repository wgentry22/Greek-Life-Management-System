package io.gtrain.service.registration;

import io.gtrain.domain.model.Verification;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface MemberVerificationService {

	Mono<Verification> generateVerification(String username);
}
