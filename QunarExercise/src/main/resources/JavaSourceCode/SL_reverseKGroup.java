package zhangxl;

import java.util.Stack;

import zhangxl.SL_SwapNodesInPairs.ListNode;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 * 
 * @author maoge
 *
 */
public class SL_reverseKGroup {
	
	public class ListNode {
		int val ;
		ListNode next ;
		ListNode(int x) {
			// TODO Auto-generated constructor stub
			val = x ;
		}
	}
	/**
	 * 解题思路：每次遍历k个节点并存入一个栈中，然后出栈的顺序就是翻转后的k个节点的顺序，将其形成一个链表，然后剩下的节点再递归调用此方法。
	 * 
	 * @param head
	 * @param k
	 * @return
	 */
	public ListNode reverseKGroup(ListNode head , int k){
		ListNode dummy = new ListNode(0);
		dummy.next = head ;
		ListNode pHead = dummy ;
		Stack<ListNode> s = new Stack<SL_reverseKGroup.ListNode>();
		for(int i=0 ; i < k ; i++){
			pHead = pHead.next ;
			//链表长度小于k
			if(pHead == null){
				s.clear();
				return dummy.next ;
			}
			//压栈
			else s.push(pHead);
			/*//长度刚好为k
			if(i == k - 1 && pHead.next == null){
				
			}*/
		}
		ListNode nextStart = pHead.next ;
		//翻转
		ListNode p = dummy ;
		dummy.next = s.peek();
		while(!s.isEmpty()){
			p.next = s.peek();
			p = s.peek();
			//出栈
			s.pop() ;			
		}
		//长度刚好为k
		if(pHead.next == null){
			return dummy.next ;
		}
		else p.next = reverseKGroup( nextStart,k);
		return dummy.next ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SL_reverseKGroup sl = new SL_reverseKGroup();
		ListNode list1[] = new ListNode[6]; 
		//初始化链表
		list1[0] = sl.new ListNode(1);
		list1[1] = sl.new ListNode(2);
		list1[2] = sl.new ListNode(3);
		list1[3] = sl.new ListNode(4);
		list1[4] = sl.new ListNode(5);
		list1[5] = sl.new ListNode(6);
		
		//构造链表
		for(int i=0 ; i < 6 ; i++){
			if( 5 == i)
				list1[5].next = null ;
			else list1[i].next = list1[i+1] ;
		}
		
		//交换
		int k = 3 ;
		ListNode result = sl.reverseKGroup(list1[0], k);
		printListNode(result);
	}
	public static void printListNode(ListNode list){
		while(list != null){
			System.out.println(list.val);
			list = list.next ;
		}
	}
}
