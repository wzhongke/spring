package com.wang.createpattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maze implements Cloneable {
	private List<Room> rooms = new ArrayList<Room>();
	public void addRoom (Room room) {
		rooms.add(room);
	}
	public Room getRoom (int i) {
		return rooms.get(i);
	}
	public List<Room> getRooms () {return rooms;}

	@Override
	public Maze clone() {
		Maze maze = null;
		try {
			maze = (Maze) super.clone();
			// 需要注意浅拷贝和深拷贝
			maze.rooms = new ArrayList<>(this.rooms.size());
			Collections.copy(maze.rooms, this.rooms);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return maze;
	}
}
