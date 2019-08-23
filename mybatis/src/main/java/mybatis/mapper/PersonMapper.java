package mybatis.mapper;

import mybatis.data.Person;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by admin on 2017/7/20.
 */
public interface PersonMapper {
    @Select("SELECT * FROM Person WHERE id = #{id}")
    Person selectPerson(int id);

    @Select("SELECT * FROM Person")
    List<Person> selectAllPerson();
}
