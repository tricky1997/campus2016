package zhangxl;

import java.util.HashMap;



public class SLisAnagram {

	/**
	 * 思路1：易位词，对出现的字符进行计数，存在HashMap中，key为字符，value为出现的次数，一个字符串存入map，另一个字符串取出map，最后判断map是否为空，复杂度为O(m+n)
	 * 思路2：对字符进行排序，两者相等则是易位词，复杂度为O(nlogn)
	 * @param s
	 * @param t
	 * @return
	 */
	public static boolean isAnngram(String s , String t){
		if(s.length() != t.length())
			return false ;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if(map.containsKey(c)){
				int count = map.get(c);
				count++ ;
				map.put(c, count) ;
			}
			else {
				map.put(c, 1);
			}
		}
		for (int i = 0; i < t.length(); i++) {
			Character c = t.charAt(i);
			if(map.containsKey(c)){
				int count = map.get(c);
				count-- ;
				map.put(c, count);
				if(count == 0)
					map.remove(c);
			}
			else return false ;
		}
		return map.isEmpty();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "anagram";
		String t = "nagaram";
		System.out.println(isAnngram(s, t));
	}

}
