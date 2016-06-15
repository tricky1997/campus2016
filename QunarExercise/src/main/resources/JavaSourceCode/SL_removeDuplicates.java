package zhangxl;
/**
 * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
 * 
 * @author maoge
 *
 */
public class SL_removeDuplicates {
	/**
	 * 删除数组中的重复数字，返回唯一元素的大小，nums数组要变化
	 * 解题思路：
	 * 1.数组长度为0 ， 直接返回0，nums数组不变化
	 * 2.数组长度不为0 ， 当nums[i]==nums[i-1],跳过这个数，直到nums[i]!=nums[i-1],count++,并将
	 * nums[i]覆给nums[i-1]
	 * @param nums
	 * @return
	 */
	public int removeDupicates(int[] nums){
		int result = 0 ;
		if(nums.length < 1)
			return 0 ;
		else result ++ ;
		for(int i = 1 ; i < nums.length ; i++ ){
			/*if(i == 0)
				result++ ;*/
			if(nums[i] == nums[i-1])
				continue ;
			else if(nums[i]!=nums[i-1]) {
				nums[result] = nums[i] ;
				result ++ ;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		int[] nums = new int[]{1,1,2,2,2,3,3,4,4,4,4} ;//{1,1,2,2,2,3,3,4,4,4,4}
		int length = new SL_removeDuplicates().removeDupicates(nums) ;
		System.out.println(length);
		for(int i = 0 ; i < length ; i ++){
			System.out.println(nums[i]);
		}
	}
}
