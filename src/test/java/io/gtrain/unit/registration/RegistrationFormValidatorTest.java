package io.gtrain.unit.registration;

import io.gtrain.domain.model.dto.LoginForm;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.validator.RegistrationFormValidator;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class RegistrationFormValidatorTest extends ApplicationUnitTestBase {

	private RegistrationFormValidator registrationFormValidator = new RegistrationFormValidator();

	@Test
	public void registrationFormValidator_ShouldSupportRegistrationForm() {
		assertThat(registrationFormValidator.supports(RegistrationForm.class)).isTrue();
	}

	@Test
	public void registrationFormValidator_ShouldNotSupportAnyOtherClass() {
		assertThat(registrationFormValidator.supports(LoginForm.class)).isFalse();
		assertThat(registrationFormValidator.supports(String.class)).isFalse();
		assertThat(registrationFormValidator.supports(LocalDateTime.class)).isFalse();
	}
}
