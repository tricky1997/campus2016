package zhangxl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 找出一个字符串数组中所有的易位词
 * @author maoge
 *
 */
public class SL_Anagrams {
/**
 * 思路：对字符串数组中的每一个字符串进行排序，如果是易位词，则排序结果相同，然后将排序后的结果作为Key存入HashMap中，Value则为该字符串所在的索引，
 * 当判断到HashMap中包含某一Key，则将这个Key的所有字符串输出
 * @param strs
 * @return
 */
	public List<String> angrams(String[] strs){
		List<String> results = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		int len = strs.length ;
		for(int i = 0 ; i < strs.length ; i++){
			char[] a = strs[i].toCharArray() ;			
			Arrays.sort(a);
			String b = new String(a) ;
			if(map.containsKey(b)){
				if(!results.contains(strs[map.get(b)]))
					results.add(strs[map.get(b)]);
				map.put(b, i);
				results.add(strs[i]);
			}
			else{
				map.put(b, i);
			}
			
		}
		return results ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strs = new String[]{"abcddabc","cbadacbd","adbccdba","dbacacbd","ashfkh","ashkh"} ;
		SL_Anagrams an = new SL_Anagrams();
		List<String> res = new ArrayList<String>();
//		HashMap<String , Integer> tMap = new HashMap<String, Integer>();
//		tMap.put("001",1);
//		tMap.put("001", 2);
//		Map里面相同的键值会覆盖前面的键值
		res = an.angrams(strs);
		Iterator it = res.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
//		System.out.println("finished");
	}

}
