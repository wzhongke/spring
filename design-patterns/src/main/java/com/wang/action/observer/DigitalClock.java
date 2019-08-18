package com.wang.action.observer;

public class DigitalClock implements Observer {

	private ClockTimer timer ;

	public DigitalClock(ClockTimer timer) {
		this.timer = timer;
		timer.attach(this);
	}

	@Override
	public void update (Subject subject) {
		if (this.timer == subject) {
			// do something
			System.out.println("DigitalClock update:" + timer.getHour() + ":" + timer.getMinute() + ":" + timer.getSecond());
		}
	}
}
