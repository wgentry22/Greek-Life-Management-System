package io.gtrain.domain.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class Name {

	private String firstname;

	private String lastname;

	public Name() {}

	public Name(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Name name = (Name) o;
		return Objects.equals(firstname, name.firstname) &&
				Objects.equals(lastname, name.lastname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, lastname);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Name.class.getSimpleName() + "[", "]")
				.add("firstname='" + firstname + "'")
				.add("lastname='" + lastname + "'")
				.toString();
	}
}
