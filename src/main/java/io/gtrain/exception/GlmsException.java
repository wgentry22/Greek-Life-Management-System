package io.gtrain.exception;

import io.gtrain.domain.model.dto.message.GenericMessage;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
public abstract class GlmsException extends RuntimeException {

	public abstract HttpStatus getHttpStatus();

	public abstract Mono<? extends GenericMessage<?>> getValidationMessages();
}
