package com.wang.action.visitor;

public class PricingVisitor implements EquipmentVisitor {

	private int total = 0;
	@Override
	public void visitFloppyDisk(FloppyDisk f) {
		total += f.netPrice();
	}

	@Override
	public void visitChassis(Chassis c) {
		total += c.discountPrice();
	}
}
