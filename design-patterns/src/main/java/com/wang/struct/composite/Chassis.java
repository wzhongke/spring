package com.wang.struct.composite;

public class Chassis extends EquipmentComposite {
	public Chassis(String name) {
		super(name);
	}

	@Override
	public int discountPrice() {
		return 0;
	}

	@Override
	public void add(EquipmentComponent e) {

	}

	@Override
	public void remove(EquipmentComponent e) {

	}
}
