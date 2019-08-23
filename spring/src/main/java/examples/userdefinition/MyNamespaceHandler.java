package examples.userdefinition;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 定义解析标签
 * @author wangzhongke
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

	public MyNamespaceHandler() {
		System.out.println("MyNamespaceHandler init");
	}

	@Override
	public void init() {
		registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
	}
}
