package com.wang.struct.composite;

public class Client {
	public static void main(String [] args) {
		Chassis chassis = new Chassis("PC Chassis");
		FloppyDiskLeaf dis = new FloppyDiskLeaf("Floppy Disk");
		chassis.add(dis);
	}

}
