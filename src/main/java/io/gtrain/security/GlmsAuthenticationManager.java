package io.gtrain.security;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * @author William Gentry
 */
@Service
@Primary
public class GlmsAuthenticationManager implements ReactiveAuthenticationManager {

	private final GlmsUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final GlmsUserDetailsPasswordService passwordService;
	private final Logger logger = Loggers.getLogger(getClass());

	public GlmsAuthenticationManager(GlmsUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, GlmsUserDetailsPasswordService passwordService) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.passwordService = passwordService;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		final String presentedPassword = (String) authentication.getCredentials();
		return userDetailsService.findByUsername(authentication.getName())
					.filter(userDetails -> passwordEncoder.matches(presentedPassword, userDetails.getPassword()))
					.switchIfEmpty(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))))
					.flatMap(userDetails -> passwordEncoder.upgradeEncoding(userDetails.getPassword()) ? passwordService.updatePassword(userDetails, passwordEncoder.encode(presentedPassword)) : Mono.just(userDetails))
					.map(GlmsAuthenticationToken::new);
	}
}
