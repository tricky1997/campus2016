package zhangxl;
/**
 * 判断一个整数是否是回文串，不使用额外的空间
 * @author maoge
 * 
 */
public class Sl_isPalindrome {

	/**
	 * 转换成字符串会使用到额外的空间，不考虑
	 * 进行翻转可能会造成溢出的问题
	 * @param x
	 * @return
	 */
	public boolean isPalindrome(int x){
		if(x < 0){
			return false ;
		}
		//记录整数的最大位数
		int len = 1 ;
		while(x / len >= 10){
			len = len * 10 ;
		}
		while(x != 0){
			//最高位
			int left = x / len ;
			//最低位
			int right = x % 10 ;
			//判断是否相等
			if(left != right){
				return false ;
			}
			//去掉最高位和最低位
			x = (x%len) / 10 ;
			//
			len = len / 100 ;
		}
		return true ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 12321 ;
		System.out.println(new Sl_isPalindrome().isPalindrome(x));
	}

}
