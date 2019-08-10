package io.gtrain.unit.converter;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.security.CookieUtils;
import io.gtrain.security.converter.JwtConverter;
import io.gtrain.unit.base.TokenUnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class JwtConverterTest extends TokenUnitTestBase {

	private final JwtConverter converter = new JwtConverter(tokenFactory);

	private MockServerWebExchange withJwtExchange;

	private MockServerWebExchange withoutJwtExchange;

	@BeforeEach
	void initWithJwtExchange() {
		final String token = tokenFactory.generateToken(new GlmsAuthenticationToken(generateValidMember()));
		MockServerHttpRequest request = MockServerHttpRequest.get("/").cookie(CookieUtils.createCookie("api_token", token)).build();
		withJwtExchange = MockServerWebExchange.from(request);
	}

	@BeforeEach
	void initWithoutJwtExchange() {
		MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
		withoutJwtExchange = MockServerWebExchange.from(request);
	}

	@Test
	public void jwtConverter_ShouldProduce_AuthenticatedGlmsToken() {
		Mono<Authentication> result = converter.convert(withJwtExchange);
		StepVerifier.create(result)
					.assertNext(auth -> {
						assertThat(auth).isInstanceOf(GlmsAuthenticationToken.class);
						assertThat(auth.isAuthenticated()).isTrue();
						assertThat(auth.getName()).isNotBlank();
						assertThat(auth.getCredentials().toString()).isBlank();
						assertThat(auth.getAuthorities()).isNotEmpty();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedElements()
					.hasNotDroppedErrors();
	}

	@Test
	public void jwtConverter_ShouldThrowBadCredentialsException_ForMissingJWT() {
		Mono<Authentication> result = converter.convert(withoutJwtExchange);
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
