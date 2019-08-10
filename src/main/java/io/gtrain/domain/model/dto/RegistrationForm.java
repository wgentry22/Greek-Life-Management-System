package io.gtrain.domain.model.dto;

import io.gtrain.domain.model.PhoneNumber;
import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class RegistrationForm {

	private String firstname;

	private String lastname;

	private String username;

	private String email;

	private String password;

	private String passwordVerify;

	private PhoneNumber phoneNumber;

	private Major major;

	private Minor minor;

	private Year year;

	public RegistrationForm() {}

	public RegistrationForm(String firstname, String lastname, String username, String email, String password, String passwordVerify, PhoneNumber phoneNumber, Major major, Minor minor, Year year) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.passwordVerify = passwordVerify;
		this.phoneNumber = phoneNumber;
		this.major = major;
		this.minor = minor;
		this.year = year;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Minor getMinor() {
		return minor;
	}

	public void setMinor(Minor minor) {
		this.minor = minor;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegistrationForm that = (RegistrationForm) o;
		return Objects.equals(firstname, that.firstname) &&
				Objects.equals(lastname, that.lastname) &&
				Objects.equals(username, that.username) &&
				Objects.equals(email, that.email) &&
				Objects.equals(password, that.password) &&
				Objects.equals(passwordVerify, that.passwordVerify) &&
				Objects.equals(phoneNumber, that.phoneNumber) &&
				major == that.major &&
				minor == that.minor &&
				year == that.year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, lastname, username, email, password, passwordVerify, phoneNumber, major, minor, year);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RegistrationForm.class.getSimpleName() + "[", "]")
				.add("firstname='" + firstname + "'")
				.add("lastname='" + lastname + "'")
				.add("username='" + username + "'")
				.add("email='" + email + "'")
				.add("password='" + password + "'")
				.add("passwordVerify='" + passwordVerify + "'")
				.add("phoneNumber=" + phoneNumber)
				.add("major=" + major)
				.add("minor=" + minor)
				.add("year=" + year)
				.toString();
	}
}
