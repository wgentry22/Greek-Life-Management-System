package io.gtrain.domain.model;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class PhoneNumber {

	private String areaCode;
	private String prefix;
	private String lineNumber;

	public PhoneNumber() {}

	public PhoneNumber(String areaCode, String prefix, String lineNumber) {
		this.areaCode = areaCode;
		this.prefix = prefix;
		this.lineNumber = lineNumber;
	}

	public String getValue() {
		if (StringUtils.hasText(areaCode) && StringUtils.hasText(prefix) && StringUtils.hasText(lineNumber)) {
			StringBuilder builder = new StringBuilder();
			builder.append(areaCode);
			builder.append("-");
			builder.append(prefix);
			builder.append("-");
			builder.append(lineNumber);
			return builder.toString();
		}
		return null;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PhoneNumber that = (PhoneNumber) o;
		return Objects.equals(areaCode, that.areaCode) &&
				Objects.equals(prefix, that.prefix) &&
				Objects.equals(lineNumber, that.lineNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaCode, prefix, lineNumber);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", PhoneNumber.class.getSimpleName() + "[", "]")
				.add("areaCode='" + areaCode + "'")
				.add("prefix='" + prefix + "'")
				.add("lineNumber='" + lineNumber + "'")
				.toString();
	}
}
