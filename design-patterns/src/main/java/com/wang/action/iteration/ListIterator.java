package com.wang.action.iteration;

public class ListIterator<T> implements Iterator<T> {
	private List<T> list;
    private int current;

	ListIterator(List<T> aList) {
		this.list = aList;
		current = 0;
	}

	@Override
	public void first() {
		current = 0;
	}

	@Override
	public void next() {
		current ++;
	}

	@Override
	public boolean isDone() {
		return current >= list.size();
	}

	@Override
	public T currentItem() {
		if (isDone()) {
			return null;
		}
		return list.get(current);
	}
}
