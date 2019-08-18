package com.wang.struct.adapter;

public class TextShapeAdapter implements Shape, TextView {

	private TextView textView;

	public TextShapeAdapter(TextView textView) {
		this.textView = textView;
	}

	@Override
	public void boundingBox(Point bottomLeft, Point topRight) {
		Coord bottom = new Coord(), left= new Coord(), width= new Coord(), height= new Coord();
		getOrigin(bottom, left);
		getExtent(width, height);
		bottomLeft.setBottom(bottom.getCenter());
		bottomLeft.setLeft(left.getCenter());
		topRight.setBottom(height.getCenter() + bottom.getCenter());
		topRight.setLeft(left.getCenter() + width.getCenter());
	}

	@Override
	public Manipulator createManipulator() {
		return null;
	}

	@Override
	public void getOrigin(Coord x, Coord y) {

	}

	@Override
	public void getExtent(Coord width, Coord height) {

	}

	@Override
	public boolean isEmpty() {
		return textView.isEmpty();
	}
}
