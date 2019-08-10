package io.gtrain.service.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.dto.RegistrationForm;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface MemberRegistrationService {

	Mono<GlmsMember> registerUser(RegistrationForm registrationForm);
}
