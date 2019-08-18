package com.wang.action.observer;

public class AnalogClock implements Observer {

	private ClockTimer timer ;

	public AnalogClock(ClockTimer timer) {
		this.timer = timer;
		timer.attach(this);
	}

	@Override
	public void update (Subject subject) {
		if (this.timer == subject) {
			// do something
			System.out.println("AnalogClock update:" + timer.getHour() + ":" + timer.getMinute() + ":" + timer.getSecond());
		}
	}
}
