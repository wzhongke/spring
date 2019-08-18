package com.wang.struct.composite;

import java.util.Iterator;

public class FloppyDiskLeaf extends EquipmentComponent {

	FloppyDiskLeaf(String name) {
		super(name);
	}

	@Override
	public int netPrice() { return 0; }

	@Override
	public int discountPrice() { return 0; }

	@Override
	public void add(EquipmentComponent e) {	}

	@Override
	public void remove(EquipmentComponent e) {	}

	@Override
	public Iterator<EquipmentComponent> createIterator() {return null;}
}
