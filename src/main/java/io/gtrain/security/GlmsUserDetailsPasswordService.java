package io.gtrain.security;

import io.gtrain.domain.model.GlmsMember;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class GlmsUserDetailsPasswordService implements ReactiveUserDetailsPasswordService {

	private final ReactiveMongoTemplate mongoTemplate;
	private final Update update = new Update();

	public GlmsUserDetailsPasswordService(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<UserDetails> updatePassword(UserDetails userDetails, String s) {
		return mongoTemplate.findAndModify(Query.query(Criteria.where("username").is(userDetails.getUsername())), getUpdatePasswordQuery(s), new FindAndModifyOptions().returnNew(true), GlmsMember.class).cast(UserDetails.class);
	}

	private Update getUpdatePasswordQuery(String password) {
		update.set("password", password);
		return update;
	}
}
