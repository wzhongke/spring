package com.wang.action.command;

/**
 *  **对于简单的不能取消和不需要参数的命令**，可以用一个泛型来参数化该命令的接收者。
 * 用Receiver类型参数化SimpleCommand，并维护一个接收者对象和一个动作之间的绑定，而这一动作是用指向一个成员函数的指针存储的
 */
public class SimpleCommand extends Command {

	Command receiver;

	SimpleCommand(Command action) {
		this.receiver = action;
	}
	@Override
	public void execute() {
		receiver.execute();
	}
}
