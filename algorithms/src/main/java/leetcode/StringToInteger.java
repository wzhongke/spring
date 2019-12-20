package leetcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToInteger {

	public int myAtoi(String str) {
		int atoi = 0;
		boolean start = false;
		int nev = 1;
		for (byte b: str.getBytes()) {
			if (b == ' ' && !start) {
				continue;
			}
			if (b == '-' && !start) {
				nev	= -1;
				start = true;
			} else if (b == '+' && !start) {
				start = true;
			} else if (b >= '0' && b <= '9') {
				int mum = (b - '0') * nev;
				if (nev > 0 && (atoi > Integer.MAX_VALUE / 10 || (atoi == Integer.MAX_VALUE / 10 && mum >= 7))) {
					return Integer.MAX_VALUE;
				} else if (nev < 0 && atoi < Integer.MIN_VALUE / 10 || (atoi == Integer.MIN_VALUE / 10 && mum <= -8)) {
					return Integer.MIN_VALUE;
				}
				atoi = atoi * 10 + mum;
				start = true;
			} else {
				break;
			}
		}
		return atoi;
	}

	// 正则
	public int pattern(String str) {
		Pattern p = Pattern.compile("^[\\+\\-]?\\d+");
		Matcher m = p.matcher(str);
		int value = 0;
		//判断是否能匹配
		if (m.find()){
			//字符串转整数，溢出
			try {
				value = Integer.parseInt(str.substring(m.start(), m.end()));
			} catch (Exception e){
				//由于有的字符串"42"没有正号，所以我们判断'-'
				value = str.charAt(0) == '-' ? Integer.MIN_VALUE: Integer.MAX_VALUE;
			}
		}
		return value;
	}

	public static void main(String[] args) {

	}
}
