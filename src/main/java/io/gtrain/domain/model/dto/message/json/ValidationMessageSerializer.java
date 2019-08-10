package io.gtrain.domain.model.dto.message.json;

import io.gtrain.domain.model.dto.message.ValidationMessage;

/**
 * @author William Gentry
 */
public class ValidationMessageSerializer extends NestedJsonMessageSerializer<ValidationMessage> {

	public ValidationMessageSerializer() {
		super(ValidationMessage.class);
	}
}
