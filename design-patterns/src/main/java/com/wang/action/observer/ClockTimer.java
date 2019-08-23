package com.wang.action.observer;

import java.time.LocalDateTime;

public class ClockTimer extends Subject {

	private LocalDateTime time = LocalDateTime.now();

	public int getHour() {
		return time.getHour();
	}

	public int getMinute() {
		return time.getMinute();
	}

	public int getSecond () {
		return time.getSecond();
	}

	public void tick () {
		time = LocalDateTime.now();
		notifyObservers();
	}
}
