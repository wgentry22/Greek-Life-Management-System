package io.gtrain.domain.model;

/**
 * @author William Gentry
 */

import com.nimbusds.jwt.JWTClaimsSet;
import io.gtrain.domain.model.dto.LoginForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
public class GlmsAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final boolean authenticated;

	public GlmsAuthenticationToken(UserDetails userDetails) {
		super(userDetails, "", userDetails.getAuthorities());
		this.authenticated = true;
	}

	public GlmsAuthenticationToken(LoginForm loginForm) {
		super(loginForm.getUsername(), loginForm.getPassword());
		authenticated = false;
	}

	public GlmsAuthenticationToken(JWTClaimsSet claimsSet) {
		super(claimsSet.getSubject(), "", Arrays.stream(((String) claimsSet.getClaim("aty")).split(",")).map(GlmsAuthority::new).collect(Collectors.toList()));
		this.authenticated = true;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}
}
