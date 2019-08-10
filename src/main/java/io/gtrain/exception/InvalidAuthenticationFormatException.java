package io.gtrain.exception;

import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
public class InvalidAuthenticationFormatException extends GlmsException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}

	@Override
	public Mono<AuthenticationFailedMessage> getValidationMessages() {
		return Mono.just(new AuthenticationFailedMessage("Authentication attempt in illegal format"));
	}
}
