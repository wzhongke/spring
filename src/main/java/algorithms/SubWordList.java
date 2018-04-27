package algorithms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;

public class SubWordList {

	private ArrayList<BlacklistEntry<String[]>> wordList;
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public SubWordList(){
		this(10);
	}
	public SubWordList(int n){
		wordList = new ArrayList<BlacklistEntry<String[]>>(n);
	}
	public void insert(String[] words, String rule_id, int level){
		insert(words, rule_id, level, BlacklistEntry.WARN_LEVEL_INVALID);
	}
	public void insert(String[] words, String rule_id, int level, int warnLevel){
		insert(words, rule_id, level, warnLevel, 0, 0);
	}
	public void insert(String[] words, String rule_id, int level, int warnLevel, long startTime, long endTime){
		if( words == null || words.length == 0 ) return;
		BlacklistEntry<String[]> entry = new BlacklistEntry<String[]>();
		entry.key = words;
		entry.id = rule_id;
		entry.value = level;
		entry.warnLevel = warnLevel;
		rwLock.writeLock().lock();
		wordList.add( entry );
		rwLock.writeLock().unlock();
	}
	public BlacklistEntry check( String[] words ){
		if( words == null || words.length == 0 ) return null;
		rwLock.readLock().lock();
		BlacklistEntry<String[]> ent = null;
		boolean allMatched = false;
		for( int i = 0; i < wordList.size(); i++ ){
			ent = wordList.get(i);
			String[] terms = ent.key;
			int ti = 0;
			// ��ѯÿһ���ؼ���
			for( ; ti < terms.length; ti ++ ){
				int wi = 0;
				for( ; wi < words.length; wi ++ ){
					// �����ƥ��Ĵʵĳ��ȱȺ���������Ĵʵĳ���С����ô������ѯ
					if( words[wi].length() < terms[ti].length() ){
						continue;
					}
					// �����ƥ����а�����������
					if( words[wi].indexOf( terms[ti] ) >= 0 ){
						break;
					}
				}
				if( wi == words.length ){ // û�е�
					break;
				}
			}

			// thisMathed˵���������򣬶�ÿ����ѯ�ʶ�����
			if( ti == terms.length ){
				allMatched = true;
				break;
			}
		}
		rwLock.readLock().unlock();

		return allMatched ? ent : null;
	}
	

	private static final Pattern patt = Pattern.compile("\ue40a");
	public BlacklistEntry check(String strs) {
		return check(patt.split(strs) );
	}


	public SubWordList loadSubWordFile( String filename ) throws IOException {
		BufferedReader reader = null;
		SubWordList ret = null;
		try{
			reader = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "GBK"));
			SubWordList sub = new SubWordList();
			String[] data = new String[16];
			for(;;){
				String line = reader.readLine();
				if( line == null ) break;
				preserveLine( line.toLowerCase(), sub, data);
			}
			ret = sub;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if( reader != null ){
				try{
					reader.close();
				}catch(IOException e){}
				reader = null;
			}
		}
		return ret;
	}

	private void preserveLine( String line, SubWordList bl, String[]data ){
		parseLine( line, data);

		String rule_id = data[15];
//		String warnLevel = data[1];
		String list = data[1];
		String[]words = null;
		if( list != null ){
			words = patt.split(list);
		}
		if( words == null || words.length == 0 ) {
			System.err.println("[WARN]Invalid Line in SubWordBlacklist:" + line);
			return;
		}

		int warnLevel = -2;

		if (data.length >= 6) {
			try {
				warnLevel = Integer.parseInt(data[5]);
			} catch (Exception e) {
				warnLevel = -2;
			}
		}

		bl.insert(words, rule_id, -1, warnLevel);
	}

	private int parseLine( String line, String[]data){
		int left = 0;
		int fi = 0;
		while( left < line.length() ){
			int right = line.indexOf('\t', left);
//			if( right != left ){
//				if( right < 0 ) right = line.length();
//				String val = line.substring(left, right );
//				if( fi < data.length ){
//					data[fi] = val;
//				}
//			}
			//����\t��ʾ�м��п���
			if( right < 0 ) right = line.length();
			String val = line.substring(left, right );
			if( fi < data.length ){
				data[fi] = val;
			}
			left = right + 1;
			fi ++;
		}
		//����������㣬���ʣ�µ�data�����ݣ�����Ӱ��������
		for (int i = fi; i < data.length; i++) {
			data[i] = "";
		}
		return fi;
	}

}
