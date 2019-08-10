package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.SuccessfulRegistrationMessage;

/**
 * @author William Gentry
 */
public class SuccessfulRegistrationMessageDeserializer extends SimpleJsonMessageDeserializer<SuccessfulRegistrationMessage> {

	public SuccessfulRegistrationMessageDeserializer() {
		super(SuccessfulRegistrationMessage.class);
	}
}
