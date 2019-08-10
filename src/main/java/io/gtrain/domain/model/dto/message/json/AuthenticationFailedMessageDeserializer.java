package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;

/**
 * @author William Gentry
 */
public class AuthenticationFailedMessageDeserializer extends SimpleJsonMessageDeserializer<AuthenticationFailedMessage> {

	public AuthenticationFailedMessageDeserializer() {
		super(AuthenticationFailedMessage.class);
	}
}

