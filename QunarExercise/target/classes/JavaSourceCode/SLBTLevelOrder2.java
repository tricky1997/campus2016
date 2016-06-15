package zhangxl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import zhangxl.SLBinaryTreeZigzagLevel.TreeNode;
/**
 * 和层次遍历一样，只是在最后保存结果的时候，每次在list的位置0插入结果，这样最后输出的结果自然就逆序了
 * @author maoge
 *
 */
public class SLBTLevelOrder2 {
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
		if(root == null)
			return res ;
		queue.add(root);
		//boolean reverse = false ; //从右往左的标志
		while(!queue.isEmpty()){
			List<Integer> list = new ArrayList<Integer>();
			int nums = queue.size();
			for(int i = 0 ; i < nums ; i++){
				TreeNode node = queue.poll();
				list.add(node.val);	
				if(node.left != null){
					queue.add(node.left);
				}
				if(node.right != null){
					queue.add(node.right);
				}
			}
			res.add(0,new ArrayList<Integer>(list));
		}
		return res ;
    }	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
