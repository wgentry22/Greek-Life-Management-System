package io.gtrain.domain.model.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.model.dto.message.json.SuccessfulRegistrationMessageDeserializer;
import io.gtrain.domain.model.dto.message.json.SuccessfulRegistrationMessageSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author William Gentry
 */
@JsonDeserialize(using = SuccessfulRegistrationMessageDeserializer.class)
@JsonSerialize(using = SuccessfulRegistrationMessageSerializer.class)
public class SuccessfulRegistrationMessage extends GenericMessage<String> {

	public SuccessfulRegistrationMessage() {}

	public SuccessfulRegistrationMessage(String email) {
		super("Verification email sent to " + email);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}
}
