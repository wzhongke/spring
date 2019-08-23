package com.wang.struct.adapter;

/**
 * TextView 由原点，宽度，高度定义
 */
public interface TextView {
	void getOrigin(Coord x, Coord y);
	void getExtent (Coord width, Coord height);
	boolean isEmpty();
}
