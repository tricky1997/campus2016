package zhangxl;



public class Solution02 {
	
	public class ListNode {
		int val ;
		ListNode next ;
		ListNode(int x){
			val = x ;
		}
	}	
	public ListNode addTwoNumbers(ListNode l1,ListNode l2){
			ListNode results = new ListNode(0);
			ListNode start = results ;
			int temp = 0 ;
			ListNode t1 = l1 ;
			ListNode t2 = l2 ;
			int t1v , t2v;
			while(t1 != null || t2 != null){
				results.next = new ListNode(0);
				results = results.next;
				if(t1 == null){
					t1v = 0 ;
				}
				else {
					t1v = t1.val ;
					t1 = t1.next ;
				}
				if(t2 == null){
					t2v = 0 ;
				}
				else {
					t2v = t2.val ;
					t2 = t2.next ;
				}
				results.val = (t1v + t2v + temp)%10 ;
				temp = (t1v + t2v + temp) / 10 ;			
			}
			if(temp!=0){
				results.next = new ListNode(0);
				results.next.val = temp ;
			}
			return start.next;
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Solution02 sl02 = new Solution02();
		ListNode[] L_in1=new ListNode[3] ;
		ListNode[] L_in2=new ListNode[3] ;
		//对第一个输入的数据初始化
		L_in1[0] = sl02.new ListNode(2);
		L_in1[1] = sl02.new ListNode(4);
		L_in1[2] = sl02.new ListNode(3);
		//对第二个输入的数据初始化
		L_in2[0] = sl02.new ListNode(5);
		L_in2[1] = sl02.new ListNode(6);
		L_in2[2] = sl02.new ListNode(4);
		
		//初始化链表
		for(int i = 0 ; i < L_in1.length-1 ; i++){
			L_in1[i].next = L_in1[i+1];
		}
		for(int i = 0 ; i < L_in2.length-1 ; i++){
			L_in2[i].next = L_in2[i+1];
		}
		ListNode l1 = L_in1[0];
		ListNode l2 = L_in2[0];
		ListNode result = sl02.addTwoNumbers(l1, l2);
		ListNode p = result ;
		while(p != null){
			System.out.println(p.val);
			p = p.next ;
		}
		
	}

}
/*int carry = 0 ;

ListNode newHead = new ListNode(0);
ListNode p1 =l1,p2=l2,p3=newHead ;
while(p1 !=null || p2!=null){
	if(p1 != null){
		carry +=p1.val ;
		p1 = p1.next ;
	}
	if(p2 != null){
		carry +=p2.val ;
		p2 = p2.next ;
	}
	p3.next = new ListNode(carry%10);
	carry /=10;
}
if(carry == 1)
	p3.next = new ListNode(1);
return newHead.next;*/