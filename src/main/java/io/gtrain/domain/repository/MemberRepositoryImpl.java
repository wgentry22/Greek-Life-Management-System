package io.gtrain.domain.repository;

import io.gtrain.domain.interfaces.MemberRepository;
import io.gtrain.domain.model.GlmsMember;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class MemberRepositoryImpl implements MemberRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public MemberRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<GlmsMember> findMemberByUsername(String username) {
		return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), GlmsMember.class);
	}

	@Override
	public Mono<GlmsMember> findMemberByEmail(String email) {
		return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), GlmsMember.class);
	}
}
