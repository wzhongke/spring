package com.wang.action.chain;

public class HelpHandler {
	public static int NO_HELP_TOPIC = -1;

	private HelpHandler successor;

	private int topic;

	public HelpHandler(HelpHandler successor, int topic) {
		this.successor = successor;
		this.topic = topic;
	}

	public boolean hasHelp() {
		return topic != NO_HELP_TOPIC;
	}

	public void handleHelp() {
		System.out.println("HelpHandler help");
		if (successor != null) {
			successor.handleHelp();
		}
	}
}
