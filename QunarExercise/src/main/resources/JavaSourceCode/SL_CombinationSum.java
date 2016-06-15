package zhangxl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
 * 
 * @author maoge
 *
 */
public class SL_CombinationSum {
	/**
	 * N皇后问题，基本思路是先排好序，然后每次递归中把剩下的元素一一加到结果集合中，并且把目标减去加入的元素，
	 * 然后把剩下元素（包括当前加入的元素）放到下一层递归中解决子问题。
	 * @param candidates
	 * @param target
	 * @return
	 */
	public List<List<Integer>> combinationSum(int[] candidates, int target){
		//保存结果
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		//数组长度为0或为空的情况
		if(candidates == null || candidates.length == 0){
			return res ;
		}
		//排序
		Arrays.sort(candidates);
		helper(candidates, 0, target, new ArrayList<Integer>(), res);
		return res;
	}
	//DFS
	private void helper(int[] candidates , int start , int target 
				, ArrayList<Integer> item ,List<List<Integer>> res){
		//target<0没有寻找到结果
		if(target < 0)
			return ;
		if(target == 0){
			//找到一个结果，添加
			res.add(new ArrayList<Integer>(item));
			//res.add(item);这样做为什么不行。。。
			return ;
		}
		//从start找
		for(int i = start ; i < candidates.length ; i++){
			if(i>0 && candidates[i]==candidates[i-1]){
				continue ;
			}
			//添加第i个数据
			item.add(candidates[i]);
			//递归调用
			helper(candidates , i ,target - candidates[i] , item , res) ;
			//清空list，以备下次调用
			item.remove(item.size()-1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		int[] candidates = new int[]{2,3,6,7};
		int target = 7 ;
		results = new SL_CombinationSum().combinationSum(candidates, target);
		Iterator it = results.iterator();
		while(it.hasNext()){
			Iterator IntIt = ((ArrayList<Integer>)it.next()).iterator();
			while(IntIt.hasNext()){
				System.out.println(IntIt.next());
			}
		}
	}

}
