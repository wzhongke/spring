package examples.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class CustomScope implements Scope {
	/**
	 * 返回作用域范围内的对象
	 */
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		return null;
	}

	/**
	 * 从作用域范围内移除对象
	 */
	@Override
	public Object remove(String name) {
		return null;
	}

	/**
	 * 对象销毁时，调用其注册的销毁方法
	 */

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
	}

	/**
	 * 获取上下文中的对象
	 */
	@Override
	public Object resolveContextualObject(String key) {
		return null;
	}

	/**
	 * 作用域的标识，不同的作用域返回标识不同
	 */
	@Override
	public String getConversationId() {
		return null;
	}
}
