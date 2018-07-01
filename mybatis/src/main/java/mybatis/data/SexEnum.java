package mybatis.data;

/**
 * @author wangzhongke
 * 性别
 */

public enum SexEnum {

	BOY("boy"), GIRL("girl");

	private String name;
	SexEnum(String name) {
		this.name = name;
	}
}
