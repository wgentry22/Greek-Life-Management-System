package io.gtrain.unit.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.PhoneNumber;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.exception.RegistrationFormValidationException;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class GlmsRegistrationServiceTest extends ApplicationUnitTestBase {

	@Test
	public void registrationService_ShouldProduceVerification_ForValidRegistrationForm() {
		Mono<GlmsMember> result = registrationService.attemptRegistration(generateRegistrationForm());
		StepVerifier.create(result)
					.assertNext(member -> {
						assertThat(member).isNotNull();
					})
					.expectComplete()
					.verifyThenAssertThat()
					.hasNotDroppedElements()
					.hasNotDroppedErrors();
	}

	// Validation Tests


	/*

	 *          USERNAME

	 */

	@Test
	public void registrationService_ShouldThrowException_ForMissingUsername() {
		RegistrationForm form = generateRegistrationForm();
		form.setUsername("");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
					.consumeErrorWith(err -> {
						assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
						RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

						// RegistrationFormValidationException should have 1 entry with 2 messages
						StepVerifier.create(ex.getValidationMessages())
									.assertNext(messages -> {
										assertThat(messages.getMessage().size()).isOne();
										assertThat(messages.getMessage().get("username").size()).isEqualTo(2);
									})
									.expectComplete()
									.verifyThenAssertThat()
									.hasNotDroppedElements()
									.hasNotDroppedErrors();
					})
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidUsernameCharacter() {
		RegistrationForm form = generateRegistrationForm();
		form.setUsername("test<>");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
					.consumeErrorWith(err -> {
						assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
						RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

						// RegistrationFormValidationException should have 1 entry with 2 messages
						StepVerifier.create(ex.getValidationMessages())
								.assertNext(messages -> {
									assertThat(messages.getMessage().size()).isOne();
									assertThat(messages.getMessage().get("username").size()).isEqualTo(1);
								})
								.expectComplete()
								.verifyThenAssertThat()
								.hasNotDroppedElements()
								.hasNotDroppedErrors();
					})
					.verifyThenAssertThat()
					.hasNotDroppedErrors()
					.hasNotDroppedElements();
	}

	/*

	 *                 FIRSTNAME

	 */

	@Test
	public void registrationService_ShouldThrowException_ForMissingFirstname() {
		RegistrationForm form = generateRegistrationForm();
		form.setFirstname("");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("firstname").size()).isEqualTo(2);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidFirstname() {
		RegistrationForm form = generateRegistrationForm();
		form.setFirstname("Test@");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("firstname").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	/*

	 *          LASTNAME

	 */

	@Test
	public void registrationService_ShouldThrowException_ForMissingLastname() {
		RegistrationForm form = generateRegistrationForm();
		form.setLastname("");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("lastname").size()).isEqualTo(2);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidLastname() {
		RegistrationForm form = generateRegistrationForm();
		form.setLastname("User@");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("lastname").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	/*

	 *          EMAIL

	 */

	@Test
	public void registrationService_ShouldThrowException_ForMissingEmail() {
		RegistrationForm form = generateRegistrationForm();
		form.setEmail("");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("email").size()).isEqualTo(2);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidEmail() {
		RegistrationForm form = generateRegistrationForm();
		form.setEmail("invalidemail");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("email").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	/*

	 *          PASSWORD

	 */
	@Test
	public void registrationService_ShouldThrowException_ForMissingPassword() {
		RegistrationForm form = generateRegistrationForm();
		form.setPassword("");
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(7);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForTooShortPassword() {
		RegistrationForm form = generateRegistrationForm();
		final String tooShortPassword = "Ab3!";
		form.setPassword(tooShortPassword);
		form.setPasswordVerify(tooShortPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForTooLongPassword() {
		RegistrationForm form = generateRegistrationForm();
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < 34; i++) {
			builder.append("Ab3!");
		}
		final String tooLongPassword = builder.toString();
		assertThat(tooLongPassword.length()).isGreaterThan(128);
		form.setPassword(tooLongPassword);
		form.setPasswordVerify(tooLongPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingDigit() {
		RegistrationForm form = generateRegistrationForm();
		final String invalidPassword = "Ab!aasdfasdfasdf";
		form.setPassword(invalidPassword);
		form.setPasswordVerify(invalidPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingSpecial() {
		RegistrationForm form = generateRegistrationForm();
		final String invalidPassword = "Ab3aasdfasdfasdf";
		form.setPassword(invalidPassword);
		form.setPasswordVerify(invalidPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingUpper() {
		RegistrationForm form = generateRegistrationForm();
		final String invalidPassword = "ab!3qwertyyu";
		form.setPassword(invalidPassword);
		form.setPasswordVerify(invalidPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingLower() {
		RegistrationForm form = generateRegistrationForm();
		final String invalidPassword = "AB3!QWERTYYU";
		form.setPassword(invalidPassword);
		form.setPasswordVerify(invalidPassword);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("password").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	/*

	 *          SCHOLASTIC INFORMATION

	 */

	@Test
	public void registrationService_ShouldThrowException_ForMissingMajor() {
		RegistrationForm form = generateRegistrationForm();
		form.setMajor(null);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
								assertThat(message.getMessage().size()).isOne();
								assertThat(message.getMessage().get("major").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingMinor() {
		RegistrationForm form = generateRegistrationForm();
		form.setMinor(null);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("minor").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForMissingYear() {
		RegistrationForm form = generateRegistrationForm();
		form.setYear(null);
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(message -> {
									assertThat(message.getMessage().size()).isOne();
									assertThat(message.getMessage().get("year").size()).isOne();
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	/*

	 *          PHONE NUMBER

	 */
	@Test
	public void registrationService_ShouldThrowException_ForMissingPhoneNumber() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("", "", ""));
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("phoneNumber").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidLineNumber() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("555", "555", "55555"));
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("phoneNumber").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidAreaCode() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("5555", "555", "5555"));
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("phoneNumber").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}

	@Test
	public void registrationService_ShouldThrowException_ForInvalidPrefix() {
		RegistrationForm form = generateRegistrationForm();
		form.setPhoneNumber(new PhoneNumber("555", "5555", "5555"));
		Mono<GlmsMember> result = registrationService.attemptRegistration(form);

		// Registration attempt should throw a RegistrationFormValidationException
		StepVerifier.create(result)
				.consumeErrorWith(err -> {
					assertThat(err).isInstanceOf(RegistrationFormValidationException.class);
					RegistrationFormValidationException ex = (RegistrationFormValidationException) err;

					// RegistrationFormValidationException should have 1 entry with 2 messages
					StepVerifier.create(ex.getValidationMessages())
							.assertNext(messages -> {
								assertThat(messages.getMessage().size()).isOne();
								assertThat(messages.getMessage().get("phoneNumber").size()).isEqualTo(1);
							})
							.expectComplete()
							.verifyThenAssertThat()
							.hasNotDroppedElements()
							.hasNotDroppedErrors();
				})
				.verifyThenAssertThat()
				.hasNotDroppedErrors()
				.hasNotDroppedElements();
	}
}
