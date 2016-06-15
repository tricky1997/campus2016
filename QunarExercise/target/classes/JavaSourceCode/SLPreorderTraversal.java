package zhangxl;

import java.util.*;
/**
 * 二叉树的前序遍历
 * @author maoge
 *
 */
 class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     
     TreeNode(int x) { 
    	 val = x; 
     	}
    }
 
public class SLPreorderTraversal {
	List<Integer> list = new ArrayList<Integer>();
	public List<Integer> preorderTraversal(TreeNode root){
		if(root == null)
			return list ;
		list.add(root.val);
		if(root.left != null)
			preorderTraversal(root.left);
		if(root.right != null)
			preorderTraversal(root.right);
		return list ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
