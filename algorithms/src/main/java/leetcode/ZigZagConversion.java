package leetcode;

public class ZigZagConversion {
	public String convert(String s, int numRows) {
		if (numRows == 1) {
			return s;
		}
		int len = s.length();
		StringBuilder builder = new StringBuilder();
		for (int i=1; i<=len && i<=numRows; i++) {
			builder.append(s.charAt(i - 1));
			int longStep = 2 * (numRows - i);
			int shortStep = 2 * (i - 1);
			if (longStep == 0 && shortStep == 0) {
				break;
			}
			if (longStep == 0) {
				longStep = shortStep;
			} else if (shortStep == 0) {
				shortStep = longStep;
			}
			boolean isLong  = false;
			for (int j = i + longStep; j<=len;) {
				builder.append(s.charAt(j - 1));
				System.out.println("j: " + j);
				j += isLong ? longStep : shortStep;
				isLong = !isLong;
			}
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		System.out.println(new ZigZagConversion().convert("AB", 1));
	}
}
