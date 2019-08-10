package io.gtrain.security.converter;

import io.gtrain.security.CookieUtils;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author William Gentry
 */
public class JwtConverter implements ServerAuthenticationConverter {

	private final TokenFactory tokenFactory;

	public JwtConverter(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	@Override
	public Mono<Authentication> convert(ServerWebExchange serverWebExchange) {
		final Optional<String> token = CookieUtils.getJWTFromCookie(serverWebExchange);
		if (!token.isPresent() || !StringUtils.hasText(token.get()))
			return Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials")));
		return Mono.just(token.get())
					.filter(tokenFactory::isValid)
					.flatMap(tokenFactory::getAuthentication);
	}
}
