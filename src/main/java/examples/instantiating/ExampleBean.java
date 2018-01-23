package examples.instantiating;

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

	public ExampleBean() {}

	@ConstructorProperties({"years", "ultimateAnswer"})
	public ExampleBean(int years, String ultimateAnswer) {
		this.years = years;
		this.ultimateAnswer = ultimateAnswer;
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

	@Override
	public String toString() {
		return "ExampleBean{" +
			"years=" + years +
			", ultimateAnswer='" + ultimateAnswer + '\'' +
			'}';
	}
}
