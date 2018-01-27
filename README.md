---
title: spring 学习笔记
date: 2018-01-21 16:00:00
tags: ["java"]
---

IoC (Inversion of Control), 也叫 DI (Dependency injection)，对象通过构造函数参数，工厂方法参数或者在工厂方法构造或返回的实例上设置属性来定义他们之间的依赖关系，容器在创建bean时，注入这些依赖。



# Bean
Spring中Bean的定义使用接口 `BeanDefinition` 表示的，它具有如下的属性：
- 一个class名
- 表明bean在容器中的行为 (scope, lifecycle callbacks等)
- 依赖的bean

## 命名 Bean
每个 Bean 都有一个或多个标识，这些标识在 Bean 的容器中必须是唯一的。在基于XML的配置中，可以用 `id` 或者 `name` 属性来指定 Bean 的标识。`id` 属性只可以指定一个，`name` 可以为 Bean 指定多个标识，通过 `,`, `;` 或者空格分隔。

如果 Bean 的定义中，没有指定name或者id，容器会自动为 Bean 生成一个标识。 Bean 的命名惯例同Java变量类似，采用驼峰式。

有时候需要为其他地方定义的bean引入一个别名。 通常，在大型系统中每个子系统都有自己的一组对象定义，需要使用别名。可使用如下方式引入别名：
```xml
<!-- fromName 是其他Bean定义中的名字， toName 是在本定义中的名字 -->
<alias name="fromName" alias="toName">
```

如果要定义一个内部静态类的 Bean，需要用 `$` 符号分隔类和内部类： `com.example.Foo$Bar`

## 构造器初始化 Bean
使用构造器初始化Bean，一般情况下需要提供一个无参构造器。如果 `Foo` 有一个有参构造器，如下：
```java
package examples.instantiating;
public class ExampleBean {
	/** Number of years to calculate the Ultimate Answer */
	private int years;
	/**  The Answer to Life, the Universe, and Everything */
	private String ultimateAnswer;
	public ExampleBean(int years, String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
	}
}

```
我们可以用类型匹配方式构造 `ExampleBean`

```xml
<bean id="exampleBean" class="examples.instantiating.ExampleBean">
    <constructor-arg type="int" value="75000"/>
    <constructor-arg type="java.lang.String" value="42" />
</bean>
```

## 静态工厂方法初始化 Bean
使用静态工厂方法初始化 Bean 时，需要在 `class` 指定含有静态工厂方法的类，并在 `factory-method` 属性中指定工厂方法的名字。
对于含有静态方法的类 `StaticFactoryMethod` :
```java
package examples.instantiating;
public class StaticFactoryMethod {
	private static StaticFactoryMethod instance = new StaticFactoryMethod();

	private StaticFactoryMethod() {}

	public static StaticFactoryMethod createInstance() {
		return instance;
	}
}
```

那么它的XML定义的Bean为：
```xml
 <bean id="staticFactoryMethod" 
    class="examples.instantiating.StaticFactoryMethod" 
    factory-method="createInstance" />
```

## 非静态工厂方法初始化 Bean
非静态工厂方法初始化 Bean 时，需要使用容器中已有的 Bean。如下
```java
package examples.instantiating;

public class NonStaticFactoryMethod {

	private NonStaticFactoryMethod instance1 = new NonStaticFactoryMethod();
	private NonStaticFactoryMethod instance2 = new NonStaticFactoryMethod();

	public NonStaticFactoryMethod getInstance1() {
		return instance1;
	}

	public NonStaticFactoryMethod getInstance2() {
		return instance2;
	}
}
```

XML 配置如下：
```xml
<bean id="nonStaticFactoryMethod" class="examples.instantiating.NonStaticFactoryMethod" />
<bean id="service1" factory-bean="nonStaticFactoryMethod" factory-method="getInstance1" />
<bean id="service2" factory-bean="nonStaticFactoryMethod" factory-method="getInstance2" />
```

# 依赖注入
依赖注入主要有两种方式：基于构造器的依赖注入 和 基于 Setter 的依赖注入

## 基于构造器的依赖注入
对于类 `ExampleBean`，除了使用类型匹配注入方式，还可以采用以下方式。

可以使用参数位置匹配方式构造 `ExampleBean`
```xml
 <!-- 构造器实例化 bean - 参数位置方式匹配 -->
<bean id="exampleBean2" class="examples.instantiating.ExampleBean">
    <constructor-arg index="0" value="75000"/>
    <constructor-arg index="1" value="42" />
</bean>
```

还可以使用参数名匹配方式，使用该方式需要在启用debug flag的情况下编译代码，否则需要在在 `ExampleBean` 的构造器上加上注解：`@ConstructorProperties({"years", "ultimateAnswer"})`
```xml
<!-- 构造器实例化 bean - 参数名方式匹配 -->
<bean id="exampleBean3" class="examples.instantiating.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

## 基于 Setter 的依赖注入
基于 Setter 的依赖注入是容器在调用无参构造器或者无参静态工厂方法后，再调用setter方法将属性注入到Bean中。
```xml
<!-- 基于 setter 方式的注入 -->
<bean id="exampleBean4" class="examples.instantiating.ExampleBean">
    <property name="years" value="750000"/>
    <property name="ultimateAnswer" value="44"/>
</bean>
```

因为我们可以混合使用构造器注入和setter注入，所以比较好的方式是：必须的依赖使用构造器注入；可选的依赖用setter注入。

**singleton-scope 的Bean会在容器创建的时候创建，同web.xml的`<load-on-start>1</load-on-start>`**

> 如果两个用构造方式注入的类有循环依赖，那么会抛出 `BeanCurrentlyInCreationException` 异常，使用setter方式注入，不会有循环依赖的问题。

我们可以用如下方式简化依赖注入：
```xml
<!-- 基于 setter 方式的注入 -->
<bean id="exampleBean5" class="examples.instantiating.ExampleBean"
        p:years="7000"
        p:ultimateAnswer="45">
</bean>
```

### `idref` 元素
`idref`标签允许容器在部署阶段验证引用的bean是真实存在的。使用 `<property name=".." value="...">` 在部署阶段不会做验证。假如`exampleBean6`是`prototype`类型，那么可能会在部署之后才会抛出异常。
```xml
<bean id="targetBean" class="examples.instantiating.ExampleRef"/>

<bean id="exampleBean6" class="examples.instantiating.ExampleBean">
	<property name="ref">
		<idref bean="targetBean" />
	</property>
</bean>
```

### `ref` 其他bean的引用
`ref` 是 `<constructor-arg/>` 或 `<property/>`中的元素，可以指定该bean引用的其他bean。
```xml
    <bean id="exampleBean7" class="examples.instantiating.ExampleBean">
        <property name="ref">
            <ref bean="targetBean"/>
        </property>
    </bean>
```

### 内部bean
我们可以把 `<bean />` 元素写到 `<property />` 或者 `<constructor-arg>` 元素中：
```xml
<bean id="outer" class="examples.instantiating.ExampleBean">
	<property name="target">
		<bean class="examples.instantiating.ExampleRef" />
	</property>
</bean>
```

内部bean没有必要定义id或者name属性，即使定义了，容器也会忽略它们。
