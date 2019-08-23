package com.wang.createpattern.builder;

import com.wang.createpattern.*;

import java.nio.file.DirectoryStream;

/**
 * Created by 王忠珂 on 2016/10/7.
 */
public class StandardMazeBuilder extends MazeBuilder{

    public StandardMazeBuilder(){}
    @Override
    public void buildMaze(){
        currentMaze = new Maze();
    }
    @Override
    public void buildRoom(int n){
        if(currentMaze.getRoom(n) == null) {
            Room room = new Room(n);
            currentMaze.addRoom(room);

            room.setSide(Direction.North, new Wall());
            room.setSide(Direction.South, new Wall());
            room.setSide(Direction.East, new Wall());
            room.setSide(Direction.West, new Wall());
        }
    }
    @Override
    public void buildDoor(int roomFrom, int roomTo){
        Room room1 = currentMaze.getRoom(roomFrom);
        Room room2 = currentMaze.getRoom(roomTo);
        Door d = new Door(room1, room2);

        room1.setSide(commonWall(room1, room2), d);
        room2.setSide(commonWall(room2, room1), d);
    }
    @Override
    public Maze getMaze(){return currentMaze;}
    private Direction commonWall(Room r1, Room r2){
        return Direction.South;
    }


    private Direction direction;
    private Maze currentMaze;
}
