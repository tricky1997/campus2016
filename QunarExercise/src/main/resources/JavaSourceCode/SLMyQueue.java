package zhangxl ;

import java.util.Stack;

public class SLMyQueue{
	Stack<Integer> stack1 = new Stack();
	Stack<Integer> stack2 = new Stack();
	//进队列
	public void push(int x){
		stack1.push(x) ;
	}

	//出队列
	public void pop(int x) throws Exception{
		if(stack2.size() <= 0){
			while(stack1.size() > 0){
				int temp = stack1.peek();
				stack1.pop();
				stack2.push(temp);
			}
		}
		if( stack2.size() == 0 )
			throw new Exception("the Queue is empty");
		int head = stack2.peek();
		stack2.pop();
	}

	//得到队首元素
	public int peek(int x) throws Exception{
		if(stack2.size() <= 0){
			while(stack1.size() > 0){
				int temp = stack1.peek();
				stack1.pop();
				stack2.push(temp);
			}
		}
		if( stack2.size() == 0 )
			throw new Exception("the Queue is empty");
		int head = stack2.peek();
		//stack2.pop();
		return head ;
	}

	//判断队列是否为空
	public boolean empty(){
		return stack1.empty() && stack2.empty();
	}

}
