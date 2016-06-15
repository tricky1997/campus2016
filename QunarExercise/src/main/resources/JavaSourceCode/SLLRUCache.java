package zhangxl;

import java.util.HashMap;

public class SLLRUCache {

	/**
	 * 双向链表
	 * @author maoge
	 *
	 */
	class DoubleLinkedList{
		private int val ;
		private int key ;
		private DoubleLinkedList pre ;
		private DoubleLinkedList next ;
		
		public DoubleLinkedList(int key , int value){
			this.key = key ;
			this.val = value ;
		}
	}
	
	// hashMap来保存结果，用于查找，时间复杂度是O(1) 
	private HashMap<Integer, DoubleLinkedList> map = new HashMap<Integer, DoubleLinkedList>();
	// 头结点
	private DoubleLinkedList head ;
	// 尾节点
	private DoubleLinkedList end ;
	// cache的大小
	private int capacity ;
	// HashMap的长度
	private int len ;
	
	/**
	 * 构造函数，设置Cache的大小
	 * @param capacity
	 */
	public SLLRUCache(int capacity){
		this.capacity = capacity ;
		this.len = 0 ;
	}
	/**
	 * 取值
	 * @param key
	 * @return
	 */
	public int get(int key){
		if(map.containsKey(key)){
			DoubleLinkedList latest = map.get(key);
			removeNode(latest);
			setHead(latest);
			return latest.val ;
		}
		else return -1 ;
	}
	
	public void set(int key , int value){
		if(map.containsKey(key)){
			DoubleLinkedList oldNode = map.get(key);
			oldNode.val = value ;
			removeNode(oldNode);
			setHead(oldNode);
		}
		else {
			DoubleLinkedList newNode = new DoubleLinkedList(key, value);
			if (len < capacity) {
				setHead(newNode);
				map.put(key, newNode);
				len++ ;
			}
			else {
				// 在map中删除链表的尾节点
				map.remove(end.key);
				// 在链表中删除尾节点
				end = end.pre ;
				if(end != null)
					end.next = null ;
				setHead(newNode);
				map.put(key, newNode);
			}

		}
	}
	
	/**
	 * 删除链表的一个节点
	 * @param node
	 */
	public void removeNode(DoubleLinkedList node){
		DoubleLinkedList cur = node ;
		DoubleLinkedList pre = cur.pre ;
		DoubleLinkedList post = cur.next ;
		// 非头节点
		if(pre != null){
			pre.next = post ;
		}
		// 要删除的节点是头节点
		else head = post ;
		// 要删除的节点不 是尾节点
		if(post != null){
			post.pre = pre ;
		}
		// 要删除的节点是尾节点
		else end = pre ;
		
	}
	/**
	 * 插入头节点
	 * @param node
	 */
	public void setHead(DoubleLinkedList node){
		node.next = head ;
		node.pre = null ;
		if(head != null)
			head.pre = node ;
		head = node ;
		if(end == null){
			end = node ;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SLLRUCache sl = new SLLRUCache(2);
		sl.set(2, 1);
		sl.set(1, 1);
		int val1 = sl.get(2);
		sl.set(4, 1);
		int val2 = sl.get(1);
		int val3 = sl.get(2);
/*		DoubleLinkedList ptr = sl.head ;
		while(ptr!=null){
			System.out.println(ptr.val);
			ptr = ptr.next ;
		}*/
		System.out.println(val1+","+val2+","+val3);
	}

}
