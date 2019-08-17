package algorithms;

import com.SizeOfAgent;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * AC 多模匹配算法，使用节点方式，适用于稀疏性
 * @author wangzhongke
 */
public class AhoCorasickAutomation {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock rlock = lock.readLock();
	private Lock wlock = lock.writeLock();

	/** AC自动机的根结点，根结点不存储任何字符信息*/
	private Node root;

	/** 内部静态类，用于表示AC自动机的每个结点，在每个结点中我们并没有存储该结点对应的字符*/
	private static class Node{

		/** 如果该结点是一个终点，即，从根结点到此结点表示了一个目标字符串，则str != null, 且str就表示该字符串*/
		String str;

		Map<Character, Node> map = new HashMap<>();

		/** 当前结点的孩子结点不能匹配文本串中的某个字符时，下一个应该查找的结点*/
		Node fail;

		List<BlacklistEntry<String[]>> entries = new ArrayList<>();

		public boolean isWord(){
			return str != null;
		}

	}

	/** target表示待查找的目标字符串集合*/
	public AhoCorasickAutomation(List<BlacklistEntry<String[]>> target){
		root = new Node();
		if (target != null && target.size() > 0) {
			wlock.lock();
			buildTrieTree(target);
			buildACFromTrie();
			wlock.unlock();
		}
	}

	/** 由目标字符串构建Trie树*/
	private void buildTrieTree(List<BlacklistEntry<String[]>> target)  {
		for (BlacklistEntry<String[]> entry: target) {
			String[] keys = entry.key;
			for (String key: keys) {
				if (StringUtils.isBlank(key)) {
					continue;
				}
				Node curr = root;
				for (int i = 0; i < key.length(); i++) {
					Character c = key.charAt(i);

					if (!curr.map.containsKey(c)) {
						curr.map.put(c, new Node());
					}
					curr = curr.map.get(c);
				}
		        /* 将每个目标字符串的最后一个字符对应的结点变成终点 */
				curr.str = key;
				curr.entries.add(entry);
			}
		}
	}

	/** 由Trie树构建AC自动机，本质是一个自动机，相当于构建KMP算法的next数组*/
	private void buildACFromTrie(){
       /*广度优先遍历所使用的队列*/
		LinkedList<Node> queue = new LinkedList<>();

        /*单独处理根结点的所有孩子结点*/
		for(Node x : root.map.values()){
			if(x != null){
                /*根结点的所有孩子结点的fail都指向根结点*/
				x.fail = root;
				queue.addLast(x);/*所有根结点的孩子结点入列*/
			}
		}

		while(!queue.isEmpty()){
            /*确定出列结点的所有孩子结点的fail的指向*/
			Node p = queue.removeFirst();
			for (Map.Entry<Character, Node> entry: p.map.entrySet()) {
				Node n = entry.getValue();
				if (n != null) {
					 /*孩子结点入列*/
					queue.addLast(n);
					 /*从p.fail开始找起*/
					Node failTo = p.fail;
					while (true) {
						 /*说明找到了根结点还没有找到*/
						if (failTo == null) {
							n.fail = root;
							break;
						}
                        /*说明有公共前缀*/
						if (failTo.map.containsKey(entry.getKey())) {
							n.fail = failTo.map.get(entry.getKey());
							break;
						} else {
							/*继续向上寻找*/
							failTo = failTo.fail;
						}
					}
				}
			}
		}
	}

//	/** 在文本串中查找所有的目标字符串*/
//	public HashSet<String> search(String text) {
//        /*创建一个表示存储结果的对象*/
//		/*表示在文本字符串中查找的结果，key表示目标字符串， value表示目标字符串在文本串出现的位置*/
//		HashSet<String> result = new HashSet<>();
//		rlock.lock();
//		Node curr = root;
//		int i = 0;
//
//		while(i < text.length()){
//            /*文本串中的字符*/
//			Character c = text.charAt(i);
//
//			/*文本串中的字符和AC自动机中的字符进行比较*/
//			if (curr.map.containsKey(c)) {
//				 /*若相等，自动机进入下一状态*/
//				curr = curr.map.get(c);
//
//				if (curr.isWord()) {
//					result.add(curr.str);
//				}
//
//				 /*这里很容易被忽视，因为一个目标串的中间某部分字符串可能正好包含另一个目标字符串，
//                 * 即使当前结点不表示一个目标字符串的终点，但到当前结点为止可能恰好包含了一个字符串*/
//				 /* 还需要回溯 fail 节点，否则会错过一些包含的内容
//				    关键词： bc, abc, c
//				    匹配词 abcd
//				  */
//				Node failNode = curr.fail;
//				while (failNode != null) {
//					if (failNode.isWord()) {
//						result.add(failNode.str);
//					}
//					failNode = failNode.fail;
//				}
//				/*索引自增，指向下一个文本串中的字符*/
//				i++;
//			}else{
//                /*若不等，找到下一个应该比较的状态*/
//				curr = curr.fail;
//
//                /*到根结点还未找到，说明文本串中以ch作为结束的字符片段不是任何目标字符串的前缀，
//                 * 状态机重置，比较下一个字符*/
//				if(curr == null){
//					curr = root;
//					i++;
//				}
//			}
//		}
//
//		rlock.unlock();
//		return result;
//	}

