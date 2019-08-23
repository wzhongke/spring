package com.wang.createpattern.factorymethod;

import com.wang.createpattern.Door;
import com.wang.createpattern.DoorNeedSpell;
import com.wang.createpattern.EnchantedRoom;
import com.wang.createpattern.Room;

public class EnchantedMazeGame extends MazeGame {
	@Override
	public Room makeRoom (int n) {
		System.out.println("EnchantedMazeGame");
		return new EnchantedRoom(n);
	}

	@Override
	public Door makeDoor (Room r1, Room r2) {
		return new DoorNeedSpell(r1, r2);
	}
}
