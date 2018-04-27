package algorithms;

public class BlacklistEntry {

	public String id;
	public String key;
	public int value;
	public int warnLevel;
	public int multiNum;
	public String[] keys;

	public BlacklistEntry (){}

	public BlacklistEntry(String id, String key, int value, int warnLevel) {
		this.id = id;
		this.key = key;
		this.value = value;
		this.warnLevel = warnLevel;
	}

	@Override
	public String toString() {
		return "BlacklistEntry{" +
			"id='" + id + '\'' +
			", key='" + key + '\'' +
			", value=" + value +
			", warnLevel=" + warnLevel +
			'}';
	}
}
