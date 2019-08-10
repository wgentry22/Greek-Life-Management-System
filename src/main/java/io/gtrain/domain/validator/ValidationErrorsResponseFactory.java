package io.gtrain.domain.validator;

import org.springframework.validation.Errors;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
public class ValidationErrorsResponseFactory {

	private ValidationErrorsResponseFactory() {}

	public static Mono<Map<String, List<String>>> getValidationMessageAsJSON(Errors errors) {
		Map<String, List<String>> messages = new HashMap<>();
		errors.getFieldErrors().forEach(err -> messages.put(err.getField(), errors.getFieldErrors(err.getField()).stream().map(fe -> fe.getDefaultMessage()).collect(Collectors.toList())));
		return Mono.just(messages);
	}
}
