package zhangxl ;

//class ListNode {
//	int val ;
//	ListNode next ;
//	ListNode (int x){
//		val = x ;
//	}
//}

public class SLisPalindrome{
	public boolean isPalindrome(ListNode head){
	if( head == null || head.next == null) 
		return true ;		
	// 找到中间节点
	ListNode mid = findMiddle(head) ;
	// 反转链表
	mid = reverseList(mid) ;
	while(head != null && mid != null){
		if(head.val != mid.val) return false ;
		head = head.next ;
		mid = mid.next ;
	}
	return true ;

	}


private ListNode findMiddle(ListNode head){
	// 维护一个快指针和一个慢指针，快指针每次移动两个节点，慢指针每次移动一个节点
	ListNode p = head ;
	while(p.next != null && p.next.next != null){
		p = p.next.next ;
		head = head.next ;
	}

	p = head.next ;
	head.next = null ;
	return p ;
}

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
}