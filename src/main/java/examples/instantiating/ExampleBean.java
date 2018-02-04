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

	@PostConstruct
	public void init() {
		// do some initialization work
		System.out.println("init " + this);
	}

	@PreDestroy
	public void destroy() {
		// do some initialization work
		System.out.printf("destroy " + this);
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
}
