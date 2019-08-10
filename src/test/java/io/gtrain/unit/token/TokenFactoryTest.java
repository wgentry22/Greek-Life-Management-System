package io.gtrain.unit.token;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.unit.base.TokenUnitTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class TokenFactoryTest extends TokenUnitTestBase {

	@Test
	public void tokenFactory_ShouldGenerateJWT() {
		final String token = tokenFactory.generateToken(new GlmsAuthenticationToken(generateValidMember()));
		assertThat(token).isNotBlank();
	}

	@Test
	public void tokenFactory_ShouldValidateJWT() {
		final String token = tokenFactory.generateToken(new GlmsAuthenticationToken(generateValidMember()));
		assertThat(tokenFactory.isValid(token)).isTrue();
	}

	@Test
	public void tokenFactory_ShouldParseAuthenticationFromToken() {
		final String token = tokenFactory.generateToken(new GlmsAuthenticationToken(generateValidMember()));
		Mono<Authentication> result = tokenFactory.getAuthentication(token);
		StepVerifier.create(result)
					.assertNext(auth -> {
						assertThat(auth).isInstanceOf(GlmsAuthenticationToken.class);
						assertThat(auth.isAuthenticated()).isTrue();
						assertThat(auth.getName()).isNotBlank();
						assertThat(auth.getAuthorities()).isNotEmpty();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}
}
