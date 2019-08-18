package com.wang.action.strategy;

/**
 * @author wangzhongke
 */
public class Composition {

	private Compositor compositor;

	public Composition(Compositor compositor) {
		this.compositor = compositor;
	}

	public void repair() {
		// do some action
		compositor.Compose();
		// do other thing
	}

	public void setCompositor(Compositor compositor) {
		this.compositor = compositor;
	}
}
