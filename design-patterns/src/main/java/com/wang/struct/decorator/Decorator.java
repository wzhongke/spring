package com.wang.struct.decorator;

/**
 * Decorator 装饰由component实例变量引用的VisualComponent，这个变量需要在构造器中初始化
 */
public class Decorator extends VisualComponent {
	private VisualComponent component;
	Decorator (VisualComponent component) {
		this.component = component;
	}

	@Override
	public void draw() {
		component.draw();
	}

	@Override
	public void resize() {
		component.resize();
	}
}
