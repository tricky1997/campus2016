package zhangxl;

public class SL_DivideTwoIntegers {
	public int divide(int dividend , int divisor){
		int result = 0;
		//除数为0，返回最大值
		if(divisor == 0){
			return Integer.MAX_VALUE ;
		}
		//判断最终结果是否为负
		boolean isNeg = (dividend > 0 && divisor < 0) 
					|| (dividend < 0 && divisor > 0);
		//除数大于被除数，返回0
		if(divisor > dividend){
			return 0 ;
		}
		dividend = Math.abs(dividend);
		divisor = Math.abs(divisor);
		int digit = 0 ;
		//左移的次数
		while(divisor <= (dividend >> 1)){
			divisor <<= 1 ;
			digit++ ;
		}
		while(digit >= 0){
			if(dividend >= divisor){
				result += 1<<digit ;
				dividend -= divisor ;
			}
			divisor >>= 1 ;
			digit-- ;
		}
		return isNeg?-result:result ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new SL_DivideTwoIntegers().divide(-1010369383, -2147483648));
		//System.out.println(Integer.MIN_VALUE);
	}

}
