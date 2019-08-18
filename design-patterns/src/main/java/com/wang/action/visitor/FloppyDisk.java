package com.wang.action.visitor;

import java.util.Random;

public class FloppyDisk extends Equipment {

	public FloppyDisk(String name) {
		super(name);
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		visitor.visitFloppyDisk(this);
	}

	public int netPrice() {
		return new Random().nextInt();
	}
}
