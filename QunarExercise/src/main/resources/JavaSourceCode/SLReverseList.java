package zhangxl;

import java.util.Stack;

import zhangxl.SL_removeNthFromEnd.ListNode;

public class SLReverseList {
	class ListNode {
		int val ;
		ListNode next ;
		ListNode(int x){
			val = x ;
		}
	}
	
	public ListNode reverseList(ListNode head){
		ListNode temp = new ListNode(0);
		Stack<ListNode> mStack = new Stack<SLReverseList.ListNode>();
		ListNode ptr = head ;
		while(ptr != null){
			mStack.push(ptr);
			ptr = ptr.next ;
		}
		temp.next = mStack.peek() ;
		ListNode ptrHead = mStack.peek() ;
		while(!mStack.empty()){
			ptr = mStack.peek();
			ptrHead.next = ptr ;
			ptrHead = ptrHead.next ;
			mStack.pop();
		}
		ptr.next = null ;
		return temp.next ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SLReverseList sl = new SLReverseList();
		ListNode list[] = new ListNode[5]; 
		//��ʼ������
		for(int i = 0 ; i < 5 ; i++){
			list[i] = sl.new ListNode(i+1);
		}
		ListNode head = list[0]; 
		//��������
		for(int i=0 ; i < 4 ; i++){
			list[i].next = list[i+1] ;
			//if(i==3) list[i+1].next = null ;
		}
		head = sl.reverseList(list[0]);
		printListNode(head);
	}
	
	public static void printListNode(ListNode list){
		while(list != null){
			System.out.println(list.val);
			list = list.next ;
		}
	}
}
