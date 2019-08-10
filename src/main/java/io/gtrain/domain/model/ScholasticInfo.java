package io.gtrain.domain.model;

import io.gtrain.domain.model.enums.Major;
import io.gtrain.domain.model.enums.Minor;
import io.gtrain.domain.model.enums.Year;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class ScholasticInfo {

	private Major major;

	private Minor minor;

	private Year year;

	public ScholasticInfo() {}

	public ScholasticInfo(Major major, Minor minor, Year year) {
		this.major = major;
		this.minor = minor;
		this.year = year;
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
		ScholasticInfo that = (ScholasticInfo) o;
		return major == that.major &&
				minor == that.minor &&
				year == that.year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(major, minor, year);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ScholasticInfo.class.getSimpleName() + "[", "]")
				.add("major=" + major)
				.add("minor=" + minor)
				.add("year=" + year)
				.toString();
	}
}
