package com.wang.action.observer;

public class Client {

	public static void main(String [] args) throws InterruptedException {
		ClockTimer timer = new ClockTimer();
		AnalogClock analogClock = new AnalogClock(timer);
		DigitalClock digitalClock = new DigitalClock(timer);
		while (true) {
			Thread.sleep(1000);
			timer.tick();
		}
	}
}
