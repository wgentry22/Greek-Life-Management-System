package io.gtrain.service.registration;

import io.gtrain.domain.model.Verification;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class DefaultMemberVerificationService implements MemberVerificationService {

	private final ReactiveMongoTemplate mongoTemplate;

	public DefaultMemberVerificationService(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<Verification> generateVerification(String username) {
		return mongoTemplate.save(new Verification(username), "verification");
	}
}
