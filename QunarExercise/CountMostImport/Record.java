import java.util.Comparator;

/**
 * Created by xuxingbo on 2016/6/20.
 */
public class Record implements Comparable{
    private int count;
    private String className;

    public Record() {
    }

    public Record(int count, String className) {
        this.count = count;
        this.className = className;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    @Override
    /**
     *record的比较方法是：
     * 1.先比较count，按count从大到小排列
     * 2.如果count相同，则按照className的字典升序排序
     */
    public int compareTo(Object obj) {
        Record record = (Record) obj;
        //count值不相等，则把count大的值排在前面，降序排列
        if(this.count != record.count){
            return record.count - this.count;
        }
        //count值相等，则直接按字符串的升序排列
        return this.className.compareTo(record.className);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Record record = (Record) obj;

        if (count != record.count)
            return false;
        return className != null ? className.equals(record.className) : record.className == null;
    }

    @Override
    public String toString() {
        return "Record{" +
                "count=" + count +
                ", className='" + className + '\'' +
                '}';
    }
}
