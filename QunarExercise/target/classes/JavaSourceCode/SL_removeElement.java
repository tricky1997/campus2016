package zhangxl;
/**
 * Given an array and a value, remove all instances of that value in place and return the new length.
 * @author maoge
 *
 */
public class SL_removeElement {
	/**
	 * 和removeDuplicates类似，除了返回新的数组的大小，还得改变数组，删掉Element的值
	 * @param nums
	 * @param val
	 * @return
	 */
	public int removeElement(int[] nums , int val){
		int removeCount = 0 ; 
		for(int i = 0 ; i < nums.length ;i++){
			if(nums[i] == val)
				removeCount++ ;
			else {
				nums[i-removeCount] = nums[i];
			}
		}
		
		return nums.length - removeCount ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{1,2,2,3,3,3,4,4,4,4};
		int val = 3 ;
		int length = new SL_removeElement()
						.removeElement(nums, val);
		System.out.println(length);
		for(int i = 0 ; i < length ; i++){
			System.out.println(nums[i]);
		}
	}

}
