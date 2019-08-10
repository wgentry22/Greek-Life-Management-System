package io.gtrain.unit.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtrain.domain.model.*;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;
import io.gtrain.domain.repository.MemberRepositoryImpl;
import io.gtrain.security.GlmsAuthenticationManager;
import io.gtrain.security.GlmsUserDetailsPasswordService;
import io.gtrain.security.GlmsUserDetailsService;
import io.gtrain.service.email.OutgoingVerificationEmailService;
import io.gtrain.service.registration.DefaultMemberRegistrationService;
import io.gtrain.service.registration.GlmsRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.Arrays;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author William Gentry
 */
@ExtendWith(MockitoExtension.class)
public class ApplicationUnitTestBase {

	private final String RAW_PASSWORD = "Password123!";
	private final String ENCODED_PASSWORD = "$2a$13$l3iT7lZ/LbeGZrCL1LGMV.Q8AyHbnim5.uKASwcj06rA0RpwZk6mS";

	protected GlmsUserDetailsService userDetailsService;

	protected GlmsAuthenticationManager authenticationManager;

	protected GlmsRegistrationService registrationService;

	protected final Logger logger = Loggers.getLogger(getClass());

	protected final ObjectMapper mapper = new ObjectMapper();

	protected final GlmsMember VALID_MEMBER = new GlmsMember("test", "william.gentry02@gmail.com", new PhoneNumber("555", "555", "5555"),
			ENCODED_PASSWORD, Arrays.asList(new GlmsAuthority("ROLE_VIEWER")), true, true, true, true,
			new ScholasticInfo(Major.MATH, Minor.STATISTICS, Year.FRESHMAN), new Name("Test", "User"));

	private final RegistrationForm VALID_REGISTRATION_FORM = new RegistrationForm("Test", "User", "test", "test@email.com", "Password123!", "Password123!", new PhoneNumber("555", "555", "5555"), Major.MATH, Minor.STATISTICS, Year.FRESHMAN);

	@BeforeEach
	void initUserDetailsService(@Mock MemberRepositoryImpl memberRepository) {
		userDetailsService = new GlmsUserDetailsService(memberRepository);

		Mockito.lenient().when(memberRepository.findMemberByUsername("test")).thenReturn(Mono.just(generateValidMember()));
		Mockito.lenient().when(memberRepository.findMemberByUsername(not(eq("test")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(memberRepository.findMemberByEmail("test@email.com")).thenReturn(Mono.just(generateValidMember()));
		Mockito.lenient().when(memberRepository.findMemberByEmail(not(eq("test@email.com")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));
	}

	@BeforeEach
	void initAuthenticationManager(@Mock MemberRepositoryImpl memberRepository, @Mock PasswordEncoder passwordEncoder, @Mock GlmsUserDetailsPasswordService passwordService) {
		userDetailsService = new GlmsUserDetailsService(memberRepository);
		authenticationManager = new GlmsAuthenticationManager(userDetailsService, passwordEncoder, passwordService);

		Mockito.lenient().when(memberRepository.findMemberByUsername("test")).thenReturn(Mono.just(generateValidMember()));
		Mockito.lenient().when(memberRepository.findMemberByUsername(not(eq("test")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(memberRepository.findMemberByEmail("test@email.com")).thenReturn(Mono.just(generateValidMember()));
		Mockito.lenient().when(memberRepository.findMemberByEmail(not(eq("test@email.com")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
		Mockito.lenient().when(passwordEncoder.upgradeEncoding(ENCODED_PASSWORD)).thenReturn(false);
	}

	@BeforeEach
	void initRegistrationService(@Mock DefaultMemberRegistrationService memberRegistrationService, @Mock OutgoingVerificationEmailService emailService) {
		registrationService = new GlmsRegistrationService(memberRegistrationService, emailService);

		Mockito.lenient().when(memberRegistrationService.registerUser(generateRegistrationForm())).thenReturn(Mono.just(generateValidMember()));
		Mockito.lenient().when(emailService.sendEmail(VALID_MEMBER)).thenReturn(Mono.just(VALID_MEMBER));
	}

	protected GlmsMember generateValidMember() {
		return VALID_MEMBER;
	}

	protected RegistrationForm generateRegistrationForm() {
		return VALID_REGISTRATION_FORM;
	}
}
