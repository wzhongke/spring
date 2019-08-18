package com.wang.struct.flyweight;

import com.sun.javafx.font.Glyph;
import com.sun.javafx.geom.RectBounds;
import com.sun.javafx.geom.Shape;

public abstract class Charactre implements Glyph {

	private char charCode;

	abstract void draw();
}
