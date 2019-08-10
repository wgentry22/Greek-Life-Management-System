package io.gtrain.handler;

import io.gtrain.domain.interfaces.ContactableMember;
import io.gtrain.domain.model.dto.ContactableMemberDTO;
import io.gtrain.security.CookieUtils;
import io.gtrain.security.GlmsUserDetailsService;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author William Gentry
 */
@Service
public class UserInfoHandler {

	private final TokenFactory tokenFactory;
	private final GlmsUserDetailsService userDetailsService;

	public UserInfoHandler(TokenFactory tokenFactory, GlmsUserDetailsService userDetailsService) {
		this.tokenFactory = tokenFactory;
		this.userDetailsService = userDetailsService;
	}

	public Mono<ServerResponse> getUserInfo(ServerRequest request) {
		final Optional<String> token = CookieUtils.getJWTFromCookie(request.exchange());
		if (!token.isPresent())
			return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
		return Mono.just(token.get())
				   .filter(tokenFactory::isValid)
				   .flatMap(tokenFactory::getAuthentication)
				   .flatMap(authentication -> userDetailsService.findByUsername(authentication.getName()))
				   .cast(ContactableMember.class)
				   .flatMap(member -> ServerResponse.ok().body(Mono.just(new ContactableMemberDTO(member)), ContactableMemberDTO.class));
	}
}
