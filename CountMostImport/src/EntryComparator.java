import java.util.Comparator;
import java.util.Map;

/**
 * 比较Java类被导入的次数
 *
 * @author  Andrew QIU
 * @date    2016-6-14
 */
public class EntryComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

        return o2.getValue() == o1.getValue()
                // 如果导入次数相等，则按照类名进行比较（字典升序）
                ? o1.getKey().compareTo(o2.getKey())
                // 否则按照导入次数降序排列
                : o2.getValue() - o1.getValue();
    }
}
