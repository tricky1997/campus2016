package zhangxl;
/**
 * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
 * [1,3,5,6], 5 → 2
   [1,3,5,6], 2 → 1
   [1,3,5,6], 7 → 4
   [1,3,5,6], 0 → 0
 * @author maoge
 *
 */

public class SL_SearchInsertPosition {

	/**
	 * 二分查找
	 * @param nums
	 * @param target
	 * @return
	 */
    public int searchInsert(int[] nums, int target) {
        int result = 0; //保存结果
        //nums为空或者长度为0，返回0
        if(nums == null || nums.length == 0){
        	return 0;
        }
        int left = 0 ; //左指针
        int right = nums.length - 1; //右指针
        int medium = 0 ;
        while(left <= right){
        	medium = (left + right) / 2 ;
        	if(target < nums[medium]){
        		right = medium - 1 ;
        	}
        	else if(target > nums[medium]){
        		left = medium + 1 ;
        	}else if(target == nums[medium]){
            	return medium ;
            }
        }
    	return left ;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{1,3,5,6};
		int target = 2;
		System.out.println(new SL_SearchInsertPosition().searchInsert(nums, target));
	}

}
