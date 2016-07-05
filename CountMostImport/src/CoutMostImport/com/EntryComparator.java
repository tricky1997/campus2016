package CoutMostImport.com;
import java.util.Comparator;
import java.util.Map;


 //比较Java类被导入的次数
 

public class EntryComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
      //如果两个类的导入次数相同，则按照字典序进行比较
        if(o1.getValue()==o2.getValue())
                 {
                     return o1.getKey().compareTo(o2.getKey());
                 }
                 else
		//否则输出两个导入次数的差值
                     return o2.getValue()-o1.getValue();
    }
}
