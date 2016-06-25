import java.util.Map;

/**
 * Created by Wang on 2016/6/23.
 */
public class MinHeap {
    private int capacity;
    private int array[];
    private int size;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        array = new int[capacity];
        size = 0;
    }

    public void add(int index, int number) {
        size++;
        array[index] = number;
        minHeapFixup(array, index);
    }

    private void minHeapFixup(int a[], int i) {
        int j, temp;
        temp = a[i];
        j = (i - 1)/2;
        while(j >=0 && i != 0) {
            if(a[j] <= temp)
                break;
            a[i] = a[j];
            i = j;
            j = (i-1) / 2;
        }
        a[i] = temp;
    }

    public void addAndAdjustTopN(int number) {
        if(array[0] > number)
            return;
        array[0] = number;
        minHeapFixdown(array, 0, capacity);
    }

    private void minHeapFixdown(int a[], int i, int n) {
        int j, temp;
        temp = a[i];
        j = 2*i + 1;
        while(j < n) {
            if(j + 1 < n && a[j+1] < a[j])
                j++;
            if(a[j] >= temp)
                break;

            a[i] = a[j];
            i = j;
            j = 2*i + 1;
        }
        a[i] = temp;
    }

    public void swap(int a[], int index1, int index2) {
        int temp = a[index1];
        a[index1] = a[index2];
        a[index2] = temp;
    }

    public boolean isNumberInHeap(int number) {
        int index =  lineSearch(array, number);
        if(index < 0)
            return false;
        return  true;
    }

    private static int lineSearch(int[] array, int number) {
        for(int i=0; i<array.length; i++){
            if(array[i] == number)
                return i;
        }
        return -1;
    }

    public int[] getArray() {
        int result[] = new int[array.length];
        for(int i=0; i<array.length; i++)
            result[i] = array[i];
        int realSize = capacity > size ? size : capacity;
        return  minHeapSortToDescend(result, realSize);
    }

    private int[] minHeapSortToDescend(int a[], int n) {
        for(int i=n-1; i >= 1; i--) {
            swap(a, i, 0);
            minHeapFixdown(a, 0, i);
        }
        return a;
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap(10);
        minHeap.add(0, 1);
        minHeap.add(1,2);

        int[] array = minHeap.getArray();
        for(int a : array) {
            System.out.print(a + ",");
        }

    }
}
