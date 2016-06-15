package zhangxl;

public class SLsortedArrayToBST {
	public TreeNode sortedArrayToBST(int[] nums){
        if(nums == null || nums.length == 0)
            return null ;
/*		int length = nums.length ;
		TreeNode root = new TreeNode(nums[length / 2]);
		if(length == 1){
			root.left = null ;
			root.right = null ;
		}
		else if(length > 1){
    		int[] leftnums = new int[length/2];
    		int[] rightnums = new int[length % 2 == 0 ?length/2 - 1 : length / 2] ;
    		System.arraycopy(nums, 0, leftnums, 0, leftnums.length);
    		System.arraycopy(nums, length/2+1, leftnums, 0, rightnums.length);
			root.left = sortedArrayToBST(leftnums);
			root.right = sortedArrayToBST(rightnums);
		}*/
		
		return ArrayToBST(nums, 0, nums.length - 1) ;   
	}
	public TreeNode ArrayToBST(int[] nums , int start , int end){
		if(start > end) 
			return null ;
		int mid = (start + end) / 2 ;
		TreeNode root = new TreeNode(nums[mid]);
		root.left = ArrayToBST(nums, start, mid - 1) ;
		root.right = ArrayToBST(nums, mid + 1, end);
		return root ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
