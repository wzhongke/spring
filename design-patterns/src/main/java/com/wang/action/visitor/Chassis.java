package com.wang.action.visitor;

import java.util.List;
import java.util.Random;

public class Chassis extends Equipment {

	private List<Equipment>  equipments;

	public Chassis(String name) {
		super(name);
	}

	public int discountPrice() {
		return new Random().nextInt();
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		for (Equipment equipment: equipments) {
			equipment.accept(visitor);
		}
	}
}
