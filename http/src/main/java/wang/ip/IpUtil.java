package wang.ip;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IpUtil {
	/**
	 * 根据掩码位数计算掩码
	 *
	 * @param mask 掩码位
	 * @return 子网掩码
	 */
	public static int getNetMask(int mask) {
		StringBuilder maskBytes = new StringBuilder();
		// 子网掩码为1 占了几个字节
		int num1 = mask / 8;
		// 子网掩码的补位位数
		int num2 = mask % 8;
		int[] array = new int[4];
		for (int i = 0; i < num1; i++) {
			array[i] = 255;
		}
		for (int i = 0; i < num2; num2--) {
			array[num1] += 1 << 8 - num2;
		}
		return array[3];
	}

	/**
	 * 根据掩码位数计算掩码
	 * @param maskIndex 掩码位
	 * @return 子网掩码
	 */
	public static String getNetMask(String maskIndex) {
		StringBuilder mask = new StringBuilder();
		Integer inetMask = 0;
		try {
			inetMask = Integer.parseInt(maskIndex);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return null;
		}
		if (inetMask > 32) {
			return null;
		}
		// 子网掩码为1占了几个字节
		int num1 = inetMask / 8;
		// 子网掩码的补位位数
		int num2 = inetMask % 8;
		int array[] = new int[4];
		for (int i = 0; i < num1; i++) {
			array[i] = 255;
		}
		for (int i = num1; i < 4; i++) {
			array[i] = 0;
		}
		for (int i = 0; i < num2; num2--) {
			array[num1] += 1 << 8 - num2;
		}
		for (int i = 0; i < 4; i++) {
			if (i == 3) {
				mask.append(array[i]);
			} else {
				mask.append(array[i] + ".");
			}
		}
		return mask.toString();
	}



	/**
	 * 根据网段计算起始IP 网段格式:x.x.x.x/x
	 * 一个网段0一般为网络地址,255一般为广播地址.
	 * 起始IP计算:网段与掩码相与之后加一的IP地址
	 *
	 * @param ipArr ip 数组
	 * @param mask 掩码
	 * @return 起始IP
	 */

	public static int getStartIp (Integer[] ipArr, int mask) {
		ipArr[3] = ipArr[3] & mask;
		return ipArr[3];
	}

	/**
	 * 根据网段计算结束IP
	 *
	 * @param startIpArray
	 * @return 结束IP
	 */
	public static int getEndIp(Integer[] startIpArray, int mask) {
		StringBuilder endIp = new StringBuilder();
		//实际需要的IP个数
		int hostNumber = 0;
		try {
			hostNumber = 1 << (32 - mask);
			startIpArray[3] = startIpArray[3] | (hostNumber - 1);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		return startIpArray[3];
	}

	public static boolean validateMask (String mask) {
		try {
			int maskInt = Integer.parseInt(mask);
			if (maskInt < 24 || maskInt > 32) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean validateIp (String ip) {
		String [] ipAdd = ip.split("\\.");
		if (ipAdd.length != 4) {
			return false;
		}
		for (String ipSeg: ipAdd) {
			try {
				int ipInt = Integer.parseInt(ipSeg);
				if (ipInt < 0 || ipInt > 255) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static String concatIp (Object[] ipArr, String last) {
		String ip = "";
		for (int i=0; i<3; i++) {
			ip += ipArr[i] + ".";
		}
		return ip + last;
	}


	public static void main(String[] args) {
		List<String> validateIps = new ArrayList<>();
		List<String> inValidateIps = new ArrayList<>();
		String ip = "218.85.130.104/29";
		System.out.println(Integer.toBinaryString(104 | 7));
		if (ip.contains("/")) {
			String [] ipMask = ip.split("/");
			if (validateIp(ipMask[0]) && validateMask(ipMask[1])) {
				Integer [] ipArr = new Integer[4];
				String [] ipStr = ipMask[0].split("\\.");
				for (int i=0; i<ipStr.length; i++) {
					ipArr[i] = Integer.parseInt(ipStr[i]);
				}
				int mask = Integer.parseInt(ipMask[1]);
				if ("24".equals(ipMask[1])) {
					validateIps.add(concatIp(ipArr, "*"));
				} else {
					int startIp = getStartIp(ipArr, getNetMask(mask));
					System.out.println(Arrays.toString(ipArr));
					getEndIp(ipArr, Integer.parseInt(ipMask[1]));
					for (int start=startIp; start < ipArr[3]; start++) {
						validateIps.add(concatIp(ipArr, "" + start));
					}

				}
			} else {
				inValidateIps.add(ip);
			}
		}

		System.out.println(validateIps);
	}

}
