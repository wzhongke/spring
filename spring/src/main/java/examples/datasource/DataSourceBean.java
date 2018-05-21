package examples.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceBean {
	private static Log log = LogFactory.getLog(DataSourceBean.class);
	private Map<String, HikariDataSource> dataSourceMap = new HashMap<>();

	public DataSourceBean () {
		Properties properties = new Properties();
		try (InputStream in = DataSourceBean.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
			properties.load(in);
			Map<String, ConfigData> configDataMap = new HashMap<>();
			for (Object key: properties.keySet()) {
				String[] keys = key.toString().split("\\.");
				if (configDataMap.containsKey(keys[0])) {
					continue;
				}
				ConfigData data = new ConfigData(keys[0], properties);
				configDataMap.put(data.alias, data);
			}

			for (Map.Entry<String, ConfigData> data: configDataMap.entrySet()) {
				ConfigData value = data.getValue();
				System.out.println(value);
				HikariConfig config = new HikariConfig();
				config.setJdbcUrl(value.url);
				config.setDriverClassName(value.driver);
				config.setUsername(value.user);
				config.setPassword(value.passwd);
				config.setConnectionTestQuery(value.testQuery);
				try{
					config.setMaximumPoolSize(Integer.parseInt(value.maxConnectionCount));
					config.setConnectionTimeout(Integer.parseInt(value.keepSleepTime));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				HikariDataSource conf = new HikariDataSource(config);
				dataSourceMap.put(value.alias, conf);
			}
			for (String key: dataSourceMap.keySet()) {
				System.out.println(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean(name = "blacklistDataSource")
	public DataSource blacklistDataSource() {
		return dataSourceMap.get("blacklist");
	}

	@Bean(name = "blacklistOldDataSource")
	public DataSource blacklistPW() {
		return dataSourceMap.get("blacklist_old");
	}


	@Bean(name = "secadminDataSource")
	public DataSource secadminDataSource() {
		return dataSourceMap.get("secadmin");
	}

	@Bean(name = "authorDataSource")
	public DataSource authorDataSource () {
		return dataSourceMap.get("author");
	}

	@PreDestroy
	public void destroy () {
		for (Map.Entry<String, HikariDataSource> entry: dataSourceMap.entrySet()) {
			log.info("closing " + entry.getKey());
			entry.getValue().shutdown();
		}
	}

	static class ConfigData {
		String url;
		String driver;
		String user;
		String passwd;
		String alias;
		String maxConnectionCount;
		String testQuery;
		String keepSleepTime;

		ConfigData(String keyPre, Properties p) {
			alias = p.getProperty(keyPre + ".alias");
			url =  p.getProperty(keyPre + ".url");
			driver =p.getProperty(keyPre + ".driver-class");
			user = p.getProperty(keyPre + ".user");
			passwd = p.getProperty(keyPre + ".password");
			maxConnectionCount = p.getProperty(keyPre + ".maximum-connection-count");
			testQuery = p.getProperty(keyPre + ".house-keeping-test-sql");
			keepSleepTime = p.getProperty(keyPre + ".house-keeping-sleep-time");
		}

		@Override
		public String toString() {
			return "ConfigData{" +
				"url='" + url + '\'' +
				", driver='" + driver + '\'' +
				", user='" + user + '\'' +
				", passwd='" + passwd + '\'' +
				", alias='" + alias + '\'' +
				", maxConnectionCount='" + maxConnectionCount + '\'' +
				", testQuery='" + testQuery + '\'' +
				", keepSleepTime='" + keepSleepTime + '\'' +
				'}';
		}
	}
}
