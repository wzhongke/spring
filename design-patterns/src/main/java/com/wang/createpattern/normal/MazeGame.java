package com.wang.createpattern.normal;

import com.wang.createpattern.*;

public class MazeGame {
	Maze createMaze () {
		Maze maze = new Maze();
		Room r1 = new Room(1);
		Room r2 = new Room(2);
		Door theDoor = new Door(r1, r2);
		maze.addRoom(r1);
		maze.addRoom(r2);
		r1.setSide(Direction.North, new Wall());
		r1.setSide(Direction.South, theDoor);

		return maze;
	}
}
