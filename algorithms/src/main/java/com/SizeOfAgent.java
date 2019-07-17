package com;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

public class SizeOfAgent {
	static Instrumentation instrumentation;

	public SizeOfAgent() {
	}

	public static void premain(String agentArgs, Instrumentation instP) {
		instrumentation = instP;
	}

	public static long sizeOf(Object o) {
		if (instrumentation == null) {
			throw new IllegalStateException("Can not access instrumentation environment.\nPlease check if jar file containing SizeOfAgent class is \nspecified in the java's \"-javaagent\" command line argument.");
		} else {
			return instrumentation.getObjectSize(o);
		}
	}

	public static long fullSizeOf(Object obj) {
		Map<Object, Object> visited = new IdentityHashMap();
		Stack<Object> stack = new Stack();

		long result;
		for(result = internalSizeOf(obj, stack, visited); !stack.isEmpty(); result += internalSizeOf(stack.pop(), stack, visited)) {
			;
		}

		visited.clear();
		return result;
	}

	private static boolean skipObject(Object obj, Map<Object, Object> visited) {
		if (obj instanceof String && obj == ((String)obj).intern()) {
			return true;
		} else {
			return obj == null || visited.containsKey(obj);
		}
	}

	private static long internalSizeOf(Object obj, Stack<Object> stack, Map<Object, Object> visited) {
		if (skipObject(obj, visited)) {
			return 0L;
		} else {
			visited.put(obj, (Object)null);
			long result = 0L;
			result += sizeOf(obj);
			Class clazz = obj.getClass();
			int i;
			if (clazz.isArray()) {
				if (clazz.getName().length() != 2) {
					int length = Array.getLength(obj);

					for(i = 0; i < length; ++i) {
						stack.add(Array.get(obj, i));
					}
				}

				return result;
			} else {
				while(clazz != null) {
					Field[] fields = clazz.getDeclaredFields();

					for(i = 0; i < fields.length; ++i) {
						if (!Modifier.isStatic(fields[i].getModifiers()) && !fields[i].getType().isPrimitive()) {
							fields[i].setAccessible(true);

							try {
								Object objectToAdd = fields[i].get(obj);
								if (objectToAdd != null) {
									stack.add(objectToAdd);
								}
							} catch (IllegalAccessException var9) {
								assert false;
							}
						}
					}

					clazz = clazz.getSuperclass();
				}

				return result;
			}
		}
	}
}