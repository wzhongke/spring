package com.wang.struct.adapter;

public interface Shape {
	void boundingBox (Point bottomLeft, Point topRight);
	Manipulator createManipulator ();
}
