package zhangxl;

import zhangxl.SLisBalanced.TreeNode;

public class SLisSymmetric {
	/**
	 * 树的数据结构
	 * @author maoge
	 *
	 */
	class TreeNode {
		int val ;
		TreeNode left ;
		TreeNode right ;
		TreeNode(int x){
			val = x ;
		}
	}
	/**
	 * 
	 * @param root
	 * @return
	 */
	public boolean isSymmetric(TreeNode root){
		if(root == null)
			return true ;
		if(root.left == null && root.right == null ) return true ;
		return isMiror(root.left, root.right);
	}
	
	public boolean isMiror(TreeNode n1 , TreeNode n2){
        if (n1 == null && n2 == null) return true;  
        if (n1 == null && n2 != null) return false;  
        if (n1 != null && n2 == null) return false;  
        if (n1.val != n2.val) return false;  
        return isMiror(n1.left, n2.right) && isMiror(n1.right, n2.left);  		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
