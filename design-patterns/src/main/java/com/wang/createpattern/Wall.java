package com.wang.createpattern;

public class Wall implements MapSite, Cloneable{
	@Override
	public void enter() {
	}

	@Override
	public Wall clone() {
		Wall wall = null;
		try {
			wall = (Wall) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return wall;
	}
}
