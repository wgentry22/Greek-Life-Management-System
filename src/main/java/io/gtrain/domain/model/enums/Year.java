package io.gtrain.domain.model.enums;

/**
 * @author William Gentry
 */
public enum Year {

	FRESHMAN("Freshman"), SOPHOMORE("Sophomore"), JUNIOR("Junior"), SENIOR("Senior");

	private final String name;

	Year(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
