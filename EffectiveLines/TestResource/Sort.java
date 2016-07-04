/**内排序有可以分为以下几类：
　　(1)、插入排序：直接插入排序、二分法插入排序、希尔排序。
　　(2)、选择排序：简单选择排序、堆排序。
　　(3)、交换排序：冒泡排序、快速排序。
　　(4)、归并排序
　　(5)、基数排序
一、稳定性:
　   稳定：冒泡排序、插入排序、归并排序和基数排序
　　不稳定：选择排序、快速排序、希尔排序、堆排序
二、平均时间复杂度
　　O(n^2):直接插入排序，简单选择排序，冒泡排序。
　　在数据规模较小时（9W内），直接插入排序，简单选择排序差不多。当数据较大时，冒泡排序算法的时间代价最高。性能为O(n^2)的算法基本上是相邻元素进行比较，基本上都是稳定的。
　　O(nlogn):快速排序，归并排序，希尔排序，堆排序。
　　其中，快排是最好的， 其次是归并和希尔，堆排序在数据量很大时效果明显。
三、排序算法的选择
　　1.数据规模较小
  　　（1）待排序列基本序的情况下，可以选择直接插入排序；
  　　（2）对稳定性不作要求宜用简单选择排序，对稳定性有要求宜用插入或冒泡
　　2.数据规模不是很大
　　（1）完全可以用内存空间，序列杂乱无序，对稳定性没有要求，快速排序，此时要付出log（N）的额外空间。
　　（2）序列本身可能有序，对稳定性有要求，空间允许下，宜用归并排序
　　3.数据规模很大
   　　（1）对稳定性有求，则可考虑归并排序。
    　　（2）对稳定性没要求，宜用堆排序
　　4.序列初始基本有序（正序），宜用直接插入，冒泡
参考网址http://www.cnblogs.com/liuling/p/2013-7-24-01.html*/
public class Sort {
	/**直接插入排序，每步将一个带排序的记录，按其顺序码大小插入到前面已经排序的子序列的合适的位置(从后向前找到合适的位置)，
	 * 直到全部插入排序完为止,也就是从序号为1开始，选取待插入元素，然后将其前面的元素从后到前同此元素比较，若大于，则后移，不大于，则停止比较，将选择
	 * 的元素插入此地方，然后将i++，继续新一轮的比较
	 * 直接插入排序是稳定的排序
	 * 文件初态不同时，直接插入排序所耗费的时间有很大差异。若文件初态为正序，则每个待插入的记录只需要比较一次就能够找到合适的位置插入，故算法的时间复杂度为O(n)，这时最好的情况。若初态为反序，则第i个
	 * 待插入记录需要比较i+1次才能找到合适位置插入，故时间复杂度为O(n^2)，这时最坏的情况。 
	 * 直接插入排序的平均时间复杂度为O(n^2)
	 * */
	
	public static double[] DirectInsertSort(double[] data){
		
		for(int i = 1; i < data.length; i ++){
			//待插入元素
			double temp = data[i];
			int j ;
			for(j = i -1;j >= 0; j --){
				//将大于temp的往后移动一位
				if(data[j] > temp){
					data[j+1] = data[j];
				}else{
					break;
				}
			}
			data[j+1] = temp;
		}
		return data;
	}
	/**取一个元素为待插入元素，原始lft为0，high为数组的长度，mid为(left+right)/2,我们检查待插入的元素与中间值的大小比较，
	*如果小的话，则置left = mid+1，否则right=right -1;然后更改mid的值，
	*继续检查待插入的值与中间值的大小，直至right<left,查找结束，查找位置为left或high+1,则右边的数据要
	*向右平移一位
	*二分法插入排序也是稳定的。
    *二分插入排序的比较次数与待排序记录的初始状态无关，仅依赖于记录的个数。当n较大时，比直接插入排序的最大比较次数少得多。但大于直接插入排序的最小比较次数。
    *算法的移动次数与直接插入排序算法的相同，最坏的情况为n^2/2，最好的情况为n，平均移动次数为O(n^2)。*/

