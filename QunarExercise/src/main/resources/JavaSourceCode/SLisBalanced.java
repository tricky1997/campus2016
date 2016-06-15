package zhangxl;

public class SLisBalanced {
	class TreeNode {
		int val ;
		TreeNode left ;
		TreeNode right ;
		TreeNode(int x){
			val = x ;
		}
	}
	/**
	 * 递归求解二叉树的深度
	 * @param root
	 * @return
	 */
	public int findTreeDeep(TreeNode root){
		int deep = 0 ;
		if(root != null){
			int leftDeep = findTreeDeep(root.left);
			int rightDeep = findTreeDeep(root.right);
			deep = leftDeep >= rightDeep ? leftDeep+1:rightDeep+1 ;
		}
		return deep ;
	}
	/**
	 * 判断二叉树是否为平衡二叉树
	 * @param root
	 * @return
	 */
	public boolean isBalanced(TreeNode root) {
		if(root == null) 
			return true ;
		int leftDeep = findTreeDeep(root.left) ;
		int rightDeep = findTreeDeep(root.right);
		if(Math.abs(rightDeep-leftDeep) > 1)
			return false ;
		else return isBalanced(root.left) && isBalanced(root.right) ;
    }	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}