package io.gtrain.service.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.validator.RegistrationFormValidator;
import io.gtrain.exception.RegistrationFormValidationException;
import io.gtrain.service.email.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class GlmsRegistrationService implements RegistrationService {

	private final MemberRegistrationService memberRegistrationService;
	private final EmailService emailService;
	private final Validator validator = new RegistrationFormValidator();

	public GlmsRegistrationService(MemberRegistrationService memberRegistrationService, EmailService emailService) {
		this.memberRegistrationService = memberRegistrationService;
		this.emailService = emailService;
	}

	@Override
	public Mono<GlmsMember> attemptRegistration(RegistrationForm form) {
		Errors errors = new BeanPropertyBindingResult(form, RegistrationForm.class.getName());
		validator.validate(form, errors);
		if (!errors.getFieldErrors().isEmpty())
			return Mono.defer(() -> Mono.error(() -> new RegistrationFormValidationException(errors)));
		return memberRegistrationService.registerUser(form).flatMap(emailService::sendEmail);
	}
}
