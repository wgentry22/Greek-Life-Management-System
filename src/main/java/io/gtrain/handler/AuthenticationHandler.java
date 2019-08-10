package io.gtrain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;
import io.gtrain.exception.InvalidAuthenticationFormatException;
import io.gtrain.security.CookieUtils;
import io.gtrain.security.GlmsAuthenticationManager;
import io.gtrain.security.converter.LoginFormConverter;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class AuthenticationHandler {

	private final LoginFormConverter converter;
	private final GlmsAuthenticationManager authenticationManager;
	private final TokenFactory tokenFactory;

	public AuthenticationHandler(ObjectMapper mapper, GlmsAuthenticationManager authenticationManager, TokenFactory tokenFactory) {
		this.converter = new LoginFormConverter(mapper);
		this.authenticationManager = authenticationManager;
		this.tokenFactory = tokenFactory;
	}

	public Mono<ServerResponse> attemptAuthentication(ServerRequest request) {
		return converter.convert(request.exchange())
				.onErrorResume(InvalidAuthenticationFormatException.class, err -> Mono.empty())
					.flatMap(authenticationManager::authenticate)
					.filter(auth -> auth.isAuthenticated())
					.flatMap(authenticated -> {
						final String token = tokenFactory.generateToken(authenticated);
						return ServerResponse.ok().cookie(CookieUtils.createApiToken(token)).build();
					})
				.switchIfEmpty(ServerResponse.unprocessableEntity().build())
				.onErrorResume(BadCredentialsException.class, err -> ServerResponse.status(HttpStatus.UNAUTHORIZED).body(Mono.just(err.getMessage()), String.class));
	}
}
