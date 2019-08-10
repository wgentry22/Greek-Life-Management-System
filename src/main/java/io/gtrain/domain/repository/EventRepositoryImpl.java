package io.gtrain.domain.repository;

import com.mongodb.client.result.DeleteResult;
import io.gtrain.domain.interfaces.EventRepository;
import io.gtrain.domain.model.Event;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author William Gentry
 */
@Repository
public class EventRepositoryImpl implements EventRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	private final Update update = new Update();

	public EventRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<List<Event>> getAllEvents() {
		return mongoTemplate.findAll(Event.class).collectList();
	}

	@Override
	public Mono<List<Event>> getAllEventsByLocation(String location) {
		return mongoTemplate.find(Query.query(Criteria.where("location").is(location)), Event.class).collectList();
	}

	@Override
	public Mono<Event> getEventById(String objectId) {
		return mongoTemplate.findOne(Query.query(Criteria.where("id").is(objectId)), Event.class);
	}

	@Override
	public Mono<Event> createEvent(Event event) {
		return mongoTemplate.save(event);
	}

	@Override
	public Mono<Event> updateEvent(Event event) {
		return mongoTemplate.findAndModify(Query.query(Criteria.where("id").is(event.getId())), getUpdateEventQuery(event), new FindAndModifyOptions().returnNew(false), Event.class);
	}

	@Override
	public Mono<Boolean> deleteEvent(Event event) {
		return mongoTemplate.remove(Event.class).matching(Query.query(Criteria.where("id").is(event.getId())))
				.all()
				.map(DeleteResult::wasAcknowledged);
	}


	private Update getUpdateEventQuery(Event event) {
		update.set("location", event.getLocation());
		update.set("date", event.getDate());
		update.set("time", event.getTime());
		update.set("address", event.getAddress());
		update.set("createdBy", event.getCreatedBy());
		return update;
	}
}
