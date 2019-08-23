package com.wang.createpattern.abstractfactory;

import com.wang.createpattern.Door;
import com.wang.createpattern.Maze;
import com.wang.createpattern.Room;
import com.wang.createpattern.Wall;

/**
 * Created by 王忠珂 on 2016/10/6.
 */
public interface MazeFactory {
    Maze makeMaze();
    Wall makeWall();
    Room makeRoom(int roomNo);
    Door makeDoor(Room r1, Room r2);
}
