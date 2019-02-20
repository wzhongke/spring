package utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EncryptTest {

	@Test
	public void testEncrypt () {
		String encrypt = Encrypt.encrypt("sogouwap" + '\3' + "伪装者豆瓣" +'\3' + "time\3b5bcd3ef62b09663e3ee30ad2d08dd9f", "mkv0BqbuJXFdTcUM");
		System.out.println(encrypt);
	}

	@Test
	public void testDecrypt () {
		String decrypt = Encrypt.desEncrypt("m5AQx8AHQ4yAJ6kDWC6rFg0Qjq5BDjh+7am+7RSHAPTV4b+ADZwznUfJXGOdxM2GwZ23hIYJiHuJb8VoHsUIKRbb0H1z+/KX4m00ZbQrAZegZ1/7gQnxv/MpG0sKsoajhBVwrDMP8AqC07Zu6aOqf3bVam1gU136rs1CLvOSXeo=", "mkv0BqbuJXFdTcUM");
		System.out.println(decrypt);
	}

	@Test
	public void testBase64 () throws UnsupportedEncodingException {
		String base64 ="eyJhIjoxLCJhZHQiOjg4OCwiYWkiOjIwNTY1NzA0LCJhbSI6InNvZ291LmNvbSIsImIiOjE1NDAyMDY4NzQsImJpIjoiZTRhNmQxZTdkMWJjN2ZhMiIsImMiOjMwMDMyNzIsImNkIjoyLCJjaSI6MTAwMCwiZGV2aWNlaWQiOiJ1bmtub3ciLCJleHBudSI6IjAiLCJleHQiOiIsLCwsLCwsLCwsLCwiLCJnIjoyNzEwMzM2LCJnYyI6MCwiaSI6IjE4MDAiLCJpaSI6MzEyNDQ2NCwiaW0iOiJ1bmtub3ciLCJpcCI6IjExMi45Ni43MC4yMDciLCJpcyI6NCwiayI6MCwibCI6MSwibWFjIjoiIiwib3MiOjIsInAiOiJlNGE2ZDFlN2QxYmM3ZmEyIiwicGJyIjoxMDAsInBkIjoic29nb3VfanJzZF9hcHBfenQiLCJwbCI6MjYzMDIxMSwicG8iOjAsInByIjoxMDAsInEiOiJrd2QiLCJyIjowLCJzIjo1MTA1MCwidHQiOjAsInVybCI6Imh0dHAlM2ElMmYlMmZiYW94aWFuLnBpbmdhbi5jb20lMmZtJTJmaW5kZXguc2h0bWwlM2ZXVC5tY19pZCUzZFQwMC1TRy1ORVctSlJTRC1LUCIsInV0IjoxLCJ3IjoiIn0K";
		System.out.println(new String(Base64.decodeBase64(base64)));
	}

	@Test
	public void testPattern () {
		Pattern pattern = Pattern.compile(".*siteId=(.*?)(&.*|$)");
		String url = "http://m.sogou.com/web/sogouhao/re?url=zQ%2F%2FXccHhPnGnOnwj9Bc0IkDpITsQBgP&siteId=123&%2BaehkhWleoyARTcaBKMBwVNrdS1LUJ9w58tWheK5FSxlOF0s7nt94RbT4nln%2FNkG3jNxz9p4Doln%2F8HCjFBpxiq3qIph34fvKly5cQ2bJ7ApFyZbAInWlgcI6S%2Ba3PkG6oy6I9uZKcaeo5p3VcJDwi8k5TGj6cuWpEagsHlJOONwzcAbtmqJzQ%3D%3D";
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			System.out.println(matcher.group(1));
		}

		System.out.println(url.replaceFirst("&siteId=.*?(&|$)", "&"));
	}
}
