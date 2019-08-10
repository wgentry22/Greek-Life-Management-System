package io.gtrain.domain.model.enums;

/**
 * @author William Gentry
 */
public enum Major {

	COMP_SCI("Computer Science"), MATH("Mathematics"), STATISTICS("Statistics"), BIOLOGY("Biology"),
	CHEMISTRY("Chemistry"), ACCOUNTING("Accounting"), FINANCE("Finance"), MANAGEMENT("Management"),
	ECONOMICS("Economics");

	private final String name;

	Major(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
