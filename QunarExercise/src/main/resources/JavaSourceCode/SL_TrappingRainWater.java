package zhangxl;

import java.util.HashSet;

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it is able to trap after raining.
 * @author maoge
 *
 */
public class SL_TrappingRainWater {

	public int trap(int[] height){
		//长度为0的
		if(height.length == 0) 
			return 0 ;
		//创建左右数组，分别储存第i个bar左右最高的bar
		int[] left = new int[height.length];
		int[] right = new int[height.length];
		left[0] = height[0];
		for(int i=1 ; i < left.length ; i++){
			left[i] = Math.max(left[i-1], height[i]);
		}
		right[height.length-1] = height[height.length-1];
		for(int i=height.length-2 ; i >= 0 ; i--){
			right[i] = Math.max(right[i+1], height[i]);
		}
		//计算第i个位置储存的水量，计算方法：min(left[i],right[i])-height[i],然后把每个位置的水量加起来
		int sum = 0 ;
		for(int i = 1 ; i < height.length-1 ; i++){
			sum += Math.min(left[i], right[i])-height[i] ;
		}
		return sum;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] height=new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(new SL_TrappingRainWater().trap(height));
	}

}
