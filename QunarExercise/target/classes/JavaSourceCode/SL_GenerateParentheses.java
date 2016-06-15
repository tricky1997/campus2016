package zhangxl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * For example, given n = 3, a solution set is:
 * "((()))", "(()())", "(())()", "()(())", "()()()"
 * 
 * 解题思路：针对一个长度为2n的合法排列，第1到2n个位置都满足如下规则：左括号的个数大于等于右括号的个数。
 * 假设在位置k还剩余left个左括号和right个右括号，如果left>0，则我们可以直接打印左括号而不违背规则。能否打印右括号，还需要
 * 验证left和right的值是否满足规则，如果left>=right，则不能打印右括号，否则可以打印，如果left和right均为0，则完成一个合法的排列组合
 * @author maoge
 *
 */

public class SL_GenerateParentheses {
	public List<String> generateParenthesis(int n){
		if(n<=0) return null ;
		List<String> results = new ArrayList<String>();
		String s = new String();
		generate(n, n, s, results);
		return results ;
	}
	/**
	 * 递归函数，
	 * @param leftNum
	 * @param rightNum
	 * @param s
	 * @param results
	 */
	public void generate(int leftNum , int rightNum , String s ,List<String> results){
		//左右剩余的括号均为0，则排列合法，将结果add到list中
		if(leftNum == 0 && rightNum == 0){
			results.add(s);
		}
		//左括号不为0，打印左括号s+'('
		if(leftNum > 0){
			//剩余的左括号-1
			generate(leftNum - 1, rightNum, s+'(', results);
		}
		//右括号不为0，且剩余的左括号小于右括号
		if(rightNum > 0 && leftNum < rightNum){
			generate(leftNum, rightNum-1, s+')', results);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> results = null ;
		int n = 3 ;
		results = new SL_GenerateParentheses().generateParenthesis(n);
		Iterator<String> it = results.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

}
