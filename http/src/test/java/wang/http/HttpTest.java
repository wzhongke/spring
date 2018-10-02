package wang.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import wang.ip.IpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpTest {

	public List<String> getIpList (String seg) {
		List<String> validateIps = new ArrayList<>();
		List<String> inValidateIps = new ArrayList<>();
//		System.out.println(Integer.toBinaryString(104 | 7));
		if (seg.contains("/")) {
			String [] ipMask = seg.split("/");
			if (IpUtil.validateIp(ipMask[0]) && IpUtil.validateMask(ipMask[1])) {
				Integer [] ipArr = new Integer[4];
				String [] ipStr = ipMask[0].split("\\.");
				for (int i=0; i<ipStr.length; i++) {
					ipArr[i] = Integer.parseInt(ipStr[i]);
				}
				int mask = Integer.parseInt(ipMask[1]);
				if ("24".equals(ipMask[1])) {
					validateIps.add(IpUtil.concatIp(ipArr, "*"));
				} else {
					int startIp = IpUtil.getStartIp(ipArr, IpUtil.getNetMask(mask));
					System.out.println(Arrays.toString(ipArr));
					IpUtil.getEndIp(ipArr, Integer.parseInt(ipMask[1]));
					for (int start=startIp; start < ipArr[3]; start++) {
						validateIps.add(IpUtil.concatIp(ipArr, "" + start));
					}

				}
			} else {
				inValidateIps.add(seg);
			}
		}
		return validateIps;
	}

	@Test
	public void testHttp() throws Exception {
		String result = HttpRequest.get("https://maa.chinanetcenter.com/api/maa/ipAreas/vivo_data", false);
		JSONObject obj = JSONObject.fromObject(result);
		JSONArray jsonArray = obj.optJSONArray("ipareas");
		for (Object o : jsonArray) {
			System.out.println(getIpList((String) o));
		}
	}

	@Test
	public void test () {

	}
}
