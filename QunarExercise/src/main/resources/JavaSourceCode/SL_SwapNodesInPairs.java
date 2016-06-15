package zhangxl;

import zhangxl.SL_MergeKSortedLists.ListNode;

/**
 * Given a linked list, swap every two adjacent nodes and return its head.
 * For example,
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 * @author maoge
 *
 */
public class SL_SwapNodesInPairs {
	/**
	 * 链表结构
	 * @author maoge
	 *
	 */
	public class ListNode {
		int val ;
		ListNode next ;
		ListNode(int x){
			this.val = x ;
		}
	}
	/**
	 * 
	 * @param head
	 * @return
	 */
	/*public ListNode swapPairs(ListNode head){
		ListNode pHead = head ;
		ListNode newHead  ;
		if(head == null) return head ;
		if(head.next == null) return head ;
		else newHead = head.next ;
		
		while(pHead.next != null && pHead.next.next != null){
			//交换
			ListNode temp = pHead.next ;
			ListNode nextStart = pHead.next.next ;
			
			pHead.next = pHead.next.next ;
			temp.next = pHead ;
			
			if(pHead.next.next != null) 
				pHead.next = pHead.next.next ;
			pHead = nextStart ;			
		}
		
		return  newHead;
	}*/
	
	public ListNode swapPairs(ListNode head){
		if(head == null || head.next == null){
			return head ;
		}
		ListNode point = new ListNode(0);
		point.next = head ;
		head = point ;
		while(point.next != null && point.next.next != null){
			ListNode temp = point.next.next.next ;
			point.next.next.next = point.next ;
			point.next = point.next.next ;
			point.next.next.next = temp ;
			point = point.next.next;
		}
		return head.next;
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SL_SwapNodesInPairs sl = new SL_SwapNodesInPairs();
		ListNode list1[] = new ListNode[5]; 
		//初始化链表
		list1[0] = sl.new ListNode(1);
		list1[1] = sl.new ListNode(2);
		list1[2] = sl.new ListNode(3);
		list1[3] = sl.new ListNode(4);
		list1[4] = sl.new ListNode(5);
		
		//构造链表
		for(int i=0 ; i < 5 ; i++){
			if( 4 == i)
				list1[4].next = null ;
			else list1[i].next = list1[i+1] ;
		}
		
		//交换
		ListNode result = sl.swapPairs(list1[0]);
		printListNode(result);
		
	}
	public static void printListNode(ListNode list){
		while(list != null){
			System.out.println(list.val);
			list = list.next ;
		}
	}
}
