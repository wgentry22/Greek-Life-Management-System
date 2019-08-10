package io.gtrain.domain.model.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.model.dto.message.json.AuthenticationFailedMessageDeserializer;
import io.gtrain.domain.model.dto.message.json.AuthenticationFailedMessageSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author William Gentry
 */
@JsonSerialize(using = AuthenticationFailedMessageSerializer.class)
@JsonDeserialize(using = AuthenticationFailedMessageDeserializer.class)
public class AuthenticationFailedMessage extends GenericMessage<String> {

	public AuthenticationFailedMessage() {}

	public AuthenticationFailedMessage(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
}
