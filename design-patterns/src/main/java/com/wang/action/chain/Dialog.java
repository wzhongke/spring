package com.wang.action.chain;

public class Dialog extends  Widget {
	public Dialog(HelpHandler successor, int topic) {
		super(successor, topic);
	}

	@Override
	public void handleHelp() {
		System.out.println("Dialog help");
		if (hasHelp()) {
			// handle help
		} else {
			super.handleHelp();
		}
	}
}
