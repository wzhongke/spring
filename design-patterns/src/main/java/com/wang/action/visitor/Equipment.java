package com.wang.action.visitor;

public abstract class Equipment {

	private String name;

	public Equipment(String name) {
		this.name = name;
	}

	public abstract void accept(EquipmentVisitor visitor);

	public String getName() {
		return name;
	}
}
