package com.wang.createpattern;

public class Room implements MapSite, Cloneable {
	private MapSite side[] = new MapSite[4];
	private int roomNo;
	public Room(){}

	public Room(int roomNo) {
		this.roomNo = roomNo;
	}
	public MapSite getSide(Direction d) { return side[d.getPos()]; }
	public void setSide (Direction d, MapSite m) {
		side[d.getPos()] = m;
	}
	@Override
	public void enter() {
	}

	@Override
	public Room clone() {
		Room room = null;
		try {
			room = (Room) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return room;
	}
}
