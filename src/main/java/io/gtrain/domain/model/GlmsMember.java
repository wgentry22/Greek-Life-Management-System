package io.gtrain.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gtrain.domain.interfaces.ContactableMember;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
@Document(collection = "glms_member")
public class GlmsMember implements ContactableMember, UserDetails {

	@Id
	private ObjectId id;

	@Indexed
	private String username;

	@Indexed
	private String email;

	private PhoneNumber phoneNumber;

	private String password;

	private Collection<GlmsAuthority> authorities;

	private boolean enabled;

	private boolean accountNonLocked;

	private boolean accountNonExpired;

	private boolean credentialsNonExpired;

	private ScholasticInfo scholasticInfo;

	private Name name;

	@PersistenceConstructor
	public GlmsMember(String username, String email, PhoneNumber phoneNumber, String password, Collection<GlmsAuthority> authorities, boolean enabled, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired, ScholasticInfo scholasticInfo, Name name) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.accountNonLocked = accountNonLocked;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.scholasticInfo = scholasticInfo;
		this.name = name;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GlmsAuthority> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public ScholasticInfo getScholasticInfo() {
		return scholasticInfo;
	}

	public void setScholasticInfo(ScholasticInfo scholasticInfo) {
		this.scholasticInfo = scholasticInfo;
	}

	@Override
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GlmsMember that = (GlmsMember) o;
		return enabled == that.enabled &&
				accountNonLocked == that.accountNonLocked &&
				accountNonExpired == that.accountNonExpired &&
				credentialsNonExpired == that.credentialsNonExpired &&
				Objects.equals(id, that.id) &&
				Objects.equals(username, that.username) &&
				Objects.equals(email, that.email) &&
				Objects.equals(phoneNumber, that.phoneNumber) &&
				Objects.equals(password, that.password) &&
				Objects.equals(authorities, that.authorities) &&
				Objects.equals(scholasticInfo, that.scholasticInfo) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, phoneNumber, password, authorities, enabled, accountNonLocked, accountNonExpired, credentialsNonExpired, scholasticInfo, name);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", GlmsMember.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("username='" + username + "'")
				.add("email='" + email + "'")
				.add("phoneNumber=" + phoneNumber)
				.add("password='" + password + "'")
				.add("authorities=" + authorities)
				.add("enabled=" + enabled)
				.add("accountNonLocked=" + accountNonLocked)
				.add("accountNonExpired=" + accountNonExpired)
				.add("credentialsNonExpired=" + credentialsNonExpired)
				.add("scholasticInfo=" + scholasticInfo)
				.add("name=" + name)
				.toString();
	}
}
