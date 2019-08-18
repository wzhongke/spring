package com.wang.action.chain;

/**
 * 所有在窗口组件都是Widget的子类，Widget是HelpHandler的子类，因为所有的用户界面都可有相关的帮助
 */

public class Widget extends HelpHandler {
	public Widget(HelpHandler successor, int topic) {
		super(successor, topic);
	}
}
