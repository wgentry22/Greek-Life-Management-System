package io.gtrain.domain.model.dto.message.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.gtrain.domain.model.dto.message.GenericMessage;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class SimpleJsonMessageSerializer<T extends GenericMessage<String>> extends StdSerializer<T> {

	public SimpleJsonMessageSerializer() { this(null); }

	public SimpleJsonMessageSerializer(Class<T> t) {
		super(t);
	}

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("message", value.getMessage());
		gen.writeEndObject();
	}
}
