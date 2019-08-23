package com.wang.action.chain;


public class Application extends HelpHandler {

	public Application(int topic) {
		this(null, 1);
	}

	public Application(HelpHandler successor, int topic) {
		super(successor, topic);
	}

	@Override
	public void handleHelp() {
		// show a list of help
	}
}
