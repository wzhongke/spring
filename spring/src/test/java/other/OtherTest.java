package other;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OtherTest {

	@Test
	public void testMapToString() {
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		System.out.println(map.toString());
	}
}
