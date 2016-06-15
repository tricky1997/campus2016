package zhangxl;
/**
 * 题目的大意是使用最少的步数到达最后的节点
 * @author maoge
 *
 */
public class SL_JumpGame2{
	/**
	* 采用了贪心算法，动态规划的思想，分成step最优和step-1最优。当超过step-1步最远的位置时，说明step-1步是不能到达终点
	* 我们就需要更新步数，将step+1.时间复杂度为O(n)
	*/
	public int jump(int[] nums){
		if(nums.length == 0)
			return 0 ;
		//保存步数
		int step = 0 ;
		//上一次最优
		int lastReach = 0 ;
		//本次最优
		int Reach = 0 ;
		
		for(int i = 0 ; i <= Reach && i < nums.length ; i++){
			if(i > lastReach){
				step++ ;
				lastReach = Reach ;
			}
			Reach = Math.max(Reach,nums[i] + i) ;
		}
		//不能到达终点
		if(Reach < nums.length-1)
			return 0 ;
		return step ;
	}
	
	public static void main(String[] args){
		int[] nums = new int[]{2,3,1,1,4};
		System.out.println(new SL_JumpGame2().jump(nums));
	}
}
