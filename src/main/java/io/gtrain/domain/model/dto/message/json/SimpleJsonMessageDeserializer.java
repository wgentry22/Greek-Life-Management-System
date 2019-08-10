package io.gtrain.domain.model.dto.message.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.gtrain.domain.model.dto.message.GenericMessage;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class SimpleJsonMessageDeserializer<T extends GenericMessage<String>> extends StdDeserializer<T> {

	private T message;

	public SimpleJsonMessageDeserializer() { this(null); }

	public SimpleJsonMessageDeserializer(Class<T> vc) {
		super(vc);
		try {
			this.message = (T) Class.forName(vc.getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = p.getCodec().readTree(p);
		message.setMessage(node.get("message").asText());
		return message;
	}
}
