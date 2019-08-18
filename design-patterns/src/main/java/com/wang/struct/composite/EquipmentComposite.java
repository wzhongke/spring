package com.wang.struct.composite;

import java.util.Iterator;
import java.util.List;

public abstract class EquipmentComposite extends EquipmentComponent {
	private List<EquipmentComponent> equipments;
	public EquipmentComposite (String name){
		super(name);
	}

	@Override
	public Iterator<EquipmentComponent> createIterator() {
		return equipments.iterator();
	}

	@Override
	public int netPrice() {
		Iterator<EquipmentComponent> it = createIterator();
		int total = 0;
		while(it.hasNext()) {
			total += it.next().netPrice();
		}
		return total;
	}

	@Override
	public void add(EquipmentComponent e) {
		equipments.add(e);
	}
	@Override
	public void remove(EquipmentComponent e) {
		equipments.remove(e);
	}
}
