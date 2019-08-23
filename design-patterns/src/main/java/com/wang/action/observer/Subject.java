package com.wang.action.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhongke
 */
public class Subject {
	private List<Observer> os;

	public Subject() {
		os = new ArrayList<>();
	}

	public void attach(Observer observer) {
		os.add(observer);
	}

	public void remove(Observer observer) {
		os.remove(observer);
	}

	public void notifyObservers () {
		for (Observer o: os) {
			o.update(this);
		}
	}
}
