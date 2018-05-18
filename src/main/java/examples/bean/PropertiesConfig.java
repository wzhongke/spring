package examples.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongke
 */

@Component
@PropertySource({"classpath:blacklistFile.properties"})
public class PropertiesConfig {
	@Value("${base.dir}")
	private String baseDir;

	@Value("${blacklist.secadminquery}")
	private String secadminQueryBlackFile;

	@Value("${white.frontweb}")
	private String webWhiteListFile;

	public String getBaseDir() {
		return baseDir;
	}

	public String getSecadminQueryBlackFile() {
		return secadminQueryBlackFile;
	}

	public String getWebWhiteListFile() {
		return webWhiteListFile;
	}
}
