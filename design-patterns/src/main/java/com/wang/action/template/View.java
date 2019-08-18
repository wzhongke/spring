package com.wang.action.template;

public abstract class View {

	public void display () {
		setFocus();
		doDisplay();
		resetFocus();
	}
	public void setFocus() {}
	protected abstract void doDisplay();
	public void resetFocus() {}
}
