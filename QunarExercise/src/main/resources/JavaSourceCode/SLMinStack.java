package zhangxl ;

import java.util.Stack;

public class SLMinStack{
	public Stack<Integer> mStack = new Stack<Integer>();
	// minSta保存一个单调递减的序列，push(5),push(1),push(3)这个过程中，3不会被压栈，但是pop(1)之前，pop(3)已经完成了，也就是说
	// minSta中peek()永远是当前mStack中最小的元素
	public Stack<Integer> minSta = new Stack<Integer>(); 
	// 压栈
	public void push(int x){
		mStack.push(x);
		if(minSta.isEmpty() || x < minSta.peek()){
			minSta.push(x);
		}
	}

	// 出栈
	public void pop(){
		if(mStack.peek() == minSta.peek())
			minSta.pop();
		mStack.pop();
	}

	// 获取栈顶元素
	public int peek(){
		return mStack.peek();
	}
	// 得到栈的最小值
	public int getMin(){
		return minSta.peek();
	}
	//是否为空
	public boolean isEmpty(){
		return mStack.isEmpty();
	} 

	public static void main(String[] args){
		SLMinStack sl = new SLMinStack();
		sl.push(7);
		sl.push(5);
		sl.push(1);
		sl.push(3);
		sl.push(4);
		sl.push(0);
		while(!sl.isEmpty()){
			System.out.println(sl.getMin());
			sl.pop();
		}
	}
}