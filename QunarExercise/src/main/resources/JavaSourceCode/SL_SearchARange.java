package zhangxl;
/**
 * Given a sorted array of integers, find the starting and ending position of a given target value.
 * 找到在一个有序数组中某个数的起始位置和终止位置
 * @author maoge
 *
 */
public class SL_SearchARange {
	/**
	 * 二分查找
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] searchRange(int[] nums , int target){
		int[] results = new int[2];
		results[0] = -1 ;
		results[1] = -1 ;
		//为空或者长度为0
		if(nums == null || nums.length == 0){
			return results ;
		}
		else if(nums.length == 1 && nums[0] == target){
			results[0] = 0 ;
			results[1] = 0 ;
		}
		int left = 0 ; //左指针
		int right = nums.length - 1 ; // 右指针
		int medium = 0 ;
		while(left <= right){
			medium = (left + right) / 2 ;
			//找到目标数据
			if(target == nums[medium]){
				int start = medium ;
				int end = medium ;
				//向左找起始位置
				while(start >0 && nums[start-1] == nums[start])
					start-- ;
				while(end < nums.length -1 && nums[end] == nums[end+1])
					end++ ;
				results[0] = start ;
				results[1] = end ;
			}
			//目标数据小于中位数
			if(target < nums[medium]){
				right = medium - 1 ;
			}
			//目标数据大于中位数
			else {
				left = medium + 1 ;
			}
		}
		return results;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{1};
		int target = 2 ;
		int[] results = new SL_SearchARange().searchRange(nums, target);
		System.out.println("结果是："+results[0]+","+results[1]);
	}	

}
