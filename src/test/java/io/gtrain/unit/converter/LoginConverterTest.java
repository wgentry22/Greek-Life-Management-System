package io.gtrain.unit.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.gtrain.domain.model.GlmsAuthenticationToken;
import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.exception.InvalidAuthenticationFormatException;
import io.gtrain.security.converter.LoginFormConverter;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class LoginConverterTest extends ApplicationUnitTestBase {

	private final LoginFormConverter loginFormConverter = new LoginFormConverter(mapper);

	private MockServerWebExchange validLoginExchange;
	private MockServerWebExchange invalidLoginExchange;
	private final DataBufferFactory factory = new DefaultDataBufferFactory();

	private final String USERNAME = "test";
	private final String PASSWORD = "Password123!";

	@BeforeEach
	void initValidServerWebExchange() throws JsonProcessingException {
		final LoginForm loginForm = new LoginForm(USERNAME, PASSWORD);
		MockServerHttpRequest request = MockServerHttpRequest.post("/").body(Flux.just(factory.wrap(mapper.writeValueAsBytes(loginForm))));
		validLoginExchange = MockServerWebExchange.from(request);
	}

	@BeforeEach
	void initInvalidServerWebExchange() throws JsonProcessingException {
		final InvalidLoginForm invalidLoginForm = new InvalidLoginForm("INVALID", "INVALID", "INVALID");
		MockServerHttpRequest request = MockServerHttpRequest.post("/").body(Flux.just(factory.wrap(mapper.writeValueAsBytes(invalidLoginForm))));
		invalidLoginExchange = MockServerWebExchange.from(request);
	}

	@Test
	public void loginFormConverter_ShouldProduceUnauthenticated_GlmsToken() {
		Mono<Authentication> result = loginFormConverter.convert(validLoginExchange);
		StepVerifier.create(result)
					.assertNext(authentication -> {
						assertThat(authentication).isInstanceOf(GlmsAuthenticationToken.class);
						assertThat(authentication.isAuthenticated()).isFalse();
						assertThat(authentication.getName()).isEqualTo(USERNAME);
						assertThat(authentication.getCredentials()).isEqualTo(PASSWORD);
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}


	@Test
	public void loginConverter_ShouldThrowException_ForInvalidRequest() {
		Mono<Authentication> result = loginFormConverter.convert(invalidLoginExchange);
		StepVerifier.create(result)
					.consumeErrorWith(err -> {
						assertThat(err).isInstanceOf(InvalidAuthenticationFormatException.class);
						InvalidAuthenticationFormatException ex = (InvalidAuthenticationFormatException) err;
						assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

						StepVerifier.create(ex.getValidationMessages())
									.assertNext(message -> {
										assertThat(message.getMessage()).isNotBlank();
									});
					})
					.verifyThenAssertThat()
					.hasNotDroppedElements()
					.hasNotDroppedErrors();
	}



	public static class InvalidLoginForm {
		private String anotherField;
		private String password;
		private String username;

		public InvalidLoginForm() {}

		public InvalidLoginForm(String username, String password, String anotherField) {
			this.username = username;
			this.password = password;
			this.anotherField = anotherField;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getAnotherField() {
			return anotherField;
		}

		public void setAnotherField(String anotherField) {
			this.anotherField = anotherField;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			InvalidLoginForm that = (InvalidLoginForm) o;
			return Objects.equals(username, that.username) &&
					Objects.equals(password, that.password) &&
					Objects.equals(anotherField, that.anotherField);
		}

		@Override
		public int hashCode() {
			return Objects.hash(username, password, anotherField);
		}
	}
}
