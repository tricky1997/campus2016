package zhangxl;

import java.util.Stack;

/**
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
 * @author maoge
 *
 */
public class SL_LongestVaildParentheses {
	public int longestValidParentheses(String s){
		//s为空或者长度为0
		if(s == null || s.length() == 0){
			return 0 ;
		}
		int start = -1 ;
		int maxLength = 0 ;
		//栈，保存字符串中字符的index
		Stack<Integer> stack = new Stack<Integer>();
		for(int i = 0 ; i < s.length() ; i++){
			//遇到左括号，无条件压栈,压栈的是字符的下标
			if(s.charAt(i) == '('){
				stack.push(i);
			}
			//遇右括号
			else{
				//非空，
				if(!stack.isEmpty()){
					stack.pop();
					//括号匹配，计算最大的长度
					if(stack.isEmpty()){
						maxLength = Math.max(maxLength, i-start);
					}
					else {
						maxLength = Math.max(maxLength,i-(int)stack.peek());
					}
				}
				else {
					start = i ;
				}
			}
		}
		return maxLength;		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = ")())()())";
		System.out.println(new SL_LongestVaildParentheses().longestValidParentheses(s));
	}

}
