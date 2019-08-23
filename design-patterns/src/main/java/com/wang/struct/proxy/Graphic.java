package com.wang.struct.proxy;

import com.wang.struct.adapter.Point;

/**
 * @author wangzhongke
 */
public interface Graphic {
	void draw();
	void handleMouse();
	void load();
	void save();
	String getExtent();
	default void move(Point p){}
}
