package zhangxl;
/**
 * Implement strStr().
 * @author maoge
 *
 */
public class SL_strStr {

	/**
	 * 暴力匹配
	 * @param haystack
	 * @param needle
	 * @return
	 */
	/*public int strStr(String haystack , String needle){
		int i = 0 ; 
		int j = 0 ;
		while(i < (haystack.length()) && j < needle.length() ){
			if(haystack.charAt(i) == needle.charAt(j)){
				i++ ; 
				j++ ;
			}
			else {
				i = i - j + 1 ; //i从下一个字符匹配
				j = 0 ;   // j复位
			}
			if( j == needle.length())
				return i - j ;
		}
		return j!=needle.length() ? -1 : i - j ;
	}*/
	public int strStr(String haystack , String needle){
		return 0 ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String haystack = "aaa";
		String needle = "aaa" ;
		System.out.println(new SL_strStr().strStr(haystack, needle));
	}

}
