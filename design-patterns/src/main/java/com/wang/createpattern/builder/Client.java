package com.wang.createpattern.builder;

import com.wang.createpattern.Maze;

/**
 * Created by 王忠珂 on 2016/10/7.
 */
public class Client {

    /**
     * 生成器隐藏了迷宫的内部表示
     * @param builder
     * @return
     */
    public Maze createMaze(MazeBuilder builder) {
        builder.buildMaze();
        builder.buildRoom(1);
        builder.buildRoom(2);
        builder.buildDoor(1,2);

        return builder.getMaze();
    }
    public static void main(String[] args){
        StandardMazeBuilder builder = new StandardMazeBuilder();
        Client client = new Client();
        client.createMaze(builder);

    }
}
