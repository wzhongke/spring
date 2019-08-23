package com.wang.struct.composite;

import java.util.Iterator;

/**
 * Equipment为 部分-整体 层次结构中的所有设备定义一个接口
 */
public abstract class EquipmentComponent {
	private String name;

	EquipmentComponent(String name) {
		this.name = name;
	}

	public String getName() {return name;}
	public abstract int netPrice();
	public abstract int discountPrice();

	public abstract void add(EquipmentComponent e);
	public abstract void remove(EquipmentComponent e);

	public abstract Iterator<EquipmentComponent> createIterator();

}
