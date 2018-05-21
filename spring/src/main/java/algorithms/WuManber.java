package algorithms;
/***
 * Wu-Manber多模式关键词快速检测(过滤)算法
 * @author Ivan Ji 2011.08.18
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WuManber {
	private int B=2;//块字符X的长度(模式串后缀字符的个数)

	private boolean initFlag = false;//是否初始化
	private UnionPatternSet unionPatternSet = new UnionPatternSet();
	private int maxIndex = (int) Math.pow(2, 16);
	private int shiftTable[] = new int[maxIndex];
	public List <AtomicPattern> hashTable[] = new List[maxIndex];
	private UnionPatternSet tmpUnionPatternSet = new UnionPatternSet();

	public WuManber() {
	}


	public static void main(String args[]){//测试
		WuManber objWM=new WuManber();
		List<BlacklistEntry> target = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D://xshell//web_front_blacklist_new.agent"),"GBK"))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] ls = line.split("\t");
				BlacklistEntry entry = new BlacklistEntry();
				entry.key = ls[0];
				entry.keys = entry.key.split("\ue40a");
				entry.multiNum = entry.keys.length;
				target.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(objWM.addFilterKeyWord(target, 0)){
			try (BufferedReader reader = Files.newBufferedReader(Paths.get("D://content.txt"))) {
				String line = "";
				long begin = System.currentTimeMillis();
				while ((line = reader.readLine()) != null) {
					BlacklistEntry entry = objWM.macth(line,new ArrayList<>(0));
					if (entry != null) {
						System.out.println(entry);
					}
				}
				System.out.println(System.currentTimeMillis() - begin);
			} catch (IOException e) {
				e.printStackTrace();
			}


		}

		objWM.clear();

	}


	//匹配
	public BlacklistEntry macth(String content, List <Integer> levelSet) {

		HashMap<String, List<BlacklistEntry>> result = new HashMap<>();
		if (initFlag == false)
			init();
		List <AtomicPattern> aps = new ArrayList <>();
		String preContent = content;//preConvert(content);
		int iSameCount=0;
		for (int i = 0; i  < preContent.length();) {
			char checkChar = preContent.charAt(i);
			if (shiftTable[checkChar] == 0) {
				List <AtomicPattern> tmpAps = findMathAps(preContent.substring(0, i + 1),hashTable[checkChar]);
				aps.addAll(tmpAps);
				if(tmpAps.size()>0){
					result.put(tmpAps.get(0).getPattern().str, tmpAps.get(0).entries);

					iSameCount++;
				}
				i++;
			} else
				i = i + shiftTable[checkChar];
		}
		parseAtomicPatternSet(aps, levelSet);
		//sResult.append(" 共有:").append(iSameCount).append("处不合格！");
		for(Map.Entry<String, List<BlacklistEntry>> entry : result.entrySet()){
			if (entry.getValue() != null) {
				for (BlacklistEntry ent : entry.getValue()) {
					int hintKey = 0;
					for (String key: ent.keys) {
						if (key.equals(entry.getKey()) || result.containsKey(key)) {
							hintKey ++;
						} else {
							break;
						}
					}
					if (hintKey == ent.multiNum) {
						return ent;
					}
				}
			}
		}
		return null;
	}


	//加入关键词
	public boolean addFilterKeyWord(List<BlacklistEntry> target, int level) {
		if (initFlag)
			return false;
		UnionPattern unionPattern = new UnionPattern();
		for (BlacklistEntry entry: target) {
			String[] keys = entry.key.split("\ue40a");
			for (String key: keys) {
				Pattern pattern = new Pattern(key);
				AtomicPattern atomicPattern = new AtomicPattern(pattern);
				unionPattern.addNewAtomicPattrn(atomicPattern);
				unionPattern.setLevel(level);
				atomicPattern.entries.add(entry);
				atomicPattern.setBelongUnionPattern(unionPattern);
			}
		}
		tmpUnionPatternSet.addNewUnionPattrn(unionPattern);
		return true;
	}

	//验证字符
	private boolean isValidChar(char ch) {
		if ((ch >= '0' && ch  <= '9') || (ch >= 'A' && ch  <= 'Z') || (ch >= 'a' && ch  <= 'z'))
			return true;
		if ((ch >= 0x4e00 && ch  <= 0x7fff) || (ch >= 0x8000 && ch  <= 0x952f))
			return true;// 简体中文汉字编码
		return false;
	}

	//封装原子模式集
	private void parseAtomicPatternSet(List <AtomicPattern> aps,List <Integer> levelSet) {
		while (aps.size() > 0) {
			AtomicPattern ap = aps.get(0);
			UnionPattern up = ap.belongUnionPattern;
			if (up.isIncludeAllAp(aps)) {
				levelSet.add(up.getLevel());
			}
			aps.remove(0);
		}
	}

	//查找原子模式
	private List <AtomicPattern> findMathAps(String src,List <AtomicPattern> destAps) {
		List <AtomicPattern> aps = new ArrayList <>();
		for (int i = 0; i  < destAps.size(); i++) {
			AtomicPattern ap = destAps.get(i);
			if (ap.findMatchInString(src))
				aps.add(ap);
		}
		return aps;
	}
	//预转换内容（除掉特殊字符）
	private String preConvert(String content) {
		String retStr = new String();
		for (int i = 0; i  < content.length(); i++) {
			char ch = content.charAt(i);
			if (this.isValidChar(ch) == true) {
				retStr = retStr + ch;
			}
		}
		return retStr;
	}
	// shift table and hash table of initialize
	private void init() {
		initFlag = true;
		for (int i = 0; i  < maxIndex; i++)
			hashTable[i] = new ArrayList <>();
		shiftTableInit();
		hashTableInit();
	}

	//清除
	public void clear() {
		tmpUnionPatternSet.clear();
		initFlag = false;
	}

	//初始化跳跃表
	private void shiftTableInit() {
		for (int i = 0; i  < maxIndex; i++)
			shiftTable[i] = B;
		List <UnionPattern> upSet = tmpUnionPatternSet.getSet();
		for (int i = 0; i  < upSet.size(); i++) {
			List <AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j  < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				//System.out.print(pattern.charAtEnd(1)+"\t");
				if (shiftTable[pattern.charAtEnd(1)] != 0)
					shiftTable[pattern.charAtEnd(1)] = 1;
				if (shiftTable[pattern.charAtEnd(0)] != 0)
					shiftTable[pattern.charAtEnd(0)] = 0;
			}
		}
	}

	//初始化HASH表
	private void hashTableInit() {
		List <UnionPattern> upSet = tmpUnionPatternSet.getSet();
		for (int i = 0; i  < upSet.size(); i++) {
			List <AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j  < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				//System.out.println(pattern.charAtEnd(0));
				if (pattern.charAtEnd(0) != 0) {
					hashTable[pattern.charAtEnd(0)].add(ap);
				}
			}
		}
	}
}

//模式类
class Pattern {
	public String str;

	Pattern(String str) {
		this.str = str;
	}
	public char charAtEnd(int index) {
		if (str.length() > index) {
			return str.charAt(str.length() - index - 1);
		} else
			return 0;
	}
	public String getStr() {
		return str;
	};
}

//原子模式类
class AtomicPattern {
	public boolean findMatchInString(String str) {
		if (this.pattern.str.length() > str.length())
			return false;
		int beginIndex = str.length() - this.pattern.str.length();
		String eqaulLengthStr = str.substring(beginIndex);
		if (this.pattern.str.equalsIgnoreCase(eqaulLengthStr))
			return true;
		return false;
	}
	AtomicPattern(Pattern pattern) {
		this.pattern = pattern;
	};
	private Pattern pattern;
	List<BlacklistEntry> entries = new ArrayList<>();
	public UnionPattern belongUnionPattern;
	public UnionPattern getBelongUnionPattern() {
		return belongUnionPattern;
	}
	public void setBelongUnionPattern(UnionPattern belongUnionPattern) {
		this.belongUnionPattern = belongUnionPattern;
	}
	public Pattern getPattern() {
		return pattern;
	}
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
}

//合并的模式类
class UnionPattern {
	// union string
	UnionPattern() {
		this.apSet = new ArrayList <>();
	}
	public List <AtomicPattern> apSet;
	public void addNewAtomicPattrn(AtomicPattern ap) {
		this.apSet.add(ap);
	}
	public List <AtomicPattern> getSet() {
		return apSet;
	}
	public boolean isIncludeAllAp(List <AtomicPattern> inAps) {
		if (apSet.size() > inAps.size())
			return false;
		for (int i = 0; i  < apSet.size(); i++) {
			AtomicPattern ap = apSet.get(i);
			if (!isInAps(ap, inAps))
				return false;
		}
		return true;
	}
	private boolean isInAps(AtomicPattern ap, List <AtomicPattern> inAps) {
		for (int i = 0; i  < inAps.size(); i++) {
			AtomicPattern destAp = inAps.get(i);
			if (ap.getPattern().str.equalsIgnoreCase(destAp.getPattern().str))
				return true;
		}
		return false;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return this.level;
	}
	private int level;
}

//合并的模式集子类

class UnionPatternSet { // union string set
	public List <UnionPattern> unionPatternSet;
	UnionPatternSet() {
		this.unionPatternSet = new ArrayList <>();
	}
	public void addNewUnionPattrn(UnionPattern up) {
		this.unionPatternSet.add(up);
	}
	public List <UnionPattern> getSet() {
		return unionPatternSet;
	}
	public void clear() {
		unionPatternSet.clear();
	}
}
