package mybatis.wang;

import mybatis.data.Person;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wangzhongke
 */
public class FirstExample {

	/**
	 * 使用 XML 构建 SqlSessionFactory
	 */
	public static SqlSessionFactory getXmlConf () throws IOException {
		String resource = "mybatis/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	public static void main(String[] args) throws IOException {
		SqlSessionFactory factory = getXmlConf();
		SqlSession session = factory.openSession();
		List<Person> personList = session.selectList("mybatis.UserMapper.findAll");
		System.out.println(personList);
	}

}
