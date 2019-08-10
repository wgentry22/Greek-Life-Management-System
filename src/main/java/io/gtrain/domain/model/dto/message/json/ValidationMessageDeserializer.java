package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.ValidationMessage;

/**
 * @author William Gentry
 */
public class ValidationMessageDeserializer extends NestedJsonMessageDeserializer<ValidationMessage> {

	public ValidationMessageDeserializer() {
		super(ValidationMessage.class);
	}
}
