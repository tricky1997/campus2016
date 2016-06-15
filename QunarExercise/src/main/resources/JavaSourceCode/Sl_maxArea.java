package zhangxl;
/**
 * Given n non-negative integers a1, a2, ..., an,
 * where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is
 *  at (i, ai) and (i, 0). 
 * Find two lines, which together with x-axis forms a container, 
 * such that the container contains the most water.
 * 题意大致是在x轴上有1,2,...,n个点，长度依次是a1,a2,...an，求任意两个线段之间的与x轴围成的容器能容纳的最多的水是多少。
 * 
 * @author maoge
 *
 */

public class Sl_maxArea {

	/**
	 * 采用贪心算法，用两个指针i,j指向首尾两个数字，比较这两个数字的大小，若a[i]<a[j]则i++，否则j--；
	 * 复杂度为O(n)
	 * @param height
	 * @return
	 */
	public int maxArea(int[] height){
		//记录最大的面积
		int max = 0 ;
		//收尾两个指针
		int i,j;
		i = 0 ;
		j = height.length - 1 ;
		while(i<j){
			//当height[i] <= height[j]时
			if(height[i] <= height[j]){
				//计算面积
				max = Math.max(max, (j-i)*height[i]);
				i++;
			}
			//当height[i] > height[j]时
			else{
				//计算面积
				max = Math.max(max, (j-i)*height[j]);
				j-- ;
			}
		}
		return max ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] height = new int[]{1,1};
		System.out.println(new Sl_maxArea().maxArea(height));
	}

}
