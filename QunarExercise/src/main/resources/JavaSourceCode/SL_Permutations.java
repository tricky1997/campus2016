package zhangxl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Given a collection of numbers, return all possible permutations.
 * For example,
	[1,2,3] have the following permutations:
	[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
 * @author maoge
 *
 */
public class SL_Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        boolean[] used = new boolean[nums.length] ;
        Helper(nums, used, res, new ArrayList<Integer>());
    	return res ;
    }
    public void Helper(int[] nums , boolean[] used , List<List<Integer>> res 
    		, ArrayList<Integer> item){
    	if(item.size() == nums.length){
    		res.add(new ArrayList<Integer>(item));
    		return ;
    	}
    	for(int i=0 ; i < nums.length ; i++){
    		if(!used[i]){
    			used[i] = true ;
    			item.add(nums[i]);
    			Helper(nums, used, res, item);
    			item.remove(item.size() - 1);
    			used[i] = false ;                  //保存现场
    		}   		
    	}
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		int[] nums = new int[]{1,2,3};
		results = new SL_Permutations().permute(nums);
		Iterator it = results.iterator();
		while(it.hasNext()){
			Iterator IntIt = ((ArrayList<Integer>)it.next()).iterator();
			while(IntIt.hasNext()){
				System.out.println(IntIt.next());
			}
		}
	}

}
