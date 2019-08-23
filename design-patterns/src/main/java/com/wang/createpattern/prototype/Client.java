package com.wang.createpattern.prototype;

import com.wang.createpattern.*;
import com.wang.createpattern.abstractfactory.MazeGame;

public class Client {
	public static void main(String [] args) {
		MazeGame game = new MazeGame();
		MazePrototypeFactory simpleMazeFactory = new MazePrototypeFactory(new Maze(), new Room(), new Wall(), new Door());
		Maze maze = game.createMaze(simpleMazeFactory);

		Maze maze2 = game.createMaze(simpleMazeFactory);
		System.out.println(maze.getRooms());
		System.out.println(maze2.getRooms());

		MazePrototypeFactory enchantedFactory = new MazePrototypeFactory(new EnchantedMaze(), new EnchantedRoom(),
			new Wall(), new DoorNeedSpell());
		Maze maze1 = game.createMaze(enchantedFactory);
	}

}
