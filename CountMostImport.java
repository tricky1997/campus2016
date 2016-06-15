/*
*三、根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。（作业命名为：CountMostImport）
 * Created by MaNa on 2016/6/13.
 */
import java.io.*;
import java.util.*;

public class CountMostImport {
    String dirName;
    HashMap<String, Integer> importClassRecords;

    public static void main(String[] args) throws FileNotFoundException {
        String dirName="F:/resourcefiles";
        CountMostImport cmi=new CountMostImport(dirName);
        cmi.getTopTenClasses();

}

/**
 *
 * 调用函数完成初始化工作
 */
    public CountMostImport(String dir) throws FileNotFoundException {
        this.dirName = dir;
        importClassRecords = new HashMap<String, Integer>();
        this.contentsFiles(new File(this.dirName));
    }
    /**
     * 递归统计目录下的所有文件，调用函数统计每个文件import的类
     */
    public void contentsFiles(File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tmpFile : files) {
                contentsFiles(tmpFile);
            }
        } else {
            countImportClasses(file);
        }
    }
    /*
    * 统计文件File中被引用的类及其引用次数
    *
    */
    public void countImportClasses(File file) throws FileNotFoundException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = null;
        try {
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("import")) {
                    String className = line.substring(6, line.length()-1).trim();
                    Integer value = importClassRecords.get(className);
                    if(value==null){
                        importClassRecords.put(className,1);
                    }else
                    importClassRecords.put(className, value+1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 获得目录下所有文件的import次数最多的十个类及其被引用次数
    */
    public void getTopTenClasses(){
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>();
        list.addAll(importClassRecords.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String,Integer> obj2) {
                if(obj1.getValue()!=null&&obj2.getValue()!=null&&obj1.getValue()<obj2.getValue())
                    return 1;
                else
                    return -1;
            }
        });
        System.out.println("-------被import最多的前十个类及其被引用次数如下-----");
        for(int i=0;i<10;i++){
            System.out.println(list.get(i));
        }
    }

}