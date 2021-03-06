package mybatis.wang;

import mybatis.data.Person;
import mybatis.data.SexEnum;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

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
		Properties prop =new Properties();
		prop.load(Resources.getResourceAsStream("config.properties"));
		return new SqlSessionFactoryBuilder().build(inputStream, prop);
	}

	private static int insert (SqlSession session) {
		Person person = new Person();
		person.setAge(12);
		person.setName("wang");
		person.setSex(SexEnum.BOY);
		return session.insert("mybatis.UserMapper.insert", person);

	}

	private static List<Person> select (SqlSession session) {
		return session.selectList("mybatis.UserMapper.findAll");
	}

	private static Person selectById (SqlSession sqlSession, int id) {
		return sqlSession.selectOne("mybatis.UserMapper.findById", id);
	}

	private static void update (SqlSession sqlSession) {

	}

	private static List<Person> selectByName (SqlSession sqlSession) {
		return sqlSession.selectList("mybatis.UserMapper.findByName");
	}

	public static void main(String[] args) throws IOException {
		SqlSessionFactory factory = getXmlConf();
		SqlSession session = factory.openSession();
		System.out.println(insert(session));
//		session.commit();
//		System.out.println(select(session));
//		System.out.println(selectById(session, 1));
		System.out.println(selectByName(session));
		session.close();
	}

}
