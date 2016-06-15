package zhangxl ;

import java.util.*;

public class SLInorderTraversal{
	// 树的数据结构
	class TreeNode {
		int val ;
		TreeNode left ;
		TreeNode right ;
		TreeNode(int x){
			val = x ;
		}
	}

	// private List<Integer> res = new ArrayList<Integer>();
	// 中序遍历二叉树
	public List<Integer> inorderTraversal(TreeNode root){
		List<Integer> res = new ArrayList<Integer>();
		if(root == null) 
			return res ;
		if(root.left != null){
			res.addAll(inorderTraversal( root.left ));
			//res.add(root.left.val) ;
		}
		res.add(root.val);
		if(root.right != null){
			res.addAll(inorderTraversal(root.right));
			//res.add(root.right.val);
		}
		return res ;
	}
}