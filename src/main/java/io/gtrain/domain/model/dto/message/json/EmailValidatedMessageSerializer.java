package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.EmailValidatedMessage;

/**
 * @author William Gentry
 */
public class EmailValidatedMessageSerializer extends SimpleJsonMessageSerializer<EmailValidatedMessage> {

	public EmailValidatedMessageSerializer() {
		super(EmailValidatedMessage.class);
	}
}
