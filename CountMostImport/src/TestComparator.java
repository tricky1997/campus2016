import java.util.*;

/**
 * Created by andrew on 16-6-15.
 */
public class TestComparator {
    private static Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        // 添加元素
        map.put("org.apache.log4j.spi.ErrorHandler", 10);
        map.put("org.apache.log4j.spi.Filter", 4);
        map.put("org.apache.log4j.spi.LoggingEvent", 7);

        // 获取Map的键值对List用于排序！！
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        // 规则排序对象
        EntryComparator comparator = new EntryComparator();
        // 排序操作
        Collections.sort(list, comparator);

        // 输出结果
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
