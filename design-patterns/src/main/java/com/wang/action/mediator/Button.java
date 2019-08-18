package com.wang.action.mediator;

public class Button extends Widget {
	public Button(DialogDirector d) {
		super(d);
	}
	void handleMouse () {
		//...
		// 调用通信接口
		changed();
	}
}
