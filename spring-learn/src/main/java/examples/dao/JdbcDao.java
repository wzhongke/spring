package examples.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("dataSource")
	public void setDateSource(DataSource dateSource) {
		this.jdbcTemplate = new JdbcTemplate(dateSource);
	}

	public void save() {
		// 可以以集合的方式注入
		List<Integer> mode = new ArrayList<>();
		mode.add(2);
		mode.add(3);
		this.jdbcTemplate.update("insert  into admin (version, mode, log_level) values (?, ?, ?)",
			new Object[] { 1,  mode }, new int[] { Types.INTEGER, Types.INTEGER }
		);
	}

	public void getUsers() {
		this.jdbcTemplate.query("", (RowMapper) (resultSet, i) -> null);
	}
}
