package io.gtrain.domain.model.dto;

import io.gtrain.domain.model.Name;
import io.gtrain.domain.model.ScholasticInfo;
import io.gtrain.domain.interfaces.ContactableMember;
import io.gtrain.domain.model.PhoneNumber;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class ContactableMemberDTO {

	private PhoneNumber phoneNumber;

	private String email;

	private ScholasticInfo scholasticInfo;

	private Name name;

	public ContactableMemberDTO() {}

	public ContactableMemberDTO(ContactableMember member) {
		this.phoneNumber = member.getPhoneNumber();
		this.email = member.getEmail();
		this.scholasticInfo = member.getScholasticInfo();
		this.name = member.getName();
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ScholasticInfo getScholasticInfo() {
		return scholasticInfo;
	}

	public void setScholasticInfo(ScholasticInfo scholasticInfo) {
		this.scholasticInfo = scholasticInfo;
	}

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
		ContactableMemberDTO that = (ContactableMemberDTO) o;
		return Objects.equals(phoneNumber, that.phoneNumber) &&
				Objects.equals(email, that.email) &&
				Objects.equals(scholasticInfo, that.scholasticInfo) &&
				Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneNumber, email, scholasticInfo, name);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ContactableMemberDTO.class.getSimpleName() + "[", "]")
				.add("phoneNumber=" + phoneNumber)
				.add("email='" + email + "'")
				.add("scholasticInfo=" + scholasticInfo)
				.add("name=" + name)
				.toString();
	}
}
