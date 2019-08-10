package io.gtrain.domain.model.dto.message.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.gtrain.domain.model.dto.message.GenericMessage;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
public class NestedJsonMessageDeserializer<T extends GenericMessage<Map<String, List<String>>>> extends StdDeserializer<T> {

	private T message;

	public NestedJsonMessageDeserializer() {
		this(null);
	}

	public NestedJsonMessageDeserializer(Class<?> vc) {
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
		JsonNode root = p.getCodec().readTree(p);
		Map<String, List<?>> json = new LinkedHashMap<>();
		if (root.isArray()) {
			root.iterator().forEachRemaining(node -> {
				System.err.println(node);
			});
		}
		return null;
	}
}
