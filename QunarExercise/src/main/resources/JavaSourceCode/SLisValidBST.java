package zhangxl;

import java.util.ArrayList;
import java.util.List;


/**
 * 判断一棵树是否为二叉平衡树
 * @author maoge
 *
 */
public class SLisValidBST {

/*		public boolean isValidBST(TreeNode root){
		if(root == null) 
			return true ;
		if(root.val == Integer.MAX_VALUE || root.val == Integer.MIN_VALUE)
			return true ;
		return isBST(root, Integer.MAX_VALUE, Integer.MIN_VALUE);
	if(root.left != null && root.right != null){
			if(root.left.val <= root.val && root.right.val >= root.val)
				return isValidBST(root.left) && isValidBST(root.right);
			else return false ;
		}
		if(root.left == null && root.right != null){
			if(root.right.val >= root.val)
				return true ;
			else return false ;
		}
		if(root.left != null && root.right == null){
			if(root.left.val <= root.val)
				return true ;
			else return false ;
		}
		else 
			return true ;
		
			
	}*/
/*	private boolean isBST(TreeNode root , int max , int min){
		if( root == null )
			return true ;
		if(root.val > min && root.val < max){
			return isBST(root.left, root.val, min) && isBST(root.right, max, root.val);
		}
		else return false ;
	}*/
	/**
	 * 中序遍历二叉搜索树，出来的结果是一个有序的数组
	 * @param root
	 * @return
	 */
	public List<Integer> inorder(TreeNode root){
		List<Integer> res = new ArrayList<Integer>();
		if(root != null){
			res.addAll(inorder(root.left));
			res.add(root.val);
			res.addAll(inorder(root.right));
			
		}
		return res ;
	}
	public boolean isValidBST(TreeNode root){
		if(root == null) return true ;
		//中序遍历二叉树
		List<Integer> res = inorder(root);
		for(int i = 0 ; i < res.size() - 1 ; i++){
			if(res.get(i+1) <= res.get(i))
				return false  ;
		}
		return true;
		
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
