package io.gtrain.unit.registration;

import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.dto.RegistrationForm;
import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;
import io.gtrain.service.registration.RegistrationUtils;
import io.gtrain.unit.base.ApplicationUnitTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class RegistrationUtilsTest extends ApplicationUnitTestBase {

	private RegistrationForm registrationForm;

	@BeforeEach
	void initRegistrationForm() {
		registrationForm = generateRegistrationForm();
	}

	@Test
	public void registrationUtils_ShouldProperlyConvertRegistrationForm_IntoMember() {
		final GlmsMember member = RegistrationUtils.getMemberFromRegistrationForm(registrationForm);
		assertThat(member).isNotNull();
		assertThat(member.getUsername()).isEqualTo("test");
		assertThat(member.getEmail()).isEqualTo("test@email.com");
		assertThat(member.getPassword()).isEqualTo("Password123!");
		assertThat(member.getAuthorities().size()).isOne();
		assertThat(member.getAuthorities().stream().map(GrantedAuthority::getAuthority).allMatch(authority -> authority.equals("ROLE_VIEWER"))).isTrue();
		assertThat(member.getName().getFirstname()).isEqualTo("Test");
		assertThat(member.getName().getLastname()).isEqualTo("User");
		assertThat(member.getPhoneNumber().getValue()).isEqualTo("555-555-5555");
		assertThat(member.getScholasticInfo().getMajor()).isEqualTo(Major.MATH);
		assertThat(member.getScholasticInfo().getMinor()).isEqualTo(Minor.STATISTICS);
		assertThat(member.getScholasticInfo().getYear()).isEqualTo(Year.FRESHMAN);
	}
}
