package io.gtrain.service.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.Verification;
import io.gtrain.domain.model.dto.RegistrationForm;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface RegistrationService {

	Mono<GlmsMember> attemptRegistration(RegistrationForm form);
}
