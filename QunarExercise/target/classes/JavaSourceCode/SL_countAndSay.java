package zhangxl;
/**
 * The count-and-say sequence is the sequence of integers beginning as follows:
 *	1, 11, 21, 1211, 111221, ...
 * @author maoge
 *
 */
public class SL_countAndSay {

	public String countAndSay(int n){
		//n=1时输出为1
		if(n == 1){
			return "1";
		}
		String s="1";
		StringBuffer ret = new StringBuffer();
		int cnt = 0 ;   //记录出现的次数
		int round = 0 ;  //round是迭代多少次
		int i ;
		while(++round < n){
			cnt = 1 ;
			ret.setLength(0);
			for(i = 1 ; i < s.length() ; i++){
				if(s.charAt(i) == s.charAt(i-1)){//重复的值，继续计数
					cnt++ ;
				}else{
					ret.append(cnt).append(s.charAt(i-1));
					cnt = 1 ;   //重置
				}
			}
			ret.append(cnt).append(s.charAt(i-1));
			s=ret.toString();
			//round++ ;
		}
		return ret.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 7;
		System.out.println(new SL_countAndSay().countAndSay(n));
	}

}
