package zhangxl;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * @author maoge
 * 解决思路：左符号进栈，右符号判断是否匹配，匹配时则出栈，最后判断栈是否为空，为空则匹配
 */
public class SL_ValidParentheses {
	public boolean isValid(String s){
		//创建栈用来存符号
		Stack<Character> mStack = new Stack<Character>();
		for(int i = 0 ; i < s.length(); i++){
			//如果等于左括号
			Character c = s.charAt(i);
			if(c == '(' || c == '[' || c == '{'){
				//等于左括号，压栈
				mStack.push(c);
			}
			//等于右括号，且栈不为空
			else if(c == ')' && !mStack.isEmpty() && mStack.peek() == '(' ){
				//左右括号匹配，则出栈
				mStack.pop();
			}else if(c == ']' && !mStack.isEmpty() && mStack.peek() == '['){
				//左右括号匹配，则出栈
				mStack.pop();
			}else if(c == '}' && !mStack.isEmpty() && mStack.peek() == '{'){
				//左右括号匹配，则出栈
				mStack.pop();
			} else {
				return false ;
			}
		}
		return mStack.isEmpty() ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "]";
		System.out.println(new SL_ValidParentheses().isValid(s));
	}

}
