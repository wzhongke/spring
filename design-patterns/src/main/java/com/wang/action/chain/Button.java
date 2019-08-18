package com.wang.action.chain;

public class Button extends Widget {

	public Button(HelpHandler successor, int topic) {
		super(successor, topic);
	}

	@Override
	public void handleHelp() {
		if (hasHelp()) {
			super.handleHelp();
			// handle button help
		} else {
			super.handleHelp();
		}
	}
}
