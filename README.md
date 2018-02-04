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

## 集合 `Collections`
一个map的key或者value，一个set的值，可以通过下列之一为其赋值：
`bean ref idref list set map props value null`
集合中使用的Javabean是：
```java
public class ComplexObject {

    private Properties adminEmails;

    private List someList;

    private Map someMap;

    private Set someSet;

    private List nullList;
}
```

如果没有类型限制，那么可以向集合中注入任意的类型。如果加上类型限制后，只能向集合中注入相应的类型。比如：`List<String> someList` 只能注入 `String` 类型的值。


### 集合合并
Spring支持集合的合并，一个集合可以定义一个`<list/>`, `<map/>`, `<set/>` 或者 `<props>`，然后子类可以通过继承来合并父类中的集合
```xml
<!-- 合并集合 -->
<bean id="parent" abstract="true" class="examples.instantiating.ComplexObject">
    <property name="adminEmails">
        <props>
            <prop key="administrator">adminstrator@example.com</prop>
            <prop key="support">support@example.com</prop>
        </props>
    </property>
</bean>
<!-- child 的 adminEmails 中的元素为：
    administrator=administrator@example.com
    sales=sales@example.com
    support=support@example.com
-->
<bean id="child" parent="parent">
    <property name="adminEmails">
        <!-- the merge is specified on the child collection definition -->
        <props merge="true">
            <prop key="sales">sales@example.com</prop>
            <prop key="support">support@example.co.uk</prop>
        </props>
    </property>
</bean>
```

```xml
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
    <!-- 注入 null -->
    <property name="nullList">
        <null/>
    </property>
</bean>
```

### `depends-on` 属性
如果一个bean对于其他bean是有依赖的，但是这个依赖不能通过 `<ref/>` 等方式在配置中体现出来，那么可以使用 `depends-on` 属性来明确指定依赖：
```xml
<bean id="dependsObject" class="examples.instantiating.ComplexObject" depends-on="exampleBean5">
    <property name="someSet">
        <set>
            <ref bean="exampleBean5" />
        </set>
    </property>
</bean>
```

### 懒初始化 bean
默认情况下，容器会在初始化时创建所有的 `singleton` bean。可以通过使用`lazy-init="ture"` 来阻止容器在启动时初始化该bean，这时容器会在该bean第一次被请求时创建。

### 自动注入 Autowiring
Spring 容器可以自动注入引用的bean，有如下优点：
- 自动注入可以很大程度上减少指定属性或者构造器参数
- 当更新对象的引用时，不用修改配置

使用XML配置时，可以通过指定 `<bean/>` 标签的 `autowire` 属性来定义自动注入模式，注入模式有如下 4 种：

模式        | 说明
:----------|:----------
no         | (默认)不自动注入。bean的引用必须用`ref`定义。对于大型应用，建议使用默认值，因为这样能够更清晰和明确地指定对象的引用。
byName     | 根据属性名注入，Spring为要注入的属性寻找与属性名相同的bean，然后将其注入。
byType     | 容器中有且仅有一个bean的名字同属性名一致，才注入属性。如果多于1个，则会抛出异常并终止程序；如果没有匹配的bean，则不设置属性
constructor| 与 `byType` 类似，只是它被用在构造参数上

自动注入也有些限制：
- 在`property` 和 `constructor-arg` 中明确指定依赖会覆盖自动注入的依赖。自动注入不能够注入基本类型、`String`类型和 `Classes`
- 自动注入不如明确注入精确。
- Spring容器生成的文档可能不会包含自动注入的信息
- setter方法或者构造器参数可能会匹配到多个符合条件的bean，Spring容器会在这种情况下抛出异常。

在Spring的XML中，设置`<bean />` 的 `autowire-candidate` 属性为 `false`，容器会将改bean在自动注入中不可用。

### 方法注入
容器中的大多数bean是 singletons 的。当一个 singleton 的bean 引用另一个 singleton 的bean，或者一个非 singleton 的bean 引用另一个非 singleton 的bean，可以将一个bean设置为另一个的属性。但是两个bean的生命周期不同时，就会有问题。假如一个 singleton 的bean A 需要使用一个非 singleton 的bean B，容器只会在初始化的时候创建一个A，并将B注入到A中，而不会每次使用A时，将一个新的B注入到A中。

一个解决方案是放弃部分IoC，让A实现容器的 `ApplicationContextAware` 接口使得容器知道，每次A在调用B时，都会返回一个B的新的实例。
```java
public class MethodInjection implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void process () {
		ExampleBean bean = createExampleBean();
		// do something
	}

	protected ExampleBean createExampleBean () {
		// notice the Spring API dependency!
		return this.applicationContext.getBean("exampleBean4", ExampleBean.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
```

以上方式明显依赖了Spring容器中的类，我们还可以使用 Lookup 方法注入。Spring框架使用CGLIB库生成bytecode来动态生成覆盖了对应方法的子类来实现方法注入。使用该方法时，对应的bean不能是 `final`，覆盖的方法也不能是 `final` 的。
该方式不能用在工厂方法中，`@Bean`方式也不会生效。

```java
public class MethodInjectionWithLookup {

	public void process () {
		ExampleBean bean = createExampleBean();
		System.out.println(bean);
		// do something
	}

    // 1. 使用xml注入
	protected ExampleBean createExampleBean () {
		// notice the Spring API dependency!
		return null;
	}

    // 2. 使用 Lookup 注解
	@Lookup("exampleBeanInject")
	protected ExampleBean createExampleBean1 () {
		// notice the Spring API dependency!
		return null;
	}
}
```

使用方式如下：
```xml
<bean id="exampleBeanInject" class="examples.instantiating.ExampleBean" scope="prototype">
    <property name="years" value="750000"/>
    <property name="ultimateAnswer" value="44"/>
</bean>

<bean id="methodInjection" class="examples.instantiating.MethodInjectionWithLookup">
    <lookup-method name="createExampleBean" bean="exampleBeanInject" />
</bean>
```

还使用lookup method injection 实现将容器管理的bean的任意方法替换为另一个方法。详见Spring的[说明文档](https://docs.spring.io/spring/docs/5.0.2.RELEASE/spring-framework-reference/core.html#beans-factory-arbitrary-method-replacement)

## Bean 生命周期
