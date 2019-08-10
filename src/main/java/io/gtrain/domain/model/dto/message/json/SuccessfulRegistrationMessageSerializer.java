package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.SuccessfulRegistrationMessage;

/**
 * @author William Gentry
 */
public class SuccessfulRegistrationMessageSerializer extends SimpleJsonMessageSerializer<SuccessfulRegistrationMessage> {

	public SuccessfulRegistrationMessageSerializer() {
		super(SuccessfulRegistrationMessage.class);
	}
}
