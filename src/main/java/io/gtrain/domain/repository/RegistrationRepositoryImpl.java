package io.gtrain.domain.repository;

import io.gtrain.domain.interfaces.RegistrationRepository;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.dto.message.EmailValidatedMessage;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class RegistrationRepositoryImpl implements RegistrationRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	private final Update emailVerificationUpdate = new Update();

	public RegistrationRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<EmailValidatedMessage> finalizeEmailVerification(String username) {
		return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), GlmsMember.class)
				// If member is enabled, email has already been verified
				.filter(member -> !member.isEnabled())
				.flatMap(member -> {
					return mongoTemplate.findAndModify(Query.query(Criteria.where("username").is(username)), getEmailVerificationUpdate(member), new FindAndModifyOptions().returnNew(false), GlmsMember.class);
				})
				.filter(member -> member.isEnabled())
				.flatMap(member -> Mono.just(new EmailValidatedMessage()));
	}

	private Update getEmailVerificationUpdate(GlmsMember member) {
		emailVerificationUpdate.set("enabled", true);
		return emailVerificationUpdate;
	}
}
