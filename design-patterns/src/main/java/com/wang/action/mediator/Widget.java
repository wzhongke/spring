package com.wang.action.mediator;

public class Widget {

	private DialogDirector director;
	public Widget(DialogDirector d) {
		this.director = d;
	}

	public void changed() {
		this.director.widgetChanged(this);
	}
}
