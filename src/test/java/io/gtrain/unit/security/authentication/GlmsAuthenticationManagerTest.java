package io.gtrain.unit.security.authentication;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class GlmsAuthenticationManagerTest extends ApplicationUnitTestBase {

	@Test
	public void authenticationManager_ShouldProduceAuthenticatedGlmsToken_ForValidUsernameLoginForm() {
		Mono<Authentication> result = authenticationManager.authenticate(new GlmsAuthenticationToken(new LoginForm("test", "Password123!")));
		StepVerifier.create(result)
					.assertNext(authentication -> {
						assertThat(authentication).isInstanceOf(GlmsAuthenticationToken.class);
						assertThat(authentication.isAuthenticated()).isTrue();
						assertThat(authentication.getName()).isEqualTo("test");
						assertThat(authentication.getAuthorities().size()).isOne();
						assertThat(authentication.getAuthorities().stream().allMatch(authority ->  ((GrantedAuthority) authority).getAuthority().equals("ROLE_VIEWER"))).isTrue();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	@Test
	public void authenticationManager_ShouldProduceAuthenticatedGlmsToken_ForValidEmailLoginForm() {
		Mono<Authentication> result = authenticationManager.authenticate(new GlmsAuthenticationToken(new LoginForm("test@email.com", "Password123!")));
		StepVerifier.create(result)
				.assertNext(authentication -> {
					assertThat(authentication).isInstanceOf(GlmsAuthenticationToken.class);
					assertThat(authentication.isAuthenticated()).isTrue();
					assertThat(authentication.getName()).isEqualTo("test");
					assertThat(authentication.getAuthorities().size()).isOne();
					assertThat(authentication.getAuthorities().stream().allMatch(authority ->  ((GrantedAuthority) authority).getAuthority().equals("ROLE_VIEWER"))).isTrue();
				})
				.expectComplete()
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void authenticationManager_ShouldThrowBadCredentialsException_ForInvalidUsernameLoginForm() {
		Mono<Authentication> result = authenticationManager.authenticate(new GlmsAuthenticationToken(new LoginForm("invalid", "invalid")));
		StepVerifier.create(result)
					.consumeErrorWith(err -> {
						assertThat(err).isInstanceOf(BadCredentialsException.class);
						assertThat(err.getMessage()).isEqualTo("Invalid Credentials");
					})
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	@Test
	public void authenticationManager_ShouldThrowBadCredentialsException_ForInvalidEmailLoginForm() {
		Mono<Authentication> result = authenticationManager.authenticate(new GlmsAuthenticationToken(new LoginForm("invalid@email.com", "invalid")));
		StepVerifier.create(result)
					.consumeErrorWith(err -> {
						assertThat(err).isInstanceOf(BadCredentialsException.class);
						assertThat(err.getMessage()).isEqualTo("Invalid Credentials");
					})
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}
}
