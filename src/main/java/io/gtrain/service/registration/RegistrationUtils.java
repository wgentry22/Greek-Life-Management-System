package io.gtrain.service.registration;

import io.gtrain.domain.model.GlmsAuthority;
import io.gtrain.domain.model.GlmsMember;
import io.gtrain.domain.model.Name;
import io.gtrain.domain.model.ScholasticInfo;
import io.gtrain.domain.model.dto.RegistrationForm;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Arrays;

/**
 * @author William Gentry
 */
public class RegistrationUtils {

	private RegistrationUtils() {}

	public static GlmsMember getMemberFromRegistrationForm(RegistrationForm form) {
		return new GlmsMember(form.getUsername(), form.getEmail(), form.getPhoneNumber(), form.getPassword(),
				Arrays.asList(new GlmsAuthority("ROLE_VIEWER")), false, true, true, true,
				new ScholasticInfo(form.getMajor(), form.getMinor(), form.getYear()), new Name(form.getFirstname(), form.getLastname()));
	}
}
