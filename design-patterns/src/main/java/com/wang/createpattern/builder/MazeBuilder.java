package com.wang.createpattern.builder;

import com.wang.createpattern.Maze;

/**
 * MazeBuilder 的所有建造迷宫的操作缺省时什么也不做，不降他们定义为纯虚函数
 * 是为了便于派生类只重定义他们所感兴趣的那些方法
 */

public class MazeBuilder {
    public void buildMaze(){}
    public void buildRoom(int room){}
    public void buildDoor(int roomFrom, int roomTo){}
    public Maze getMaze(){return null;}
}
