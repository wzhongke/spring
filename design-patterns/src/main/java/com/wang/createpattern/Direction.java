package com.wang.createpattern;

public enum Direction {
	North(0), South(1), East(2), West(3);
	int pos;
	Direction(int pos) {
		this.pos = pos;
	}
	public int getPos () {
		return pos;
	}
}
