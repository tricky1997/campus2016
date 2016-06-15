package zhangxl;

public class SLDetectCycle {
	
	public ListNode detectCycle(ListNode head){
		ListNode start = head ;
//		当链表为空或者长度为1时返回null
		if(head == null || head.next == null)
			return null ;
		ListNode slow = head ;
		ListNode fast = head ;
		while(true){
//			遇到null，说明没有环
			if(fast == null || fast.next == null)
				return null ;
			slow = slow.next ;
			fast = fast.next.next ;
			if(fast == slow)
				break ;
		}
		slow = head ;
//		再一次相遇时，慢指针就是环的起点
		while(slow != fast){
			slow = slow.next ;
			fast = fast.next ;
		}
		return slow ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