	/** 在文本串中查找所有的目标字符串*/
	public BlacklistEntry search(String text) {
        /*创建一个表示存储结果的对象*/
		/*表示在文本字符串中查找的结果，key表示目标字符串， value表示目标字符串在文本串出现的位置*/
		HashMap<String, List<BlacklistEntry<String[]>>> result = new HashMap<>();
		rlock.lock();
		Node curr = root;
		int i = 0;

		while(i < text.length()){
            /*文本串中的字符*/
			Character c = text.charAt(i);

			/*文本串中的字符和AC自动机中的字符进行比较*/
			if (curr.map.containsKey(c)) {
				 /*若相等，自动机进入下一状态*/
				curr = curr.map.get(c);

				if (curr.isWord()) {
					result.put(curr.str, curr.entries);
				}

				 /*这里很容易被忽视，因为一个目标串的中间某部分字符串可能正好包含另一个目标字符串，
                 * 即使当前结点不表示一个目标字符串的终点，但到当前结点为止可能恰好包含了一个字符串*/
				if (curr.fail != null) {
					Node failNode = curr.fail;
					while (failNode != null) {
						if (failNode.isWord()) {
							result.put(failNode.str, failNode.entries);
						}
						failNode = failNode.fail;
					}
				}
				/*索引自增，指向下一个文本串中的字符*/
				i++;
			}else{
                /*若不等，找到下一个应该比较的状态*/
				curr = curr.fail;

                /*到根结点还未找到，说明文本串中以ch作为结束的字符片段不是任何目标字符串的前缀，
                 * 状态机重置，比较下一个字符*/
				if(curr == null){
					curr = root;
					i++;
				}
			}
		}

		rlock.unlock();
		BlacklistEntry<String[]> hitEntry = null;
		int hitLevel = 100;
		for(Map.Entry<String, List<BlacklistEntry<String[]>>> entry : result.entrySet()){
			if (entry.getValue() != null) {
				for (BlacklistEntry<String[]> ent : entry.getValue()) {
					int hintKey = 0;
					for (String key: ent.key) {
						if (key.equals(entry.getKey()) || result.containsKey(key)) {
							hintKey ++;
						} else {
							break;
						}
					}
					if (hintKey == ent.key.length) {
						if (hitLevel > ent.warnLevel) {
							hitEntry = ent;
							hitLevel = ent.warnLevel;
						}
					}
				}
			}
		}
		return hitEntry;
	}

	private static AhoCorasickAutomation loadSubAcWord(String filename) {
		List<BlacklistEntry<String[]>> target = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "GBK"))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] ls = line.split("\t");
				if (ls.length < 6 || StringUtils.isBlank(ls[1])) {
					continue;
				}
				BlacklistEntry<String[]> entry = new BlacklistEntry<>();
				entry.id = ls[0];
				entry.key = ls[1].toLowerCase().split(" ");
				entry.value = Integer.parseInt(ls[2]);
				entry.multiNum = entry.key.length;
				try {
					entry.warnLevel = Integer.parseInt(ls[5]);
				} catch (Exception e) {
					entry.warnLevel = -2;
				}
				target.add(entry);
			}
			return new AhoCorasickAutomation(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new AhoCorasickAutomation(target);
	}


	public static void main(String[] args) {
		BlacklistEntry<String[]> entry = new BlacklistEntry<>();
		entry.key = new String[] {"倍可亲", "36岁神秘中国女首富"};
		entry.warnLevel = 3;
		entry.multiNum = 2;
		BlacklistEntry<String[]> entry1 = new BlacklistEntry<>();
		entry1.key = new String[] {"倍可亲"};
		entry1.warnLevel = 0;
		entry1.multiNum = 1;
		List<BlacklistEntry<String[]>> entries = new ArrayList<>();
		entries.add(entry);
		entries.add(entry1);
		AhoCorasickAutomation ac = new AhoCorasickAutomation(entries);
		System.out.println(ac.search("倍可亲dfd36岁神秘中国女首富"));
		System.out.println("中国");
	}
}