package com.wang.action.mediator;


public abstract class DialogDirector {
	public abstract void showDialog();
	public abstract void widgetChanged(Widget widget);
	protected abstract void createWidgets();
}
