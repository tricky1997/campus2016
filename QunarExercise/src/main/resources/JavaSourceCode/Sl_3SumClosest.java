package zhangxl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target. 
 * Return the sum of the three integers.
 * You may assume that each input would have exactly one solution.
 * 
 * 解决方法：先对整数进行排序,然后左右夹逼，跳过重复的数，复杂度O(n^2)
 * @author maoge
 *
 */
public class Sl_3SumClosest {
	public int threeSum(int[] nums , int target){
		//保存结果
		int results = 0;
		//当数组的长度小于3，直接返回null
		//if(nums.length < 3) return results;
		//对数组进行排序,排序的复杂度O(n*logn)
		Arrays.sort(nums); //调用Arrays类中的sort方法，得到的nums即是排序好的		
		
		//和target之差的最小值，初始化为Int型的最大值
		int Min = Integer.MAX_VALUE; 
		
		for(int i=0;i<nums.length;i++){
			int j = i + 1 ;
			int k = nums.length - 1 ;
			while(j < k){
				//3个数之和
				int sums = nums[i] + nums[j] + nums[k] ;
				int gap = Math.abs(sums - target);
				if(gap < Min){
					results = sums ;
					Min = gap ;
				}
				if(sums < target) j++ ;
				else k--;
			}
		}
		return results;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{0,2,1,-3};
		Sl_3SumClosest sl = new Sl_3SumClosest();
		int target = 1 ;
		int results = sl.threeSum(nums, target);
		System.out.println(results);
		
	}

}