	public static double[] binaryInsertSort(double[] data){
		for(int i = 0; i < data.length;i++){
			double temp = data[i];
			int left = 0;
			int right = i-1;
			int mid =0;
			while(left<=right){
				mid = (left+right)/2;
				if(temp<data[mid]){
					right = mid-1;
				}else{
					left = mid+1;					
				}
			}
			for(int j = i-1;j >= left; j --){
				data[j+1] = data[j];
			}
		}
		return data;
	}
	/**冒泡排序基本思想：在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对响铃的两个熟依次进行比较和调整，
	 * 让较大的数往下沉，较小的数往上冒。每循环一次，较大的数就能到最后一个，所以每次循环的数就会少一个！
	 * 冒泡排序是一种稳定的排序方法。　
     * 若文件初状为正序，则一趟起泡就可完成排序，排序码的比较次数为n-1，且没有记录移动，时间复杂度是O(n)
     *若文件初态为逆序，则需要n-1趟起泡，每趟进行n-i次排序码的比较，且每次比较都移动三次，比较和移动次数均达到最大值∶O(n2)
     *起泡排序平均时间复杂度为O(n^2)*/
	public static double[] bubbleSort(double[] data){
		int length = data.length;
		for(int i = 0; i < length; i ++){
			for(int j = 0; j < length -1 - i ; j ++){
				if(data[j]>data[j+1]){
					double temp = data[j];
					data[j] =  data[j + 1];
					data[j+1] = temp;				
				}
			}
			for(int j = 0 ; j < data.length; j ++){
				System.out.print(data[i] + " ");
			}
		}
		return data;
	}
	/**快排序基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,
	*一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,
	*然后再用同样的方法递归地排序划分的两部分。
	*在一趟扫描中，当位在low的值小于基准元素时，low就加一，然后继续比较，若处在high的值大于基准元素时，
	*high的值就减小一，继续比较，直至将排序分成两部分，两部分也分别可以用快排来进行排序，所以整体是个递归的过程。
	*快速排序是不稳定的排序，快速排序的时间复杂度为O(nlogn),当n较大时使用快排比较好，当序列基本有序时用快排反而不好。
	*/
	public static double[] quickSort(double[] data){
		if(data.length>0){
			quick(data,0,data.length-1);
		}
		return data;
	}
	//将序列分成两部分，然后对每一部分进行快排
	private static void quick(double[] data,int low,int high){
		if(low<high){
			int middle = getMiddle(data,low,high);
			quick(data,0,middle-1);
			quick(data,middle+1,high);
			
		}
	}
	//设定基准值，然后进行一趟扫描，low值变大，high值减小，直至将基准值放在中间
	private static int getMiddle(double[] data,int low,int high){
		double temp = data[low];//基准元素
		while(low<high){//如果不加上这个判断，递归会无法退出导致堆栈溢出异常
			//找到比基准元素小的元素位置
			while(low<high&&data[high]>=temp){
				high--;
			}
			data[low] = data[high];
			while(low<high&&data[low]<=temp){
				low++;
			}
			data[high] = data[low];
		}
		data[low] = temp;
		return low;
	}
	/**归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，
	*每个子序列是有序的。然后再把有序子序列合并为整体有序序列
	*归并排序是稳定的排序方法。
　　    *归并排序的时间复杂度为O(nlogn)。
　　     *速度仅次于快速排序，为稳定排序算法，一般用于对总体无序，但是各子项相对有序的数列。*/
	public static double[] mergeSort(double[] data,int left,int right){
		if(left<right){
			int middle = (left+right)/2;
		//对左边进行递归
		mergeSort(data,left,middle);
		//对右边进行递归
		mergeSort(data,middle+1,right);
		//合并
		merge(data,left,middle,right);
		}
		return data;
	}
	private static void merge(double[] data,int left,int middle,int right){
		double[] tmpArr = new double[data.length];
		int mid = middle+1;//右边的起始位置
		int tmp = left;
		int third = left;
		while(left<=middle&&mid<=right){
			//从两个数组中选取较小的数放入中间数组
			if(data[left]<=data[mid]){
				tmpArr[third++]=data[left++];
			}else{
				tmpArr[third++]=data[mid++];
			}
		}
		//将剩余的部分放入中间数组
		while(left<=middle){
			tmpArr[third++]=data[left++];
		}
		while(mid<=right){
			tmpArr[third++]=data[mid++];
		}
		//将中间数组复制回原数组
		while(tmp<=right){
			data[tmp] = tmpArr[tmp++];
		}
	}
	
	public static void main(String[] args){
		double[] a ={49,38,65,97,76,13,27,49,78,34,12,64,1};
		for(int i = 0 ; i < a.length; i ++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
		double[] data = Sort.DirectInsertSort(a);
		System.out.println("排序之后：");
		for(int i = 0 ; i < data.length; i ++){
			System.out.print(data[i] + " ");
		}
	}
}