package com.wang.struct.decorator;

public class Client {

	public static void main(String [] args) {
		VisualComponent component = new BorderDecorator(new TextView(), 1);
	}
}
