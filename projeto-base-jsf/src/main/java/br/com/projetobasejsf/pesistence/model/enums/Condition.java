package br.com.projetobasejsf.pesistence.model.enums;

public enum Condition {

	LIKE("LIKE"), EQUAL("=");

	private String condition;

	Condition(String condition) {
		this.condition = condition;
	}

	public String getCondition() {
		return condition;
	}

}
