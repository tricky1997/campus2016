package zhangxl;

public class Sl_ReverseInteger {
	/*public int reverse(int x){
		Integer int_x = (Integer)x;
		String s_x_reverse = null ;
		if( x>=0 ){
			String s_x = int_x.toString();
			s_x_reverse = new StringBuilder(s_x).reverse().toString();
		}
		else if(x<0){
			String s_x = int_x.toString();
			s_x_reverse = new StringBuilder(s_x.substring(1)).reverse().toString();			
			s_x_reverse = s_x.charAt(0) + s_x_reverse ;
		}
		 
		int result = Integer.valueOf(s_x_reverse); 
		return result ;
	}*/
	/*public int reverse(int x){
		long  r = 0;
		final int max = 0x7fffffff ; //int型最大值
		final int min = 0x80000000 ; //int最小值
		
		while(x!=0){
			r = r * 10 + x % 10 ;
			if(r > max || r < min){
				r = r > 0 ? max : min ;
				return (int) r ;
			}
			x = x / 10 ;
		}
		return (int) r ;
	}*/
	public int reverse(int x){
		if(x == Integer.MIN_VALUE)
			return 0 ;
		int num = Math.abs(x);
		int res = 0 ;
		while(num != 0){
			if(res >(Integer.MAX_VALUE - num%10)/10)
				return 0 ;
			res = res *  10 + num % 10 ;
			num /= 10 ;
		}
		return x>0?res:-res ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int  x = 153423646 ;
		System.out.println(new Sl_ReverseInteger().reverse(x));
	}

}
