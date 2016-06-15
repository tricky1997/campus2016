package zhangxl;
/**
 * Given an unsorted integer array, find the first missing positive integer.
 * @author maoge
 *
 */
public class SL_FirstMissingPositive {
	/**
	 * 思路：把出现的数值放到与下标一致的位置，再判断什么位置最先出现不连续的数值
	 * @param nums
	 * @return
	 */
	public int firstMissingPositive(int[] nums){
		if(nums.length == 0)
			return 1 ;
		//1.把出现数值放在与下标一致的位置
		for(int i = 0 ; i<nums.length ; i++){
			if(nums[i]>0 && nums[i]<nums.length){
				if(nums[nums[i]-1]!=nums[i]){
					int temp = nums[nums[i]-1];
					nums[nums[i]-1]=nums[i];
					nums[i] = temp ;
					i-- ;
				}
			}
		}
		//2.判断最先不出现连续的数值
		int j ;
		for(j=0 ; j<nums.length ; j++){
			if((nums[j]-1)!=j)
			return j+1 ;	
		}
		return j+1;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{3,4,-1,1};
		System.out.println(new SL_FirstMissingPositive().firstMissingPositive(nums));
	}

}
