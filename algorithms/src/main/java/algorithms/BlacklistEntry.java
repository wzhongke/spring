package algorithms;

/**
 * item in the BlackList
 * Created on 2006-10-30
 * @author Liu Mingzhu (mingzhuliu@sohu-inc.com)
 *
 */
public class BlacklistEntry<E> {
	public E key;
	public int mask;
	public int warnLevel;

	public int multiNum;
	public int value;
	public String id;

	public String toString () {
		StringBuilder sb = new StringBuilder(64);
		if (key instanceof String[]) {
			String[] tmp = (String[]) key;
			for (String s : tmp) {
				sb.append(s).append(' ');
			}
			return sb.toString();
		}
		return key.toString();
	}
}