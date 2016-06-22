import java.util.*;

/**
 * Created by ju on 2016/6/20.
 */
public class ValueComparator implements Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String, Integer> mp1, Map.Entry<String, Integer> mp2)
        {
            return mp2.getValue() - mp1.getValue();
        }
}
