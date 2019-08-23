package examples.bean.instantiating;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.ConstructorProperties;

/**
 * @author wangzhongke
 */
public class ExampleBean implements InitializingBean, DisposableBean {

	/** Number of years to calculate the Ultimate Answer */
	private int years;

	/**  The Answer to Life, the Universe, and Everything */
	private String ultimateAnswer;

	private ExampleRef ref;

	private String refBean;

	public ExampleBean() {
		System.out.println("constructor");
	}

	@ConstructorProperties({"years", "ultimateAnswer"})
	public ExampleBean(int years, String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
		System.out.println("constructor");
	}

	/**
	 * 初始化执行顺序：
	 * 1. PostConstruct,
	 * 2. afterPropertiesSet(),
	 * 3. init()
	 */
	@PostConstruct
	public void postConstruct() {
		// do some initialization work
		System.out.println("post construct");
	}

	public void init () {
		System.out.println("init method.");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("after properties set");
	}

	/**
	 * 销毁执行顺序
	 * 1. @PreDestroy
	 * 2. destroy()
	 * 3. 自定义的 destroy
	 */

	@PreDestroy
	public void preDestroy() {
		// do some initialization work
		System.out.printf("pre destroy" + this);
	}

	@Override
	public void destroy () {
		System.out.println("destroy bean");
	}

	public void customDestroy () {
		System.out.println("custom destroy");
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public String getUltimateAnswer() {
		return ultimateAnswer;
	}

	public void setUltimateAnswer(String ultimateAnswer) {
		this.ultimateAnswer = ultimateAnswer;
	}

	public ExampleRef getRef() {
		return ref;
	}

	public void setRef(ExampleRef ref) {
		this.ref = ref;
	}

	public String getRefBean() {
		return refBean;
	}

	public void setRefBean(String refBean) {
		this.refBean = refBean;
	}

	@Override
	public String toString() {
		return super.toString() + " ExampleBean{" +
			"years=" + years +
			", ultimateAnswer='" + ultimateAnswer + '\'' +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ExampleBean that = (ExampleBean) o;

		if (years != that.years) {
			return false;
		}
		if (ultimateAnswer != null ? !ultimateAnswer.equals(that.ultimateAnswer) : that.ultimateAnswer != null) {
			return false;
		}
		if (ref != null ? !ref.equals(that.ref) : that.ref != null) {
			return false;
		}
		return refBean != null ? refBean.equals(that.refBean) : that.refBean == null;
	}

	@Override
	public int hashCode() {
		int result = years;
		result = 31 * result + (ultimateAnswer != null ? ultimateAnswer.hashCode() : 0);
		result = 31 * result + (ref != null ? ref.hashCode() : 0);
		result = 31 * result + (refBean != null ? refBean.hashCode() : 0);
		return result;
	}
}
