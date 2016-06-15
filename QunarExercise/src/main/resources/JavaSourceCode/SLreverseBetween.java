package zhangxl ; 

import java.awt.List;

public class SLreverseBetween{

	public ListNode reverseBetween(ListNode head , int m , int n){
		if(head == null) 
			return null ;
		if(head.next == null || m == n)
			return head ;
		ListNode newHead = new ListNode(0);
		newHead.next = head ;
		// 第m个节点,以及第m个节点的前一个节点
		ListNode mHead = head, mpre = newHead;
		// 第n个节点,以及第n个节点的下一个节点
		ListNode nptr = null , Nnext = null;
		ListNode ptr = head ;
		for (int i = 0; i < n; i++) {
			if(m >= 2 && i == m-2)
				mpre = ptr ;
			else if(i == m-1)
				mHead = ptr ;
			else if(i == n-1){
				nptr = ptr ;
				Nnext = nptr.next ;
			}

			ptr = ptr.next ;
		}
		nptr.next = null ;
		mpre.next = reverseList(mHead);
		mHead.next = Nnext ;
		return head ;
	}

	/**
	 * 反转链表
	 */
	public ListNode reverseList(ListNode head ){
		if(head == null || head.next == null) 
			return head ;
		// 维护前一个节点的指针,初始化为头指针
		ListNode pre = head ;
		// 维护一个当前节点的指针,初始化为头指针的下一个指针
		ListNode cur = head.next ;
		pre.next = null ;
		// 头指针反转之后变成尾指针，因此将其next赋值为null
		// 维护下一个节点的指针，初始化为null
		ListNode nxt = null ;
		while(cur != null){
			nxt = cur.next ;
			cur.next = pre ;
			pre = cur ;
			cur = nxt ;
		}
		return pre ;
	}
	public static void main(String[] args) {
		ListNode[] list = new ListNode[5];

	}
}