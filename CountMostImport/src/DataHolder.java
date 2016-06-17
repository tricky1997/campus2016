import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6 0006.
 * 此类通过内部维护的一个Items数组，通过使用小顶堆排序算法，
 * 统计目录下引入最多的前N个类，理论上时间复杂度为n*logN
 */
public class DataHolder {
    /**
     * 此内部类是为方便排序而引入
     */
    public class Items{
        //类的全限定名称
        private String classPath;
        //此类已经出现了的次数
        private int classCount;

        /**
         * 只有类名的有参构造函数，次数初始化为1
         * @param classPath
         * 类的全限定名称
         */
        public Items(String classPath){
            this.classPath=classPath;
            classCount=1;
        }

        /**
         * 拥有类名和统计次数的有参构造函数
         * @param classPath
         * 类的全限定名称
         * @param classCount
         * 类的初始化统计次数
         */
        public Items(String classPath,int classCount){
            this.classPath=classPath;
            this.classCount=classCount;
        }

        /**
         * 类的统计次数+1
         */
        public void increase(){
            classCount++;
        }

        /**
         * 返回类的打印格式
         * @return
         * 返回需要打印的字符串
         */
        public String toString(){
            return classPath+"\t"+classCount;
        }

        /**
         * getter方法
         * @return
         * 返回类的全限定名称
         */
        public String getClassPath(){
            return classPath;
        }

        /**
         * getter方法
         * @return
         * 返回当前类的计数
         */
        public int getClassCount(){
            return classCount;
        }

        /**
         * 比较当前对象是否比参数中对象大
         * @param item
         * 待比较对象
         * @return
         * 布尔类型，代表当前对象是否比参数对象大
         */
        public boolean isGreaterThan(Items item){
            if(this.classCount>item.getClassCount()){
                return true;
            }
            if(classCount==item.getClassCount()){
                if(this.classPath.compareTo(item.getClassPath())<0){
                    return true;
                }
            }
            return false;
        }

        /**
         * 比较当前对象是否比参数对象小
         * @param items
         * 待比较对象
         * @return
         * 布尔类型，代表是否比待比较对象小
         */
        public boolean isSmallerThan(Items items){
            if(this.classCount<items.getClassCount()){
                return true;
            }
            if(this.classCount==items.getClassCount()){
                if(classPath.compareTo(items.getClassPath())>0){
                    return true;
                }
            }
            return false;
        }

        /**
         * 对当前对象进行哈希存储。
         * @return
         * 返回classPath的哈希结果
         */
        public int hashCode(){
            return classPath.hashCode();
        }

        /**
         * 比较当前对象是否与参数对象相等
         * @param items
         * 待比较对象
         * @return
         * 布尔类型，代表当前对象是否与待比较对象相等
         */
        public boolean equals(Items items){
            return classPath.equals(items.getClassPath());
        }
    }
    //计数，代表当前统计的类一共有多少个
    private int counter=0;
    //取前多少个引用最多的类
    private int max;
    //存储当前引用最多的前N个类，小顶堆排序方法的操作对象
    private Items[] topN;
    //存储所有的类及其引用次数
    private HashMap<String,Items> importClassMap=new HashMap<String,Items>();
    //存储当前所有类是否在topN数组中，及在数组中的位置。-1代表没有在topN数组中
    private HashMap<String,Integer> importClassIndex=new HashMap<String,Integer>();

    /**
     * 带有需要统计个数的有参构造函数
     * @param topN
     * 需要统计前多少个最多引用的类
     */
    public DataHolder(int topN){
        this.topN=new Items[topN];
        for(int i=0;i<topN;++i){
            this.topN[i]=new Items("",0);
        }
        this.max=topN;
    }

    /**
     * 将类加入importClassMap中
     * @param classPath
     * 类的全限定名称
     */
    public void addClass(String classPath){
        //如果此类已经存在于importClassMap中，那么只需对其计数+1
        if(importClassMap.containsKey(classPath)){
            Items item=importClassMap.get(classPath);
            item.increase();

            addToTopN(item);
        }
        //否则新建Item存于importClassMap和importClassIndex中，初始化其index为-1，即未存于数组中
        else{
            Items items=new Items(classPath,1);
            importClassMap.put(classPath, items);
            importClassIndex.put(classPath,-1);
            addToTopN(items);
            counter++;
        }
    }

    /**
     * 看是否能将参数所代表的Items添加到topN数组中
     * @param item
     * 待添加的元素
     */
    private void addToTopN(Items item){
        //该元素是否存在于importClassIndex中
        if(importClassIndex.containsKey(item.getClassPath())){
            int index=importClassIndex.get(item.getClassPath());
            //如果存在与importClassIndex，并且其index为-1.那么将其域topN中的最小元素也就是topN[0]比较
            if(index==-1){
                //如果小于最小值，那么肯定不能加入topN中
                if(!item.isGreaterThan(topN[0])){
                    return;
                }
                //如果大于，则加入topN中
                else{
                    //对于要移除topN的元素，修改其在importClassIndex中的索引值为-1
                    importClassIndex.put(topN[0].getClassPath(),-1);
                    topN[0]=item;
                    //修改新加入类在classIndex中的索引值为0
                    importClassIndex.put(item.getClassPath(),0);
                    //从小顶堆顶部向下调整
                    adjust(0,max);
                }

            }
            //如果其index不为-1，那么它一定存在于topN中，这时将其计数+1，并且从当前位置进行小顶堆调整即可（因为它只有可能破坏其子节点的小顶堆规则）
            else{
                adjust(index,max);
            }
        }
    }

    /**
     * 对小顶堆进行调整并对importClassIndex中的index进行相应的调整
     * @param begin
     * 调整的起始位置
     * @param end
     * 调整的结束位置
     */
    private void adjust(int begin,int end){
        Items temp;
        //找到其子节点
        int child=begin*2+1;
        while(child<end){
            //找到其计数较小的子节点
            if(child+1<end&&topN[child].isGreaterThan(topN[child+1])){
                child++;
            }
            //如果当前节点比其子节点大，那么交换并修改索引值
            if(topN[begin].isGreaterThan(topN[child])){
                temp=topN[begin];
                topN[begin]=topN[child];
                importClassIndex.put(topN[begin].getClassPath(),begin);
                topN[child]=temp;
                importClassIndex.put(topN[child].getClassPath(),child);
                begin=child;
                child=begin*2+1;
            }
            else {
                break;
            }
        }
        /*System.out.println(importClassMap);
        System.out.println(importClassIndex);
        printTopN();*/
    }

    /**
     * 建立小顶堆
     */
    private void buildHeap(){
        for(int i=(max-1)/2;i>=0;--i){
            adjust(i,max);
        }
    }

    /**
     * 通过堆排序得到从小到大的排序的结果
     * @return
     * 计数值从小到大对Items的排序结果
     */
    public List<Items> getTop(){
        //buildHeap();
        List<Items> itemsList=new ArrayList<Items>();
        for(int i=0;i<max;++i){
            itemsList.add(topN[0]);
            topN[0]=topN[max-1-i];
            adjust(0,max-1-i);
        }
        return itemsList;
    }

    /**
     * getter方法
     * @return
     * 返回当前一共统计了多少个类
     */
    public int getCounter(){
        return counter;
    }

    /**
     * 调试过程中，打印数组中间状态所使用的方法
     */
    private void printTopN(){
        for(int i=0;i<max;++i){
            System.out.print(topN[i]+" ");
        }
        System.out.println("");
    }
}
