package io.gtrain.service.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.dto.RegistrationForm;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class DefaultMemberRegistrationService implements MemberRegistrationService {

	private final ReactiveMongoTemplate mongoTemplate;

	public DefaultMemberRegistrationService(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<GlmsMember> registerUser(RegistrationForm registrationForm) {
		return mongoTemplate.save(RegistrationUtils.getMemberFromRegistrationForm(registrationForm));
	}
}
