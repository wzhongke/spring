package algorithms;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

		List<BlacklistEntry<String>> entries = new ArrayList<>();

		public boolean isWord(){
			return str != null;
		}

	}

	/** target表示待查找的目标字符串集合*/
	public AhoCorasickAutomation(List<BlacklistEntry<String>> target){
		root = new Node();
		if (target != null && target.size() > 0) {
			wlock.lock();
			buildTrieTree(target);
			buildACFromTrie();
			wlock.unlock();
		}
	}

	/** 由目标字符串构建Trie树*/
	private void buildTrieTree(List<BlacklistEntry<String>> target)  {
		for (BlacklistEntry<String> entry: target) {
			String[] keys = entry.key.split("\ue40a");
			for (String key: keys) {
				if (key == null ||key.length() == 0) {
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

	/** 在文本串中查找所有的目标字符串*/
	public BlacklistEntry search(String text) {
        /*创建一个表示存储结果的对象*/
		/*表示在文本字符串中查找的结果，key表示目标字符串， value表示目标字符串在文本串出现的位置*/
		HashMap<String, List<BlacklistEntry<String>>> result = new HashMap<>();
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
				if (curr.fail != null && curr.fail.isWord()) {
					result.put(curr.fail.str, curr.fail.entries);
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
		for(Map.Entry<String, List<BlacklistEntry<String>>> entry : result.entrySet()){
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


	public static void main(String[] args) throws IOException {
		List<BlacklistEntry<String>> target = new ArrayList<>();
//		try (BufferedReader reader = new BufferedReader( new InputStreamReader(new FileInputStream("D://xshell//secadmin_output1_new.dat.agent"), "GBK"))) {
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				String[] ls = line.split("\t");
//				BlacklistEntry<String> entry = new BlacklistEntry<>();
//				entry.id = ls[0];
//				entry.key = ls[1];
//				entry.value = Integer.parseInt(ls[2]);
//				entry.keys = entry.key.split("\ue40a");
//				entry.multiNum = entry.keys.length;
//				target.add(entry);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		BlacklistEntry<String> entry = new BlacklistEntry<>();
		entry.key = "释放刘晓波";
		entry.multiNum = 1;
		entry.keys = "释放刘晓波".split("\ue40a");

		target.add(entry);
		entry = new BlacklistEntry<>();
		entry.key = "刘晓波";
		entry.multiNum = 1;
		entry.keys = "刘晓波".split("\ue40a");
		target.add(entry);

		AhoCorasickAutomation aca = new AhoCorasickAutomation(target);

		String text = "中方驳斥美方要求释放刘晓波:勿充当别国法官_新闻频道_中国青年网 问:美国国务卿克里24日发表书面声明,要求中国政府释放等人,并对中国人权表示关注.中方欢迎和支持美古双方恢复正常关系,并希望美方尽早取消...";
		long begin = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		System.out.println(aca.search(text));
		System.out.println(System.currentTimeMillis() -begin);

		text = "所谓“因言获罪”是对刘晓波案的误读＿中国网刘晓波因犯煽动颠覆国家政权罪，被判处有期徒刑十一年，剥夺政治权利二年。此案已于．．．";
		System.out.println(aca.search(text));

	}
}