package mybatis.data;

/**
 * @author wangzhongke
 */
public class Person {

	private Integer id;
	private String name;
	private Integer age;
	private SexEnum sex;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Person{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			'}';
	}
}
