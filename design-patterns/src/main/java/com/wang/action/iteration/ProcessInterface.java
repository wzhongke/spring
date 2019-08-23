package com.wang.action.iteration;

@FunctionalInterface
public interface ProcessInterface<T> {
	public void doWork(T t);
}
