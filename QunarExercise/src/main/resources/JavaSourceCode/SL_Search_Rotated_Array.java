package zhangxl;
/**
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 * @author maoge
 *
 */
public class SL_Search_Rotated_Array {
	//二分查找，比有序的二分查找要复杂一些
	public int search(int[] nums,int target){
		//为空或者长度为0，返回1
		if(nums == null || nums.length == 0){
			return -1 ;
		}
		int left = 0;
		int right = nums.length - 1 ;

		//分三种情况
		while(left <= right){
			int m = (left + right) / 2 ;
			//相等
			if(target == nums[m])
				return m ;
			//中位数大于右边缘的数，则m->right是有序的
			if(nums[m] < nums[right]){
				//target在m->right之间
				if(target > nums[m] && target <= nums[right]){
					left = m + 1 ;
				}
				//target不在m->right之间
				else {
					right = m - 1 ;
				}
			}
			//中位数大于左边缘的数，则left->m是有序的
			else if(nums[m] >= nums[right]){
				//target在left->m之间
				if(target >= nums[left] && target <nums[m]){
					right = m - 1 ;
				}
				//target在left->m之间
				else{
					left = m + 1 ;
				}
			}
		}
		return -1 ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] nums = new int[]{1,3};
		int target = 0;
		System.out.println(new SL_Search_Rotated_Array().search(nums, target));
	}

}
