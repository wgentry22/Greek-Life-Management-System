package io.gtrain.exception;

import io.gtrain.domain.model.dto.message.ValidationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
public class RegistrationFormValidationException extends GlmsException {

	private final Errors errors;

	public RegistrationFormValidationException(Errors errors) {
		assert errors != null;
		this.errors = errors;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}

	@Override
	public Mono<ValidationMessage> getValidationMessages() {
		Map<String, List<String>> messages = new HashMap<>();
		errors.getFieldErrors().forEach(err -> messages.put(err.getField(), errors.getFieldErrors(err.getField()).stream().map(fe -> fe.getDefaultMessage()).collect(Collectors.toList())));
		return Mono.just(new ValidationMessage(messages));
	}
}
