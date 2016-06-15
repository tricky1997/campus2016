package zhangxl;

import java.util.*;

public class SLBinaryTreeZigzagLevel {
	/**
	 * Definition for a binary tree node
	 * @author maoge
	 *
	 */
	public class TreeNode {
		int val ;
		TreeNode left ;
		TreeNode right ;
		TreeNode(int x){
			val = x ;
		}
	}
	/**
	 * 使用队列来保存树的节点
	 * @param root
	 * @return
	 */
	public List<List<Integer>> zigzagLevelOrder(TreeNode root){
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		LinkedList<TreeNode> queue = new LinkedList<SLBinaryTreeZigzagLevel.TreeNode>();
		if(root == null)
			return res ;
		queue.add(root);
		boolean reverse = false ; //从右往左的标志
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
			// 翻转顺序
			if(reverse){
				Collections.reverse(list);
				reverse = false ;
			}
			else {
				reverse = true ;
			}
			res.add(new ArrayList<Integer>(list));
		}
		return res ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
