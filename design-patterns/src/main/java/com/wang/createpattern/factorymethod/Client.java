package com.wang.createpattern.factorymethod;


public class Client {
	public static void main(String [] args) {
		MazeGame game = new EnchantedMazeGame();
		game.createMaze();
	}
}
