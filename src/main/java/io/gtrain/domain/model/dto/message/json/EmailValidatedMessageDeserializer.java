package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.EmailValidatedMessage;

/**
 * @author William Gentry
 */
public class EmailValidatedMessageDeserializer extends SimpleJsonMessageDeserializer<EmailValidatedMessage> {

	public EmailValidatedMessageDeserializer() {
		super(EmailValidatedMessage.class);
	}
}
