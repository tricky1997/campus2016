package zhangxl;

import zhangxl.SL_removeNthFromEnd.ListNode;

/**
 * Merge two sorted linked lists and return it as a new list. 
 * The new list should be made by splicing together the nodes of the first two lists.
 * 分别遍历两个链表，将其中一个链表按大小顺序插入到另一个链表中
 * @author maoge
 *
 */
public class SL_MergeTwoLists {
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
	/*
	public ListNode mergeTwoLists(ListNode l1 , ListNode l2){
		//l1的头指针
		ListNode ptrL1 = l1 ;
		//l2的头指针
		ListNode ptrL2 = l2 ;
		while(ptrL1 != null && ptrL2!=null){
			//ptrL1到了最后一个节点
			if(ptrL2.val >= ptrL1.val && ptrL1.next == null ){
				ListNode temp = ptrL1.next ;
				ptrL1.next = ptrL2 ;
				ptrL1 = temp ;
			}
			else if(ptrL2.val < ptrL1.val && ptrL1.next == null){
				//ptrL2往后移
				ptrL2 = ptrL2.next ;
			}
			//如果L2的节点在L1的两个节点之间，则将L2插入L1
			else if(ptrL2.val > ptrL1.val && ptrL2.val <= ptrL1.next.val){
				ListNode temp = ptrL1.next ;
				//保存下一个节点
				ListNode temp2 = ptrL2.next ;
				//插入
				ptrL1.next = ptrL2 ;
				ptrL2.next = temp ;
				//将ptrL2往后移
				ptrL2 = temp2 ;
			}
			else if(ptrL2.val <= ptrL1.val){
				//保存下一个节点
				ListNode temp2 = ptrL2.next ;
				ptrL2.next = ptrL1 ;
				//将ptrL2往后移
				ptrL2 = temp2 ;
			}
			else {
				//ptrL1往后移
				ptrL1.next = ptrL1.next.next ;
			}
		}
		
		return l1 ;
	}*/
	/**
	 * 总的思路是新建一个链表dummy，分别遍历两个链表并比较它们的大小，将小的节点链接到dummy下，直到其中一个链表为空
	 * 最后将lastNode的next指向不为空的那个链表
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode mergeTwoLists(ListNode l1, ListNode l2){
		//虚拟头指针
		ListNode dummy = new ListNode(0);
		//保存头指针信息
		ListNode lastNode = dummy ;
		if(l1 == null) return l2 ;
		if(l2 == null) return l1 ;
		while(l1 != null && l2 != null){
			if(l1.val <l2.val){
				lastNode.next = l1 ;
				l1 = l1.next ;
			}
			else {
				lastNode.next = l2 ;
				l2 = l2.next ;
			}
			lastNode = lastNode.next ;
		}
		if(l1 != null){
			lastNode.next = l1 ;
		}else {
			lastNode.next = l2 ;
		}
		return dummy.next ;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SL_MergeTwoLists sl = new SL_MergeTwoLists(); 
		ListNode list1[] = new ListNode[5]; 
		//初始化链表
		list1[0] = sl.new ListNode(1);
		list1[1] = sl.new ListNode(3);
		list1[2] = sl.new ListNode(5);
		list1[3] = sl.new ListNode(7);
		list1[4] = sl.new ListNode(9);
		
		//构造链表
		for(int i=0 ; i < 5 ; i++){
			if( 4 == i)
				list1[4].next = null ;
			else list1[i].next = list1[i+1] ;
		}
		
		ListNode list2[] = new ListNode[5]; 
		//初始化链表
		list2[0] = sl.new ListNode(2);
		list2[1] = sl.new ListNode(4);
		list2[2] = sl.new ListNode(5);
		list2[3] = sl.new ListNode(8);
		list2[4] = sl.new ListNode(10);
		
		//构造链表
		for(int i=0 ; i < 5 ; i++){
			if( 4 == i)
				list2[4].next = null ;
			else list2[i].next = list2[i+1] ;
		}
		
		//合并两个链表
		
		ListNode mMerge = sl.mergeTwoLists(list1[0], list2[0]);
		
		//输出链表
		
		printListNode(mMerge);
	}
	
	public static void printListNode(ListNode list){
		while(list != null){
			System.out.println(list.val);
			list = list.next ;
		}
	}

}
