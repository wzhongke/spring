package algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AhoCorasickAutomation {

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock rlock = lock.readLock();

	/*本示例中的AC自动机只处理英文类型的字符串，所以数组的长度是128*/
	private static final int ASCII = 256;

	/*AC自动机的根结点，根结点不存储任何字符信息*/
	private Node root;

	private static Charset charset = Charset.forName("GBK");

	/*待查找的目标字符串集合*/
	private List<BlacklistEntry> target;

	/*表示在文本字符串中查找的结果，key表示目标字符串， value表示目标字符串在文本串出现的位置*/
	private HashMap<String, List<BlacklistEntry>> result;

	/*内部静态类，用于表示AC自动机的每个结点，在每个结点中我们并没有存储该结点对应的字符*/
	private static class Node{

		/*如果该结点是一个终点，即，从根结点到此结点表示了一个目标字符串，则str != null, 且str就表示该字符串*/
		String str;

		/*ASCII == 128, 所以这里相当于128叉树*/
		Node[] table = new Node[ASCII];

		Map<Character, Node> map = new HashMap<>();

		/*当前结点的孩子结点不能匹配文本串中的某个字符时，下一个应该查找的结点*/
		Node fail;

		List<BlacklistEntry> entries = new ArrayList<>();

		public boolean isWord(){
			return str != null;
		}

	}

	/*target表示待查找的目标字符串集合*/
	public AhoCorasickAutomation(List<BlacklistEntry> target){
		root = new Node();
		this.target = target;
		buildTrieTree();
		build_AC_FromTrie();
	}

	/*由目标字符串构建Trie树*/
	private void buildTrieTree()  {
		for (BlacklistEntry entry: target) {
			String[] keys = entry.key.split("\ue40a");
			for (String key: keys) {
				Node curr = root;
				byte[] bytes = key.getBytes(charset);
				for (int i = 0; i < bytes.length; i++) {
					int ch = bytes[i] + 128;
					if (curr.table[ch] == null) {
						curr.table[ch] = new Node();
					}
					curr = curr.table[ch];
				}
		        /* 将每个目标字符串的最后一个字符对应的结点变成终点 */
				curr.str = key;
				curr.entries.add(entry);
			}
		}
	}

	/*由Trie树构建AC自动机，本质是一个自动机，相当于构建KMP算法的next数组*/
	private void build_AC_FromTrie(){
        /*广度优先遍历所使用的队列*/
		LinkedList<Node> queue = new LinkedList<Node>();

        /*单独处理根结点的所有孩子结点*/
		for(Node x : root.table){
			if(x != null){
                /*根结点的所有孩子结点的fail都指向根结点*/
				x.fail = root;
				queue.addLast(x);/*所有根结点的孩子结点入列*/
			}
		}

		while(!queue.isEmpty()){
            /*确定出列结点的所有孩子结点的fail的指向*/
			Node p = queue.removeFirst();
			for(int i = 0; i < p.table.length; i++){
				if(p.table[i] != null){
                    /*孩子结点入列*/
					queue.addLast(p.table[i]);
                    /*从p.fail开始找起*/
					Node failTo = p.fail;
					while(true){
                        /*说明找到了根结点还没有找到*/
						if(failTo == null){
							p.table[i].fail = root;
							break;
						}

                        /*说明有公共前缀*/
						if(failTo.table[i] != null){
							p.table[i].fail = failTo.table[i];
							break;
						}else{/*继续向上寻找*/
							failTo = failTo.fail;
						}
					}
				}
			}
		}
	}

	/*在文本串中查找所有的目标字符串*/
	public BlacklistEntry find(String text) {
        /*创建一个表示存储结果的对象*/
		result = new HashMap<>();
		rlock.lock();
		Node curr = root;
		int i = 0;
		byte[] bytes = text.getBytes(charset);
		while(i < bytes.length){
            /*文本串中的字符*/
			int ch = bytes[i] + 128;

            /*文本串中的字符和AC自动机中的字符进行比较*/
			if(curr.table[ch] != null){
                /*若相等，自动机进入下一状态*/
				curr = curr.table[ch];

				if(curr.isWord()){
					result.put(curr.str, curr.entries);
				}

                /*这里很容易被忽视，因为一个目标串的中间某部分字符串可能正好包含另一个目标字符串，
                 * 即使当前结点不表示一个目标字符串的终点，但到当前结点为止可能恰好包含了一个字符串*/
				if(curr.fail != null && curr.fail.isWord()){
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
		for(Entry<String, List<BlacklistEntry>> entry : result.entrySet()){
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
		List<BlacklistEntry> target = new ArrayList<>();

		try (BufferedReader reader = Files.newBufferedReader(Paths.get("D://xshell//secadmin_output1_new.dat.agent"), charset)) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] ls = line.split("\t");
				BlacklistEntry entry = new BlacklistEntry();
				entry.id = ls[15];
				entry.key = ls[1];
				entry.value = Integer.parseInt(ls[2]);
				entry.keys = entry.key.split("\ue40a");
				entry.multiNum = entry.keys.length;
				target.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		AhoCorasickAutomation aca = new AhoCorasickAutomation(target);

		String text = "饲料稀疏rss临 - 搜狗百科 人民报消息,de pers的消息,新鹿特丹商业报”parool新闻,玻璃,nrc的下一则新闻,荷兰语日报,widgetboards,德宏声,noorden新闻日报“国家报”新闻 ...";
		long begin = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		System.out.println(aca.find(text));
		System.out.println(System.currentTimeMillis() -begin);


	}
}