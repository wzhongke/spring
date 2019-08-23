package com.wang.createpattern;

public class Door implements MapSite, Cloneable{
	private Room room1;
	private Room room2;
	private boolean isOpen;
	public Door() {}
	public Door(Room room1, Room room2) {
		this.room1 = room1;
		this.room2 = room2;
	}
	public Room otherSideFrom (Room oRoom) {
		return room1;
	}
	@Override
	public void enter() {

	}

	public void initialize(Room r1, Room r2) {
		this.room1 = r1;
		this.room2 = r2;
	}

	@Override
	public Door clone() {
		Door door = null;
		try {
			door = (Door) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return door;
	}
}
