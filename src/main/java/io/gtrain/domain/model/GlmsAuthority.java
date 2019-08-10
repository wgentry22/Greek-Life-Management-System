package io.gtrain.domain.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

/**
 * @author William Gentry
 */
public class GlmsAuthority implements GrantedAuthority {

	private final String authority;

	public GlmsAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GlmsAuthority that = (GlmsAuthority) o;
		return Objects.equals(authority, that.authority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authority);
	}
}
