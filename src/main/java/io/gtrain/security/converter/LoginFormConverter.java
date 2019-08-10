package io.gtrain.security.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.exception.InvalidAuthenticationFormatException;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class LoginFormConverter implements ServerAuthenticationConverter {

	private final ObjectMapper mapper;

	public LoginFormConverter(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	private DataBufferFactory bufferFactory = new DefaultDataBufferFactory();

	@Override
	public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
		Mono<LoginForm> form = serverWebExchange.getRequest().getBody().next().flatMap(buffer -> {
			try {
				return Mono.just(mapper.readValue(buffer.asByteBuffer().array(), LoginForm.class)).map(loginForm -> {
					System.err.println("LoginFormConverter: " + loginForm);
					return loginForm;
				});
			} catch (IOException e) {
				return Mono.defer(() -> Mono.error(() -> new InvalidAuthenticationFormatException()));
			}
		});
		return form.flatMap(loginForm -> Mono.just(new GlmsAuthenticationToken(loginForm)));
	}
//		return serverWebExchange.getRequest().getBody().next().flatMap(buffer -> {
//			try {
//				return Mono.just(mapper.readValue(buffer.asInputStream(), LoginForm.class)).flatMap(loginForm -> Mono.just(new GlmsAuthenticationToken(loginForm)));
//			} catch (IOException e) {
//				return Mono.defer(() -> Mono.error(new InvalidAuthenticationFormatException()));
//			}
//		});
//	}
}
