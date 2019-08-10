package io.gtrain.unit.web;

import io.gtrain.domain.model.PhoneNumber;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.dto.message.SuccessfulRegistrationMessage;
import io.gtrain.handler.RegistrationHandler;
import io.gtrain.service.email.OutgoingVerificationEmailService;
import io.gtrain.service.registration.DefaultMemberRegistrationService;
import io.gtrain.service.registration.GlmsRegistrationService;
import io.gtrain.unit.base.WebUnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author William Gentry
 */
public class RegistrationHandlerTest extends WebUnitTestBase {

	@BeforeEach
	void initRegistrationWebClient(@Mock DefaultMemberRegistrationService memberRegistrationService, @Mock OutgoingVerificationEmailService emailService) {
		registrationService = new GlmsRegistrationService(memberRegistrationService, emailService);
		RegistrationHandler handler = new RegistrationHandler(registrationService);
		RouterFunction<ServerResponse> registrationHandlerRouter = RouterFunctions.route().POST("/register", accept(MediaType.APPLICATION_JSON_UTF8), handler::attemptRegistration).build();
		registrationWebClient = WebTestClient.bindToRouterFunction(registrationHandlerRouter).handlerStrategies(HandlerStrategies.withDefaults()).build();

//		Mockito.lenient().doNothing().when(emailService).sendEmail(VALID_MEMBER);
		Mockito.lenient().when(emailService.sendEmail(VALID_MEMBER)).thenReturn(Mono.just(VALID_MEMBER));
		// Mock saving to storage
		Mockito.lenient().when(memberRegistrationService.registerUser(generateRegistrationForm())).thenReturn(Mono.just(generateValidMember()));
	}

	@Test
	public void registrationHandler_ShouldPublishEvent_ForValidRegistrationForm() {
		registrationWebClient.post()
					.uri(URI.create("/register"))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(Mono.just(generateRegistrationForm()), RegistrationForm.class)
					.exchange()
					.expectStatus()
						.isOk()
					.expectBody(SuccessfulRegistrationMessage.class)
						.consumeWith(result -> {
							assertThat(result.getResponseBody().getMessage()).isNotEmpty();
						});
	}

	@Test
	public void registrationService_ShouldProvide2ErrorMessages_ForMissingUsername() {
		RegistrationForm form = generateRegistrationForm();
		form.setUsername("");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
					.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("username").size()).isEqualTo(2);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage1_ForInvalidUsername() {
		RegistrationForm form = generateRegistrationForm();
		form.setUsername("test?");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
					.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("username").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide2ErrorMessages_ForMissingEmail() {
		RegistrationForm form = generateRegistrationForm();
		form.setEmail("");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("email").size()).isEqualTo(2);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidEmail() {
		RegistrationForm form = generateRegistrationForm();
		form.setEmail("test3mail.com");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("email").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide7ErrorMessages_ForMissingPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("");
		form.setPasswordVerify("");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
					.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isEqualTo(2);
					assertThat(messages.get("password").size()).isEqualTo(6);
					assertThat(messages.get("passwordVerify").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingLowerPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("PASSWORD123!");
		form.setPasswordVerify("PASSWORD123!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingUpperPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("password123!");
		form.setPasswordVerify("password123!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingDigitPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("PASSWORDasdf!");
		form.setPasswordVerify("PASSWORDasdf!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingSpecialPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("PASSWORD123asdf");
		form.setPasswordVerify("PASSWORD123asdf");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMismatchPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("Password123!");
		form.setPasswordVerify("Passturd123!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForTooManyRepeatedPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("Passssword123!");
		form.setPasswordVerify("Passssword123!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMaxRequirementPassword() {
		RegistrationForm form = generateRegistrationForm();
		StringBuilder tooLongPassword123 = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			tooLongPassword123.append("Password");
			tooLongPassword123.append(i);
			tooLongPassword123.append("123!");
		}
		final String password = tooLongPassword123.toString();
		assertThat(password.length()).isGreaterThan(128);
		form.setPassword(password);
		form.setPasswordVerify(password);
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMinRequirementPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("Pas123!");
		form.setPasswordVerify("Pas123!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("password").size()).isEqualTo(1);
				});
	}

	@Test
	public void registrationService_ShouldProvide2ErrorMessages_ForMissingFirstname() {
		RegistrationForm form = generateRegistrationForm();
		form.setFirstname("");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
					.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
					.consumeWith(result -> {
						assertThat(result.getResponseBody()).isNotEmpty();
						Map<String, List<String>> messages = result.getResponseBody();
						assertThat(messages.size()).isOne();
						assertThat(messages.get("firstname").size()).isEqualTo(2);
					});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidFirstname() {
		RegistrationForm form = generateRegistrationForm();
		form.setFirstname("Test!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("firstname").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide2ErrorMessages_ForMissingLastname() {
		RegistrationForm form = generateRegistrationForm();
		form.setLastname("");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("lastname").size()).isEqualTo(2);
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidLastname() {
		RegistrationForm form = generateRegistrationForm();
		form.setLastname("User!");
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("lastname").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingPhoneNumber() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("", "", ""));
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("phoneNumber").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidAreaCode() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("55", "555", "5555"));
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("phoneNumber").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidLinePrefix() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("555", "55", "5555"));
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("phoneNumber").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForInvalidLineNumber() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("555", "555", "555"));
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("phoneNumber").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingMajor() {
		RegistrationForm form = generateRegistrationForm();
		form.setMajor(null);
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("major").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingMinor() {
		RegistrationForm form = generateRegistrationForm();
		form.setMinor(null);
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("minor").size()).isOne();
				});
	}

	@Test
	public void registrationService_ShouldProvide1ErrorMessage_ForMissingYear() {
		RegistrationForm form = generateRegistrationForm();
		form.setYear(null);
		registrationWebClient.post()
				.uri(URI.create("/register"))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(form), RegistrationForm.class)
				.exchange()
				.expectStatus()
				.isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {})
				.consumeWith(result -> {
					assertThat(result.getResponseBody()).isNotEmpty();
					Map<String, List<String>> messages = result.getResponseBody();
					assertThat(messages.size()).isOne();
					assertThat(messages.get("year").size()).isOne();
				});
	}
}
