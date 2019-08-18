package com.wang.action.iteration;

public class ListTraverser<T> {

	private ListIterator<T> iterator;
	ListTraverser(List<T> aList) {
		this.iterator = new ListIterator<>(aList);
	}

	public void processItem(ProcessInterface<T> process) {
		for (iterator.first();!iterator.isDone();iterator.next()) {
			process.doWork(iterator.currentItem());
		}
	}
}
