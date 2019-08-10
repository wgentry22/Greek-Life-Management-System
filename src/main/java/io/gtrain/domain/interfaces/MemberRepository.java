package io.gtrain.domain.interfaces;

import io.gtrain.domain.model.GlmsMember;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface MemberRepository {

	Mono<GlmsMember> findMemberByUsername(String username);
	Mono<GlmsMember> findMemberByEmail(String email);
}
