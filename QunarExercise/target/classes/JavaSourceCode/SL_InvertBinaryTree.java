package zhangxl;

public class SL_InvertBinaryTree {
	/**
	 * 二叉树的数据结构
	 * @author maoge
	 *
	 */
	  public class TreeNode {
		     public int val;
		     public TreeNode left, right;
		     public TreeNode(int val) {
		         this.val = val;
		         this.left = this.right = null;
		     }
		}
	 public TreeNode invertTree(TreeNode root){
		 if(root == null){
			 return null;
		 }
		 TreeNode temp = null;
		 //交换左右子树的节点
		 temp = root.left ;
		 root.left = root.right ;
		 root.right = temp ;
		 
		 //递归调用，将左右子树分别翻转
		 invertTree(root.left);
		 invertTree(root.right);
		 return root ;
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
