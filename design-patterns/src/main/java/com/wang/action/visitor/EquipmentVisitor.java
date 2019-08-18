package com.wang.action.visitor;

public interface EquipmentVisitor {

	void visitFloppyDisk(FloppyDisk f);
	void visitChassis(Chassis c);
}
