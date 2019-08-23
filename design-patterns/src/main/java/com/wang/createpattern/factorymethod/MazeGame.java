package com.wang.createpattern.factorymethod;

import com.wang.createpattern.*;

/**
 * MazeGame 中提供了一些缺省的实现
 */
public class MazeGame {
	public Maze makeMaze() {
		return new Maze();
	}

	public Room makeRoom (int n) {
		return new Room(n);
	}

	public Wall makeWall () {
		return new Wall();
	}

	public Door makeDoor (Room r1, Room r2) {
		return new Door(r1, r2);
	}

	/**
	 * 用工厂方法重新 createMaze
	 */
	public Maze createMaze () {
		Maze maze = makeMaze();
		Room r1 = makeRoom(1);
		Room r2 = makeRoom(2);
		Door theDoor = makeDoor(r1, r2);
		maze.addRoom(r1);
		maze.addRoom(r2);
		r1.setSide(Direction.North, makeWall());
		r1.setSide(Direction.East, theDoor);
		r1.setSide(Direction.South, makeWall());
		r1.setSide(Direction.West, makeWall());

		return maze;
	}
}
