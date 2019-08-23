package com.wang.createpattern.abstractfactory;

import com.wang.createpattern.Direction;
import com.wang.createpattern.Door;
import com.wang.createpattern.Maze;
import com.wang.createpattern.Room;

public class MazeGame {

	public Maze createMaze(MazeFactory factory) {
		Maze maze = factory.makeMaze();
		Room room1 = factory.makeRoom(1);
		Room room2 = factory.makeRoom(2);
		Door door = factory.makeDoor(room1, room2);
		maze.addRoom(room1);
		maze.addRoom(room2);

		room1.setSide(Direction.North, factory.makeWall());
		room1.setSide(Direction.South, door);

		return maze;
	}
}
