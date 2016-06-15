package zhangxl;

public class SLhasPathSum {
	public boolean hasPathSum(TreeNode root, int sum){
		if(root == null)
			return false ;
		if(root.left != null || root.right != null){
			return hasPathSum(root.left, sum - root.val) ||
					hasPathSum(root.right, sum - root.val) ;
		}
		else {
			if(root.val == sum)
				return true ;
			else return false ;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
