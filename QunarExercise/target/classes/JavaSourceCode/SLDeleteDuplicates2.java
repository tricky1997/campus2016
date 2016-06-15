package zhangxl ;

public class SLDeleteDuplicates2{
	public static ListNode deleteDuplicates(ListNode head){
		if(head == null || head.next == null)
			return head ;
		ListNode newHead = new ListNode(0) ;
		newHead.next = head ;
		int curVal = head.val ;
		// 前驱指针，始终指向上一个不重复的元素
		ListNode pre = newHead ;
		// 当前节点指针
		ListNode cur = head ;
		while(cur!= null){
			// 找到下一个不重复的元素
			while(cur.next!=null && pre.next.val == cur.next.val){
				cur = cur.next ;
			}
			if(pre.next == cur)
				pre = pre.next ;
			else pre.next = cur.next ;

			cur = cur.next ;

		}
		return newHead.next ;
	}

	public static void main(String[] args){
		ListNode[] list = new ListNode[7];
		list[0] = new ListNode(1);
		list[1] = new ListNode(2);
		list[2] = new ListNode(3);
		list[3] = new ListNode(3);
		list[4] = new ListNode(4);
		list[5] = new ListNode(4);
		list[6] = new ListNode(5);

		ListNode head = list[0];
		for(int i = 0 ; i < 7 ; i++){
			if(i == 6)
				list[i].next = null ;
			else list[i].next = list[i+1] ;
		}  

		ListNode ptr = deleteDuplicates(head);
		while(ptr != null){
			System.out.println(ptr.val);
			ptr = ptr.next ;
		}
	}
}