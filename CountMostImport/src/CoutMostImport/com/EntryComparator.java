package CoutMostImport.com;
import java.util.Comparator;
import java.util.Map;


 //�Ƚ�Java�౻����Ĵ���
 

public class EntryComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
      //���������ĵ��������ͬ�������ֵ�����бȽ�
        if(o1.getValue()==o2.getValue())
                 {
                     return o1.getKey().compareTo(o2.getKey());
                 }
                 else
		//�������������������Ĳ�ֵ
                     return o2.getValue()-o1.getValue();
    }
}
