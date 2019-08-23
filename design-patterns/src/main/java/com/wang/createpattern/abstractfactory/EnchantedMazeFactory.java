package com.wang.createpattern.abstractfactory;

import com.wang.createpattern.*;

public class EnchantedMazeFactory implements MazeFactory {
	@Override
	public Maze makeMaze() {
		return new EnchantedMaze();
	}

	@Override
	public Wall makeWall() {
		return new Wall();
	}

	@Override
	public Room makeRoom(int roomNo) {
		return new EnchantedRoom(roomNo);
	}

	@Override
	public Door makeDoor(Room r1, Room r2) {
		return new DoorNeedSpell(r1, r2);
	}
}
