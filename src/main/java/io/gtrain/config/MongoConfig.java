package io.gtrain.config;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.gtrain.properties.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

/**
 * @author William Gentry
 */
@Configuration
public class MongoConfig {

	private final MongoProperties mongoProperties;

	public MongoConfig(MongoProperties mongoProperties) {
		this.mongoProperties = mongoProperties;
	}

	@Bean
	public MongoClient reactiveMongoClient() {
		return MongoClients.create(new ConnectionString(mongoProperties.getUrl()));
	}

	protected String getDatabaseName() {
		return mongoProperties.getDatabase().getName();
	}

	@Bean
	public ReactiveMongoDatabaseFactory reactiveMongoDbFactory() {
		return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient(), getDatabaseName());
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
		return new ReactiveMongoTemplate(reactiveMongoDbFactory());
	}
}
