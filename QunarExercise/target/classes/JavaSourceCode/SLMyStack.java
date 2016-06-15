package zhangxl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class SLMyStack {
	public Queue<Integer> mQueue1 = new LinkedList<Integer>();
	public Queue<Integer> mQueue2 = new LinkedList<Integer>();
	
//	压栈
	public void push(int x){
		Queue<Integer> temp = mQueue1.isEmpty()?mQueue2:mQueue1 ;
		temp.offer(x);
	}
	
// 	出栈
	public void pop(){
		//做完任何一个操作后，总有一个队列始终为空
		Queue<Integer> qEmpty = mQueue1.isEmpty()?mQueue1:mQueue2;
		Queue<Integer> qNotEmpty = mQueue1.isEmpty()?mQueue2:mQueue1 ;
		while(qNotEmpty.size() > 1){
			int temp = qNotEmpty.peek();
			qNotEmpty.remove();
			qEmpty.offer(temp);
		}
		if(qNotEmpty.size() == 1){
			int top = qNotEmpty.peek();
			qNotEmpty.remove();
		}
	}
//	获取栈顶元素
	public int peek(){
		int top = 0 ;
		//做完任何一个操作后，总有一个队列始终为空
		Queue<Integer> qEmpty = mQueue1.isEmpty()?mQueue1:mQueue2;
		Queue<Integer> qNotEmpty = mQueue1.isEmpty()?mQueue2:mQueue1 ;
		while(qNotEmpty.size() >1){
			int temp = qNotEmpty.peek();
			qNotEmpty.remove();
			qEmpty.offer(temp);
		}
		if(qNotEmpty.size() == 1){
			top = qNotEmpty.peek();
			qNotEmpty.remove();
			qEmpty.offer(top);
		}		
		return top ;
	}
	
//	判断栈是否为
	public boolean empty(){
		return mQueue1.isEmpty() && mQueue2.isEmpty() ;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SLMyStack mStack = new SLMyStack();
		mStack.push(1);
		mStack.push(2);
		mStack.push(3);
		while(!mStack.empty()){
			System.out.println(mStack.peek());
			mStack.pop();
		}
	}

}
