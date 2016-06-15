package zhangxl;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 已知一棵树的前序遍历序列和中序遍历序列，求树的构造
 * @author maoge
 *
 */
public class SLBuildTree {
	public TreeNode buildTree(int[] preorder , int[] inorder){
		if(preorder.length == 0 && inorder.length == 0)
			return null ;
		//用hashMap来储存中序遍历的序列的index
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0 ; i < inorder.length ; i++){
			map.put(inorder[i], i);
		}
		return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map) ;
	}
	
	public TreeNode helper(int[] preorder , int preL  , int preR , 
				int[] inorder , int inL , int inR , HashMap<Integer, Integer> map){
		if(preL > preR || inL > inR)
			return null ;
		//找到根节点
		TreeNode root = new TreeNode(preorder[preL]);
		int index = map.get(preorder[preL]);
		root.left = helper(preorder, preL+1, index - inL + preL, inorder, inL, index - 1, map);
		root.right = helper(preorder, preL+index-inL+1, preR, inorder, index+1, inR, map);
		return root ;		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
