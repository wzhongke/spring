package com.wang.createpattern.prototype;

import com.wang.createpattern.Door;
import com.wang.createpattern.Maze;
import com.wang.createpattern.Room;
import com.wang.createpattern.Wall;
import com.wang.createpattern.abstractfactory.MazeFactory;

public class MazePrototypeFactory implements MazeFactory {

	private Maze prototypeMaze;
	private Room prototypeRoom;
	private Wall prototypeWall;
	private Door prototypeDoor;

	public MazePrototypeFactory(Maze prototypeMaze, Room prototypeRoom, Wall prototypeWall, Door prototypeDoor) {
		this.prototypeMaze = prototypeMaze;
		this.prototypeRoom = prototypeRoom;
		this.prototypeWall = prototypeWall;
		this.prototypeDoor = prototypeDoor;
	}

	@Override
	public Maze makeMaze() {
		return prototypeMaze.clone();
	}

	@Override
	public Wall makeWall() {
		return prototypeWall.clone();
	}

	@Override
	public Room makeRoom(int roomNo) {
		return prototypeRoom.clone();
	}

	@Override
	public Door makeDoor(Room r1, Room r2) {
		Door door = prototypeDoor.clone();
		door.initialize(r1, r2);
		return door;
	}
}
