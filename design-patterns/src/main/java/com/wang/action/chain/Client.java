package com.wang.action.chain;

public class Client {

	private static int PRINT_TOPIC = 1;
	private static int PAPER_ORIENTATION_TOPIC = 2;
	private static int APPLICATION_TOPIC = 3;

	public static void main(String [] args) {
		Application application = new Application(APPLICATION_TOPIC);

		Dialog dialog = new Dialog(application, PRINT_TOPIC);
		Button button = new Button(dialog, PAPER_ORIENTATION_TOPIC);

		button.handleHelp();

		System.out.println(0x01 << (1 - 1));
	}
}
