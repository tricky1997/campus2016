package zhangxl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
 * @author maoge
 *
 */
public class SL_CombinationSum2 {

	public List<List<Integer>> combinationSum2(int[] candidates , int target){
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		//candidates为空或者长度为0
		if(candidates == null || candidates.length == 0){
			return res ;
		}
		//先排序
		Arrays.sort(candidates);
		Helper(candidates, 0, target, new ArrayList<Integer>(), res);
		return res  ;
	}
	private void Helper(int[] candidates , int start , int target , 
					ArrayList<Integer> item , List<List<Integer>> res){
		if(target < 0)
			return ;
		if(target == 0){
			if(!res.contains(item))
				res.add(new ArrayList<Integer>(item));
			//res.add(item);
			return ;
		}
		for(int i = start ; i < candidates.length ; i++){
			//跳过重复的
			/*if(i > 0 && candidates[i]==candidates[i-1]){
				continue ;
			}*/
			item.add(candidates[i]);
			Helper(candidates, i+1, target-candidates[i], item, res);
			item.remove(item.size()-1);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		int[] candidates = new int[]{10,1,2,7,6,1,5};
		int target = 8 ;
		results = new SL_CombinationSum2().combinationSum2(candidates, target);
		Iterator it = results.iterator();
		while(it.hasNext()){
			Iterator IntIt = ((ArrayList<Integer>)it.next()).iterator();
			while(IntIt.hasNext()){
				System.out.println(IntIt.next());
			}
		}
	}

}
