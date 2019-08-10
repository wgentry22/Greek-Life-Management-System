package io.gtrain.domain.model.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.model.dto.message.json.EmailValidatedMessageDeserializer;
import io.gtrain.domain.model.dto.message.json.EmailValidatedMessageSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author William Gentry
 */
@JsonSerialize(using = EmailValidatedMessageSerializer.class)
@JsonDeserialize(using = EmailValidatedMessageDeserializer.class)
public class EmailValidatedMessage extends GenericMessage<String> {

	public EmailValidatedMessage() {
		super("Email validated!");
	}

	@Override
	public HttpStatus getHttpStatus() {
		return null;
	}
}
