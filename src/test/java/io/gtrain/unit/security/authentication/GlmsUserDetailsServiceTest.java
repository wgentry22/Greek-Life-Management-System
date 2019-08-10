package io.gtrain.unit.security.authentication;

import io.gtrain.domain.interfaces.ContactableMember;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class GlmsUserDetailsServiceTest extends ApplicationUnitTestBase {

	@Test
	public void glmsUserDetailsService_ShouldProduceUserDetails_ForValidUsername() {
		Mono<UserDetails> result = userDetailsService.findByUsername("test");
		StepVerifier.create(result)
					.assertNext(userDetails -> {
						assertThat(userDetails).isInstanceOf(ContactableMember.class);
						assertThat(userDetails).isInstanceOf(GlmsMember.class);
						assertThat(userDetails.getUsername()).isNotBlank();
						assertThat(userDetails.getAuthorities()).isNotEmpty();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	@Test
	public void glmsUserDetailsService_ShouldProduceUserDetails_ForValidEmail() {
		Mono<UserDetails> result = userDetailsService.findByUsername("test@email.com");
		StepVerifier.create(result)
					.assertNext(userDetails -> {
						assertThat(userDetails).isInstanceOf(ContactableMember.class);
						assertThat(userDetails).isInstanceOf(GlmsMember.class);
						assertThat(userDetails.getUsername()).isNotBlank();
						assertThat(userDetails.getAuthorities()).isNotEmpty();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	@Test
	public void glmsUserDetailsService_ShouldThrowBadCredentialsException_ForInvalidUsername() {
		Mono<UserDetails> result = userDetailsService.findByUsername("invalid");
		StepVerifier.create(result)
					.expectError(BadCredentialsException.class)
					.verifyThenAssertThat()
					.hasNotDroppedElements()
					.hasNotDroppedErrors();
	}

	@Test
	public void glmsUserDetailsService_ShouldThrowBadCredentialsException_ForInvalidEmail() {
		Mono<UserDetails> result = userDetailsService.findByUsername("invalid@email.com");
		StepVerifier.create(result)
				.expectError(BadCredentialsException.class)
				.verifyThenAssertThat()
				.hasNotDroppedElements()
				.hasNotDroppedErrors();
	}
}
