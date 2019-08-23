package com.wang.struct.bridge;

import com.wang.struct.adapter.Point;

public abstract class Window {
	/**  Window 维护一个对WindowImp的引用，WindowImp抽象类定义了一个对底层窗口系统的接口 */
	private WindowImp imp;
	private View contents;

	abstract void drawContents();
	abstract void open();
	abstract void close();
	abstract void iconify();
	abstract void deiconify();

	abstract void setOrigin(Point at);
	abstract void setExtent(Point extent);
	abstract void raise();
	abstract void lower();
	abstract void drawLine(Point start, Point end);
	abstract void drawRect(Point top, Point bottom);
}
