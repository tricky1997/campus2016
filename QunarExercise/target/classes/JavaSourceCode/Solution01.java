package zhangxl;

import java.util.HashMap;
import java.util.Map;

public class Solution01 {

	/**
	 * @param args
	 */
	public  int[] twoSum(int[] nums , int target){
		Map<Integer , Integer> mapping =new HashMap<Integer , Integer>();
		int[] results = new int[2];
		for(int i=0 ; i<nums.length ; i++){
			mapping.put(nums[i], i);
		}
		for(int i=0 ; i<nums.length;i++){
			int gap = target - nums[i];
			if(mapping.containsKey(gap) && mapping.get(gap)>i){
				results[0] = i+1;
				results[1] = mapping.get(gap)+1;
			}
		}
		return results ;		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] numbers = new int[]{2,7,11,15};
		int target = 9 ;
		int[] results = new Solution01().twoSum(numbers , target);
		System.out.println("index1="+results[0]+","+"index2="+results[1]);
		
		
		
	}

}
