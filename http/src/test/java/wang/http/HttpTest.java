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
	public void post() throws Exception {
		String result = HttpRequest.post("http://lzquery05.webrank.zw.ted:10000", "key=query&start=0&end=5&theme=http%3A%2F%2Fwap.redquan.com%2Fask%2F2019011724357.html%23%2380020063&tab=sogouhao");
		System.out.println(result);
	}

	@Test
	public void test () {
		String test = "\u0003vr_50026601_s#null_tc#null_https://wap.169kang.com/question/380561043.html\u0003vr_11018301_s#web2wap_tc#0_person#0_https://wap.169kang.com/question/144482069.html\u0003web_s#web2wap_tc#0_person#0_http://m.babytree.com/community/beijing/topic_27628371.html\u0003vr_30010156_s#web2wap_tc#0_person#0_http://3g.club.xywy.com/static/20151019/80562668.htm\u0003vr_30010081_s#insert_tc#0_person#0_http://wap-hint/\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/\u0003web_s#web2wap_tc#0_person#0_http://3g.yaolan.com/ask/question/150419131533b0adc876.html\u0003vr_11005401_s#wapvr_tc#0_person#0_http://sogouwenwenknowledge/\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/\u0003vr_30000201_s#web2wap_tc#0_person#0_http://3g.yaolan.com/ask/question/160715055934587c0de9.html\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/\u0003vr_30000201_s#web2wap_tc#0_person#0_http://m.babytree.com/ask/detail/19058152\u0003vr_30000201_s#web2wap_tc#0_person#0_http://wapiknow.baidu.com/question/1496953971037870659\u0003vr_30010156_s#web2wap_tc#0_person#0_https://wap.169kang.com/question/135772105.html\u0003vr_30000201_s#web2wap_tc#0_person#0_http://wapiknow.baidu.com/question/114774262\u0003vr_30010156_s#web2wap_tc#0_person#0_http://3g.club.xywy.com/static/20130105/17261252.htm\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/\u0003vr_50000000_s#wapvr_tc#0_person#0_http://zhilifang/";
		String test2 = "\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003c5c1910fb988d18e4c2aa105e708f5b4\u0003\u0003\u0003\u0003";
		int count = 0;
		for (char c: test.toCharArray()) {
			if (c == '\u0003') {
				count ++;
			}
		}
		System.out.println(count);
		System.out.println("中国");
	}
}
