package leetcode;

public class MaxWater {
	public int maxArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int i = 0;
		int j = height.length - 1;
		int max = 0;
		while ( i < j) {
			max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
			if (height[i] > height[j]) {
				j--;
			} else {
				i++;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		System.out.println(new MaxWater().maxArea(new int[]{1, 8,6,2,5,4,8,3,7}));
	}
}
