package zhangxl;
/**
 * Given two numbers represented as strings, return multiplication of the numbers as a string.
 * @author maoge
 *
 */
public class SL_MultiplyString {
 /**
  * 1 翻转string
	2 建立数组，双层循环遍历两个string，把单位的乘积累加到数组相应的位置
	3 处理进位并输出
	4 注意前导零的corner case
  * @param num1
  * @param num2
  * @return
  */
	public String multiply(String num1, String num2) {
		//翻转字符串
		String n1 = new StringBuilder(num1).reverse().toString() ;
		String n2 = new StringBuilder(num2).reverse().toString() ;
		//存放乘积，两个数相乘，结果不会超过两个数长度之和
		int[] d = new int[num1.length()+num2.length()]; 
		for(int i = 0 ; i < num1.length() ; i++){
			for(int j = 0 ; j < num2.length() ; j++){
				d[i+j] += (n1.charAt(i)-'0')*(n2.charAt(j)-'0');
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < d.length ; i++){
			int digit = d[i]%10 ; //当前位
			int carry = d[i]/10 ; //进位
			if(i+1 < d.length){
				d[i+1]+=carry ;
			}
			sb.insert(0, digit);
		}
		//移除最高位的0
		while(sb.charAt(0)=='0' && sb.length() > 1){
			sb.deleteCharAt(0);
		}
		return sb.toString() ;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String num1 = "180";
		String num2 = "20";
		System.out.println(new SL_MultiplyString().multiply(num1, num2));
	}

}
