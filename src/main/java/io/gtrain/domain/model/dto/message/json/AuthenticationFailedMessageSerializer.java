package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;

/**
 * @author William Gentry
 */
public class AuthenticationFailedMessageSerializer extends SimpleJsonMessageSerializer<AuthenticationFailedMessage> {

	public AuthenticationFailedMessageSerializer() {
		super(AuthenticationFailedMessage.class);
	}
}
