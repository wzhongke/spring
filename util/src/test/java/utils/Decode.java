package utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class Decode {

	@Test
	public void test() throws UnsupportedEncodingException {
		String value = "ky4rLCU6KyQheyZ3cXsnJ3B7JnUmIHRvf28hLS9sNicsIScsNmwvNjZvf291b39vemxwemxzb39vITd/b39vJCN/NCs0LW9/byQwfzE2LTAnHSMsJjAtKyYdNCs0LW9/byQkMH8xNi0wJx0jLCYwLSsmHTQrNC1vf28wKyZ/c3Jvf28pMX9yb39vMCZ/c3F1b39v";
		String decode = new String(Base64.decodeBase64(value.getBytes()));
		char [] chars = "中".toCharArray();
		System.out.println(Integer.toBinaryString(chars[0]));
		System.out.print(Integer.toBinaryString(228));
		System.out.print(Integer.toBinaryString(184));
		System.out.print(Integer.toBinaryString(173));
	}
}
