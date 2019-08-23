package com.wang.action.iteration;

import java.util.ArrayList;

/**
 * @author wangzhongke
 */
public class List<T> {
	private ArrayList<T> list;
	private int size;
	List(int size) {
		list = new ArrayList<>();
		this.size = size;
	}
	int count() {
		return this.size;
	}

	int size() {
		return this.list.size();
	}

	T get(int index) {
		return list.get(index);
	}

	void add(T item) {
		if (list.size() >= this.size) {
			return ;
		}
		list.add(item);
	}
	Iterator<T> createIterator() {
		return new ListIterator<>(this);
	}
	// ...
}
