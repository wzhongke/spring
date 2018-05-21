package examples.instantiating;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.ConstructorProperties;

/**
 * @author wangzhongke
 */
public class ExampleBean {

	/** Number of years to calculate the Ultimate Answer */
	private int years;

	/**  The Answer to Life, the Universe, and Everything */
	private String ultimateAnswer;

	private ExampleRef ref;

	private String refBean;

	public ExampleBean() {}

	@ConstructorProperties({"years", "ultimateAnswer"})
	public ExampleBean(int years, String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
	}

	/**
	 * 构造完成后执行
	 */
	@PostConstruct
	public void init() {
		// do some initialization work
		System.out.println("init " + this);
	}

	@PreDestroy
	public void destroy() {
		// do some initialization work
		System.out.println("destroy " + this);
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExampleBean that = (ExampleBean) o;

		if (years != that.years) return false;
		if (ultimateAnswer != null ? !ultimateAnswer.equals(that.ultimateAnswer) : that.ultimateAnswer != null)
			return false;
		if (ref != null ? !ref.equals(that.ref) : that.ref != null) return false;
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
