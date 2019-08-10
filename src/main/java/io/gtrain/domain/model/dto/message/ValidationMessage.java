package io.gtrain.domain.model.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.model.dto.message.json.ValidationMessageDeserializer;
import io.gtrain.domain.model.dto.message.json.ValidationMessageSerializer;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
@JsonDeserialize(using = ValidationMessageDeserializer.class)
@JsonSerialize(using = ValidationMessageSerializer.class)
public class ValidationMessage extends GenericMessage<Map<String, List<String>>> {

	public ValidationMessage() {}

	public ValidationMessage(Map<String, List<String>> stringListMap) {
		super(stringListMap);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}
}
