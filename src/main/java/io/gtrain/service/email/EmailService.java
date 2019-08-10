package io.gtrain.service.email;

import io.gtrain.domain.model.GlmsMember;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface EmailService {

	Mono<GlmsMember> sendEmail(GlmsMember member);
}
