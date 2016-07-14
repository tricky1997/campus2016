/**
 * Created by zhaozhenyu on 16-7-14.
 *  功能：统计指定目录下被引用次数最多的前十个类
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CountMostImport {
    HashMap<String, Integer> records;//记录引用类的名字和次数
    public CountMostImport(String filePath){
        records = new HashMap<String, Integer>();
        File file=new File(filePath);
        getFile(file);
    }
    //递归分析目录中的java源文件
    public void getFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tempFile : files) {
                getFile(tempFile);
            }
        } else {
            processImportClass(file);
        }
    }
    //统计java源文件中引用的类
    public void processImportClass(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                //当遇到类时，停止读入
                if (line.startsWith("public") || line.startsWith("class"))
                    break;
                //不统计import static 和 带*的引用
                if (line.startsWith("import") && !line.startsWith("import static") && line.charAt(line.length() - 1) != '*') {
                    String className=line.substring(6).trim();
                    Integer count=records.get(className);
                    if(count==null)
                        records.put(className,1);
                    else
                        records.put(className,count+1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 排序
    public ArrayList<Map.Entry<String,Integer>> getTop10MostImportClass(){
        ArrayList<Map.Entry<String,Integer>> list= new ArrayList<Map.Entry<String,Integer>>(recordclass.entrySet()) ;

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() < o2.getValue())
                    return 1;
                if (o1.getValue() > o2.getValue())
                    return -1;
                return 0;
            }
        });
        return list;
    }

}