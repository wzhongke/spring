package com.wang.action.iteration;

public class Client {

	public static void main(String [] args) {
		List<String> names = new List<>(4);
		names.add("First name");
		names.add("Last name");

		Iterator<String> iterator = new ListIterator<>(names);
		for (iterator.first(); !iterator.isDone(); iterator.next()) {
			System.out.println(iterator.currentItem());
		}

		ListTraverser<String> traverser = new ListTraverser<>(names);
		traverser.processItem((item)-> System.out.println("name:" + item));

	}
}
