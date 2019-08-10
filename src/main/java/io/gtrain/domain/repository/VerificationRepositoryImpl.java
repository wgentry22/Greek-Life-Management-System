package io.gtrain.domain.repository;

import io.gtrain.domain.interfaces.VerificationRepository;
import io.gtrain.domain.model.Verification;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class VerificationRepositoryImpl implements VerificationRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public VerificationRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<Verification> findVerificationByUsername(String username) {
		return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), Verification.class);
	}

	@Override
	public Mono<Verification> createVerification(Verification verification) {
		return mongoTemplate.save(verification);
	}

	@Override
	public Mono<Verification> findVerificationById(String id) {
		return mongoTemplate.findOne(Query.query(Criteria.where("id").is(id)), Verification.class);
	}
}
