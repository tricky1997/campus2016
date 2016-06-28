
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InterruptedIOException;
import java.util.*;


/**
 * Created by cjq on 2016/6/20.
 * 注意：
 * 1.import写在同一行的情况
 * 2.不考虑.*与static导入
 * 3.导入次数相同的类按照字典顺序排列
 * 4.在处理字符串的时候，不能直接去掉前7个字符，防止import后有两个空格，因此先去掉前6个，然后调用trim()
 * 5.同一个类导入多次，考虑吗？
 */
public class CountMostImport {

    private static Map<String, Integer> naturalOrderMap = new TreeMap<String, Integer>();

    public void recordTotalImportedClass(File file) {
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {        //如果是目录则递归
                recordTotalImportedClass(files[i]);
            }
            else {
                if (files[i].getName().endsWith(".java")) {     //如果是java文件进行处理
                    countImportedClassFromFile(files[i]);
                }
            }
        }


    }

    public void countImportedClassFromFile(File file) {
        String importString = null;
        String[] importArray = null;
        int flag = 0;   //用于标识是否已经读取到import语句
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(true) {
                if (br.readLine() == null) {
                    break;      //如果文件中只有import语句，防止读到文件末尾时出现的空指针错误
                }
                importString = br.readLine().trim();
                // 当读取到某一行不是以import开头或是空白的，则读取完毕，退出
                if ((!importString.startsWith("import")) && (!importString.equals(""))) {
                    if (flag == 0) {
                        continue;
                    } else {
                        break;      //读取到import语句之后如果出现其他语句，则表示import区域已过，可以退出
                    }
                }
                flag = 1;
                // 将每行截取为若干个import语句
                importArray = importString.split(";");
                for(String temp : importArray) {
                    temp = temp.trim();
                    if ("".equals(temp)) {
                        break;
                    }
                    temp = temp.substring(6);
                    temp = temp.trim();
                    if (naturalOrderMap.containsKey(temp)) {  //如果包含该类，则对其数量加1
                        count = naturalOrderMap.get(temp);
                        count++;
                        naturalOrderMap.put(temp, count);
                    } else {
                        naturalOrderMap.put(temp, 1);
                    }
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String args[]) {
        CountMostImport cmt = new CountMostImport();
        File file = new File("D:\\program\\JAVA\\work");
        cmt.recordTotalImportedClass(file);

        //将字母顺序的排序再按照引入次数重新排序
        List<Map.Entry<String, Integer>> finalOrderList = new ArrayList<>(naturalOrderMap.entrySet());
        Collections.sort(finalOrderList,
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return (Integer)o2.getValue().compareTo((Integer)o1.getValue());
                    }
                });

        Iterator<Map.Entry<String, Integer>> it = finalOrderList.iterator();
        int i = 0;
        while(it.hasNext() && i < 10) {
            Map.Entry<String, Integer> temp = it.next();
            System.out.println((i+1) + "\t" + temp.getKey() + "导入" + temp.getValue() + "次");
            i++;
        }
    }
}
