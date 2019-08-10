package io.gtrain.unit.web;

import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.domain.model.dto.message.AuthenticationFailedMessage;
import io.gtrain.unit.base.WebUnitTestBase;
import io.gtrain.unit.converter.LoginConverterTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class AuthenticationHandlerTest extends WebUnitTestBase {

	@Test
	public void authenticationHandler_ShouldProduceValidJWTCookie() {
		final LoginForm validLoginForm = new LoginForm("test", "Password123!");
		authenticationWebClient.post()
					.uri(URI.create("/login"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(Mono.just(validLoginForm), LoginForm.class)
					.exchange()
					.expectStatus()
						.isOk()
					.expectBody()
						.consumeWith(result -> {
							// Cookie should be present
							assertThat(result.getResponseCookies().getFirst("api_token")).isNotNull();

							// JWT in Cookie should not be blank
							final String token = result.getResponseCookies().getFirst("api_token").getValue();
							assertThat(token).isNotBlank();

							Mono<Authentication> authentication = tokenFactory.getAuthentication(token);
							StepVerifier.create(authentication)
										.assertNext(auth -> {
											assertThat(auth).isInstanceOf(GlmsAuthenticationToken.class);
											assertThat(auth.isAuthenticated()).isTrue();
											assertThat(auth.getName()).isNotBlank();
											assertThat(auth.getAuthorities()).isNotEmpty();
										})
										.expectComplete()
										.verifyThenAssertThat()
										.hasNotDroppedElements()
										.hasNotDroppedErrors();
						});
	}

	@Test
	public void authenticationHandler_ShouldProduceBadCredentialsException_ForInvalidAttempt() {
		final LoginForm invalidLoginForm = new LoginForm("invalid", "doesntmatter");
		authenticationWebClient.post()
					.uri(URI.create("/login"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(Mono.just(invalidLoginForm), LoginForm.class)
					.exchange()
					.expectStatus()
						.isUnauthorized()
					.expectBody(String.class)
						.consumeWith(result -> {
							assertThat(result.getResponseBody()).isEqualTo("Invalid Credentials");
						});
	}
}
