package io.gtrain.security;

import io.gtrain.security.converter.JwtConverter;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * @author William Gentry
 */
@Service
public class GlmsTokenAuthenticationFilter extends AuthenticationWebFilter {

	private final JwtConverter converter;
	private final ServerWebExchangeMatcher matchers;
	private final Logger logger = Loggers.getLogger(getClass());

	public GlmsTokenAuthenticationFilter(GlmsAuthenticationManager authenticationManager, TokenFactory tokenFactory) {
		super(authenticationManager);
		converter = new JwtConverter(tokenFactory);
		matchers = ServerWebExchangeMatchers.pathMatchers("/api/**");
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		final String request = exchange.getRequest().getMethodValue();
		final String uri = exchange.getRequest().getURI().toString();
		logger.info("{} request coming to {}", request, uri);
		if ((uri.equals("/login") || uri.equals("/register")) && request.equals("POST"))
			return chain.filter(exchange);

		if (uri.contains("/verify/email") && request.equals("GET"))
			return chain.filter(exchange);

		return matchers.matches(exchange)
					.filter(ServerWebExchangeMatcher.MatchResult::isMatch)
					.flatMap(result -> {
						return converter.convert(exchange);
					})
					.filter(Authentication::isAuthenticated)
					.map(authentication -> {
						logger.info("Found authenticated user: {}", authentication);
						return authentication;
					})
					.flatMap(authentication -> {
						return chain.filter(exchange.mutate().principal(Mono.just(authentication)).build())
										.subscriberContext(ReactiveSecurityContextHolder.withAuthentication(authentication));
					});
	}
}
