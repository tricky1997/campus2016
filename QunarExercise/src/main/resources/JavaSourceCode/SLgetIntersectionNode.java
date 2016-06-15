package zhangxl;

public class SLgetIntersectionNode {

	public ListNode getIntersectionNode(ListNode headA , ListNode headB){
		ListNode Intersection = null;
		// A链表的长度
		int lengthA = 0 ;
		// B链表的长度
		int lengthB = 0 ;
		// 遍历两个链表得到其长度
		ListNode ptr = headA ;
		while(ptr != null){
			lengthA++ ;
			ptr = ptr.next ;
		}
		ptr = headB ;
		while(ptr != null){
			lengthB++ ;
			ptr = ptr.next ;
		}
//		int maxlength = (lengthA - lengthB)>0 ? lengthA : lengthB ;
//		int minlength = (lengthA - lengthB)>0 ? lengthB : lengthA ;
		ListNode ptrA = headA ;
		ListNode ptrB = headB ;
		
		if(lengthA > lengthB){
			int i = 0 ;
			//长的链表先走几步
			while(i < lengthA-lengthB){
				ptrA = ptrA.next ;
				i++ ;
			}
			//两个链表同时跑，遇到第一个相同则返回相同的节点
			while(ptrA != null && ptrB != null){
				if(ptrA == ptrB)
					return ptrA ;
				else {
					ptrA = ptrA.next ;
					ptrB = ptrB.next ;
				}
			}
		}
		else {
			int i = 0 ;
			//长的链表先走几步
			while(i < lengthB-lengthA){
				ptrA = ptrA.next ;
				i++ ;
			}
			//两个链表同时跑，遇到第一个相同则返回相同的节点
			while(ptrA != null && ptrB != null){
				if(ptrA == ptrB)
					return ptrA ;
				else {
					ptrA = ptrA.next ;
					ptrB = ptrB.next ;
				}
			}
		}
		
		return null ;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
