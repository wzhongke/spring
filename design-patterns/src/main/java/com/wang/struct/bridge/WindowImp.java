package com.wang.struct.bridge;

import com.wang.struct.adapter.Coord;

public abstract class WindowImp {
	abstract void impTop();
	abstract void impBottom();
	abstract void impSetExtent();

	abstract void deviceRect(Coord c1, Coord c2, Coord c3, Coord c4);
	abstract void deviceText(char c, Coord c1, Coord c2);
}
