package leetcode;

public class PalindromeInteger {
	public boolean isPalindrome(int x) {
		if (x < 0 || (x > 0 && x % 10 == 0)) {
			return false;
		}
		int revert = 0;
		// 计算到中间就可以了
		while (revert < x) {
			revert = revert * 10 + x % 10;
			x /= 10;

		}
		return x == revert || x == revert / 10;
	}

	public static void main(String[] args) {
		System.out.println(new PalindromeInteger().isPalindrome(121));
	}
}
