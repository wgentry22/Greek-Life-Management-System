package io.gtrain.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.StringJoiner;

/**
 * @author William Gentry
 */
@Document(collection = "verification")
public class Verification {

	@Id
	private String id;

	@Indexed(unique = true)
	private final String username;

	@PersistenceConstructor
	public Verification(String username) {
		this.username = username;
	}

	// Convenience constructor for testing
	public Verification(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Verification.class.getSimpleName() + "[", "]")
				.add("id='" + id + "'")
				.add("username='" + username + "'")
				.toString();
	}
}
