package zhangxl;
/**
There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted
arrays. The overall run time complexity should be O (log ( m + n )).
 * @author maoge
 *
 */
public class Solution04 {

	public static double findMedianSortedArrays(int[] nums1, int[] nums2){
		int total = nums1.length + nums2.length ;
		//total为奇数
		if(total % 2 == 1){
			return find_kth(nums1,nums2,total/2 + 1);
		}
		//total为偶数
		else {
			return (find_kth(nums1,nums2,total/2 ) 
					+ find_kth(nums1, nums2, total/2+1))/2;
		}
	}
	private static double find_kth(int[] nums1, int[] nums2, int k) {
		// TODO Auto-generated method stub
		if(nums1.length > nums2.length) return find_kth(nums2, nums1, k);
		//当nums1或者nums2为空时，直接返回nums1[k-1]或者nums2[k-1]
		if(nums1.length == 0) return nums2[k-1];
		//当k=1时，返回min(nums1[0],nums2[0])
		if(k == 1) return Math.min(nums1[0], nums2[0]);
		//divide k into two part 
		int ia = Math.min(k / 2 , nums1.length),ib = k - ia ;
		//当nums1[ia-1] < nums2[ib-1
		if(nums1[ia-1] < nums2[ib-1]){
			int[] a = new int[nums1.length - ia];
			//int[] b = new int[k-ia];
			System.arraycopy(nums1, ia,a , 0, nums1.length - ia);
			return find_kth(a, nums2, k-ia);
		}
		else if(nums1[ia-1] > nums2[ib-1]){
			int[] b = new int[nums2.length - ib];
			System.arraycopy(nums2, ib, b, 0, nums2.length-ib);
			return find_kth(nums1, b, k-ib);
		}
		else return nums1[ia-1];
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums1 = new int[]{1,3,6,9,12,15,17,18,20,22,30};
		int[] nums2 = new int[]{2,4,6,8,10,13,14,16,21,32};
		double results = findMedianSortedArrays(nums1,nums2);
		System.out.println(results);
	}

}
