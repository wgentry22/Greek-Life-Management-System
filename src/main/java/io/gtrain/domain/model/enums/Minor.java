package io.gtrain.domain.model.enums;

/**
 * @author William Gentry
 */
public enum Minor {

	COMP_SCI("Computer Science"), MATH("Mathematics"), STATISTICS("Statistics"), BIOLOGY("Biology"),
	CHEMISTRY("Chemistry"), ACCOUNTING("Accounting"), FINANCE("Finance"), MANAGEMENT("Management"),
	ECONOMICS("Economics"), NOT_APPLICABLE("N/A");

	private final String name;

	Minor(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
