package zhangxl;
/**
 * 实现字符串到整数之间的转换
 * 
 * 分析：考虑到几点特殊的字符
 * 1.空字符串或者空指针
 * 2.空格
 * 3.正负号
 * 4.计算真实值
 * 5.溢出的处理
 * @author maoge
 *
 */
public class Sl_atoi {

	public int atoi(String str){
		//去掉空格
		//str = str.replace(" ", "");
		//去掉字符串首尾的空格
		str = str.trim();
		//处理空字符串或者空指针
		if(str == null || str.length()<1){
			return 0 ;
		}
		char flag = '+';
		int i = 0 ;
		if(str.charAt(0) == '-'){
			flag = '-';
			i++ ;
		}else if(str.charAt(0) == '+'){
			i++;
		}
		//使用double类型存储结果
		double result = 0 ;
		while(str.length() > i && str.charAt(i) >= '0' && str.charAt(i)<='9'){
			result = result * 10 + (str.charAt(i) - '0');
			i++ ;
		}
		if(flag == '-'){
			result = -result ;
		}
		if(result > Integer.MAX_VALUE){
			return Integer.MAX_VALUE ;
		}
		if(result < Integer.MIN_VALUE){
			return Integer.MIN_VALUE ;
		}
		return (int)result ;
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "  -123 45  " ;
		System.out.println(new Sl_atoi().atoi(string));
	}

}
