package zhangxl ;

import java.util.Stack;

public class SLEvalRPN{
	// 通过逆波兰表达式计算结果
	public int evalRPN(String[] tokens){
		Stack<Integer> s = new Stack<Integer>();
		// 结果
		int results = 0 ;
		for(int i = 0 ; i < tokens.length ; i++){
			String s_num = tokens[i] ;
			//使用正则表达式判断字符串是否为数字,若为数字，则无条件压栈
			if(s_num.equals("+")){
				// 取出操作数
				int num1 = s.pop() ;
				int num2 = s.pop() ;
				results = num1 + num2 ;
				s.push(results);
			}
			else if(s_num.equals("-")){
				// 取出操作数
				int num1 = s.pop() ;
				int num2 = s.pop() ;
				results = num2 - num1 ;
				s.push(results);
			}
			else if(s_num.equals("*")){
				// 取出操作数
				int num1 = s.pop() ;
				int num2 = s.pop() ;
				results = num1 * num2 ;
				s.push(results);
			}
			else if(s_num.equals("/")){
				// 取出操作数
				int num1 = s.pop() ;
				int num2 = s.pop() ;
				results = num2 / num1 ;
				s.push(results);
			}
			else {
				s.push(Integer.valueOf(s_num));
			}
		}
		if(s.size() == 1)
			return s.pop() ;
		return results;
	}

	public static void main(String[] args){
		//String[] tokens = new String[]{"2","1","+","3","*"};
		//String[] tokens = new String[]{"4","13","5","/","+"};
		String[] tokens = new String[]{"0","3","/"};
		//Integer.valueOf("-1-");
		System.out.println(new SLEvalRPN().evalRPN(tokens));
	}
}