package zhangxl ; 

import zhangxl.SL_reverseKGroup.ListNode;

public class SLinsertionSortList{
	class ListNode {
		int val ;
		ListNode next ;
		ListNode(int x){
			val = x ;
		}
	}
	// 使用插入排序对链表进行排序
	public ListNode insertionSortList(ListNode head){
		ListNode newHead = new ListNode(Integer.MIN_VALUE);
		newHead.next = head ;
		ListNode cur = head.next ;
		head.next = null ;
		while(cur != null){
			// 前一个指针
			ListNode pre = newHead ;
			// 当前节点的指针
			ListNode pCur = pre.next ;
			// 下一个节点的指针
			ListNode pNext = pCur.next ;
			while(pCur != null){
				if( cur.val >= pCur.val && pNext != null){
					pre = pre.next ;
					pCur = pCur.next ;
					pNext = pNext.next ;
				}
				// 最后一个节点,将当前节点插入到最后面
				else if(cur.val >= pCur.val && pNext == null){
					pCur.next = cur ;
					ListNode temp = cur.next ;
					cur.next = null;
					cur = temp ;
					break ;
				}
				else if(cur.val >= pre.val && cur.val < pCur.val){
					pre.next = cur ;
					ListNode temp = cur.next ;
					cur.next = pCur ;
					cur = temp ;
					break;
				}
			}
		}
		return newHead.next ;		
	}
	
	public static void main(String[] args){
		SLinsertionSortList sl = new SLinsertionSortList() ;
		ListNode list1[] = new ListNode[6]; 
		//��ʼ������
		list1[0] = sl.new ListNode(1);
		list1[1] = sl.new ListNode(6);
		list1[2] = sl.new ListNode(5);
		list1[3] = sl.new ListNode(4);
		list1[4] = sl.new ListNode(5);
		list1[5] = sl.new ListNode(6);
		for(int i = 0 ; i < 6 ; i++){
			if(i == 5)
				list1[i].next = null ;
			else list1[i].next = list1[i+1];
		}
		ListNode head = sl.insertionSortList(list1[0]);
		while(head != null){
			System.out.println(head.val);
			head = head.next ;
		}
	}

}