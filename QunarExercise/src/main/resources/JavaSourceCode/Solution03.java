package zhangxl;

import java.util.HashMap;

public class Solution03 {
	
	public static int lengthOfLongerstSubstring(String s){
		//最长子串的长度
		int MaxLength = 0 ;
		//字符串的两个指针，Shead为头指针，Stail为尾指针
		int Shead=0,Stail=0;
		while(Stail < s.length()-1){
			//获取子串
			Stail++ ;
			String ss = s.substring(Shead, Stail);
			//尾指针向后移动
			String Schar = s.charAt(Stail) +"";
			if(ss.contains(Schar)){
				//子串出现了重复字符
					//与最优值做比较，若大于最优值，则用最新的值替换
					MaxLength = Math.max(MaxLength, Stail - Shead) ;
				//令此时的头指针指向上一个重复字母的index+1
				int i = ss.indexOf(Schar);
				Shead = Shead + ss.indexOf(Schar) + 1;
			}
		}
		return Math.max(MaxLength, s.length() - Shead);
		/*char[] arr = s.toCharArray();
		int pre = 0 ;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for(int i=0 ; i<arr.length ; i++){
			if(!map.containsKey(arr[i])){
				map.put(arr[i],i);
			}else {
				pre = Math.max(pre, map.size());
				i = map.get(arr[i]);
				map.clear();
			}
		}
		return Math.max(pre, map.size());*/
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "abcabcdbbabcdebbbbabcdefghijklmn!@#$%^&*()_+" ;
		System.out.println(lengthOfLongerstSubstring(string));
	}

}
