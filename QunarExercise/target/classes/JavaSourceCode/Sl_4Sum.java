package zhangxl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
/**
 * Given an array S of n integers, are there elements a, b, c in S 
 * such that a + b + c = 0? Find all unique triplets in the array 
 * which gives the sum of zero
 */
import java.util.List;
/**
 * 解决方法：先对整数进行排序,然后左右夹逼，跳过重复的数，复杂度O(n^2)
 * @author maoge
 *
 */
public class Sl_4Sum {
	public List<List<Integer>> treeSum(int[] nums,int target){
		//保存结果
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		//当数组的长度小于4，直接返回null
		if(nums.length < 4) return results;
		//对数组进行排序,排序的复杂度O(n*logn)
		Arrays.sort(nums); //调用Arrays类中的sort方法，得到的nums即是排序好的
		
		//final int target = 0 ;
		
		//数组最后一个数据的指针
		int last = nums.length - 1 ;
		//从数组的第一个数据开始,到倒数第3个数
		for (int first = 0 ; first < last - 2 ; first++){
			for (int i = first+1 ; i < last - 1 ; i++){			
				int k = last ;
				int j = i + 1 ;
				//遇到nums[i]重复，跳过
				if(i > first + 1 && nums[i] == nums[i-1]) continue ;
				//从第i+1个数开始左右夹逼，遇到重复的数跳过
				while(j < k){
					if(nums[first]+nums[i] + nums[j] + nums[k] < target){
						//三个数之和小于target，则j加1
						j++ ;
						//跳过重复的数
						while(nums[j] == nums[j - 1] && j < k)
							j++;
					}
					//三个数之和小于target，则k-1
					else if( nums[first]+nums[i] + nums[j] + nums[k] > target ){
						k-- ;
						//跳过重复的数
						while(nums[k] == nums[k+1] && j < k) k-- ;
					}
					else {					
						ArrayList<Integer> list = new ArrayList<Integer>() ;
						list.add(Integer.valueOf(nums[first]));
						list.add(Integer.valueOf(nums[i]));
						list.add(Integer.valueOf(nums[j]));
						list.add(Integer.valueOf(nums[k]));
						results.add(list);
						//跳过重复部分
						j++;
						k--;
						while(nums[j] == nums[j-1] && nums[k] == nums[k+1] && j<k)
							j++ ;
					}
				}
			}
		}
		//去掉重复的部分
		HashSet h = new HashSet<List<Integer>>(results);
		results.clear();
		results.addAll(h);
		return results;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int[] nums = new int[]{-1,0,1,2,-1,-4,2,-3,3,6};
		int[] nums = new int[]{-1,-5,-5,-3,2,5,0,4};
		Sl_4Sum sl = new Sl_4Sum();
		List<List<Integer>> results = sl.treeSum(nums , -7);
		//使用迭代器对List进行输出
		Iterator<List<Integer>> it = results.iterator();
		while(it.hasNext()){
			List<Integer> list = it.next();
			System.out.println(list.get(0)+","+
					list.get(1)+","+list.get(2)+","+list.get(3));
		}
	}

}
