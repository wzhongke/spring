package com.wang.action.command;

import com.wang.action.chain.Application;

public class OpenCommand extends Command {

	private Application application;
	String response;

	public OpenCommand(Application application) {
		this.application = application;
	}

	private String askUser() {
		return "answer";
	}

	@Override
	public void execute() {
		String name = askUser();
		// do some thing
	}
}
