package io.gtrain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author William Gentry
 */
@Component
@PropertySource("classpath:mongo.properties")
@ConfigurationProperties(prefix = "mongo")
public class MongoProperties {

	private Database database = new Database();

	private String url;

	public static class Database {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
