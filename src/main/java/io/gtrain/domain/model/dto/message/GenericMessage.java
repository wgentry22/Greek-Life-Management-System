package io.gtrain.domain.model.dto.message;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @author William Gentry
 */
public abstract class GenericMessage<T> {

	protected T message;

	public GenericMessage() {}

	public GenericMessage(T message) {
		this.message = message;
	}

	public abstract HttpStatus getHttpStatus();

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) { this.message = message; }

	@Override
	public String toString() {
		return "{ \"message\": \"" + message + "\"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GenericMessage<?> that = (GenericMessage<?>) o;
		return Objects.equals(message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(message);
	}
}
