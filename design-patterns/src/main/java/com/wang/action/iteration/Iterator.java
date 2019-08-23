package com.wang.action.iteration;

public interface Iterator<T> {
	void first();
	void next();
	boolean isDone();
	T currentItem();
}
