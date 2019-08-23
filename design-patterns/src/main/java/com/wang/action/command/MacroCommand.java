package com.wang.action.command;

import java.util.List;

public class MacroCommand extends Command {

	private List<Command> cmds;

	public void remove(Command c) {
		cmds.remove(c);
	}

	public void add(Command c) {
		cmds.add(c);
	}

	@Override
	public void execute() {
		for (Command command: cmds) {
			command.execute();
		}
	}
}
