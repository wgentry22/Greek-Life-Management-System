package io.gtrain.domain.model.dto;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class Address {

	private String street;
	private String city;
	private String state;
	private String zipCode;

	@PersistenceConstructor
	public Address(String street, String city, String state, String zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(street, address.street) &&
				Objects.equals(city, address.city) &&
				Objects.equals(state, address.state) &&
				Objects.equals(zipCode, address.zipCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(street, city, state, zipCode);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
				.add("street='" + street + "'")
				.add("city='" + city + "'")
				.add("state='" + state + "'")
				.add("zipCode='" + zipCode + "'")
				.toString();
	}
}
