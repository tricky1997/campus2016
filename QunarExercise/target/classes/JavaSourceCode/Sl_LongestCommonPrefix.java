package zhangxl;
/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * @author maoge
 *
 */
public class Sl_LongestCommonPrefix {
	
	/**
	 * 纵向比较，从第0个字符开始，比较每一个字符串的每一个位置的字符是否相等，知道遇到第一个不匹配的
	 * @param strs
	 * @return
	 */
	 public String longestCommonPrefix(String[] strs) {
		 //保存结果
		 String results = "" ;
		 
		 if(strs.length == 0) return "";
		 //根据第一个字符串一个一个比较字符
		 for(int i = 0 ; i < strs[0].length() ; i++){
			 //从第二个字符串开始与第一个字符串的字符一个一个比较
			 for(int j = 1 ; j < strs.length; j++){
				//大于第j个字符串的长度
				if( i >= strs[j].length())
					return strs[0].substring(0, i);
				else if(strs[j].charAt(i)!=strs[0].charAt(i)) 
					 return strs[0].substring(0, i);
			 }
			 results = strs[0];
		 }
		 return results ;
	    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] strs = new String[]{""};
		System.out.println(
				new Sl_LongestCommonPrefix().longestCommonPrefix(strs));
	}

}
