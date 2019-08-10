package io.gtrain.web;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;
import io.gtrain.exception.InvalidAuthenticationFormatException;
import io.gtrain.security.CookieUtils;
import io.gtrain.security.GlmsAuthenticationManager;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
//@RestController
public class AuthenticationController {

	private final GlmsAuthenticationManager authenticationManager;
	private final TokenFactory tokenFactory;

	public AuthenticationController(GlmsAuthenticationManager authenticationManager, TokenFactory tokenFactory) {
		this.authenticationManager = authenticationManager;
		this.tokenFactory = tokenFactory;
	}

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Mono<ServerResponse> attemptAuthentication(@RequestBody LoginForm loginForm) {
		System.err.println("Inside authenticaiton controller");
		return authenticationManager.authenticate(new GlmsAuthenticationToken(loginForm))
				.flatMap(authentication -> Mono.just(tokenFactory.generateToken(authentication)))
				.flatMap(jwt -> {
					return ServerResponse.ok().cookie(CookieUtils.createApiToken(jwt)).build();
				})
				.onErrorResume(InvalidAuthenticationFormatException.class, err -> ServerResponse.status(err.getHttpStatus()).body(err.getValidationMessages(), AuthenticationFailedMessage.class))
				.onErrorResume(BadCredentialsException.class, err -> ServerResponse.status(HttpStatus.UNAUTHORIZED).body(Mono.just(err.getMessage()), String.class));
	}
}
