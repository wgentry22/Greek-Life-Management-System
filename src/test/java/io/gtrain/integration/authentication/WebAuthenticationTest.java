package io.gtrain.integration.authentication;

import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.integration.base.WebIntegrationTestBase;
import io.gtrain.security.CookieUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class WebAuthenticationTest extends WebIntegrationTestBase {

	private LoginForm loginForm;

	private LoginForm invalidContentLoginForm;

	@BeforeEach
	void initForms() {
		this.loginForm = new LoginForm("test", "Password123!");
		this.invalidContentLoginForm = new LoginForm("invalid", "doesntmatter");
	}

	@Test
	public void validLoginForm_ShouldGenerateApiTokenCookie() {
		webClient
				.post()
				.uri(URI.create("/login"))
				.body(Mono.just(loginForm), LoginForm.class)
				.exchange()
				.expectStatus()
					.isOk()
				.expectBody()
					.consumeWith(result -> {
						assertThat(result.getResponseCookies().size()).isNotZero();
						assertThat(result.getResponseCookies().getFirst(CookieUtils.COOKIE_NAME)).isNotNull();
						assertThat(result.getResponseCookies().getFirst(CookieUtils.COOKIE_NAME).getValue()).isNotBlank();
					});
	}

	@Test
	public void invalidContentLoginForm_ShouldGenerateUnauthorized() {
		webClient
				.post()
				.uri(URI.create("/login"))
				.body(Mono.just(invalidContentLoginForm), LoginForm.class)
				.exchange()
				.expectStatus()
					.isUnauthorized()
				.expectBody(String.class)
					.isEqualTo("Invalid Credentials");
	}
}
