package com.qunar.exercise;

import java.io.*;
import java.util.*;

/**
 * Created by zhangxueling on 16/6/14.
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么
 */
public class CountMostImport {

    public static void main(String[] args) {
        String filePath = "src/main/resources/JavaSourceCode" ;
        CountMostImport cmi = new CountMostImport() ;
        List<File> res = cmi.getFileList(filePath) ;
        /*for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i).getAbsolutePath());
        }*/
        // 统计每个Import的类出现的次数
        Map<String , Integer> results =  cmi.CountImport(res);
        // 进行排序
        //List<String> list = cmi.SortImport(results);
        Map<String , Integer> map = cmi.SortImportByTreeMap(results) ;
        // 显示前十个最多的类
        cmi.DisplayMost(map);
    }

    /**
     * 通过递归得到制定目录下所有Java文件的集合
     * @param filePath 指定的目录
     * @return 返回的java文件List
     */
    public List<File> getFileList(String filePath ){
        List<File> fileList = new ArrayList<File>() ;
        File root = new File(filePath) ;
        File[] files = root.listFiles() ;
        if(files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    fileList.add(file);
                } else if (file.isDirectory()) {
                    fileList.addAll(getFileList(file.getPath()));
                }
            }
        }
        return fileList ;
    }


    /**
     * 统计Java文件中被Import的类
     * @param fileList
     * @return 返回保存结果的Map
     */
    public Map<String , Integer> CountImport(List<File> fileList) {
        // 保存被Import的类及其被Import的次数
        Map<String , Integer> results = new HashMap<String, Integer>() ;
        InputStream is = null ;
        BufferedReader br = null ;
        Iterator it = fileList.iterator() ;
        while(it.hasNext()){
            try {
                is = new FileInputStream((File) it.next()) ;
                br = new BufferedReader(new InputStreamReader(is)) ;
                String str ;
                while( (str = br.readLine()) != null ){
                    // 去掉所有空格
                    str = str.trim() ;
                    if(str.startsWith("import")){
                        // 去掉前面的import和最后的";"
                        str = str.substring(6 , str.length() - 1) ;
                        // 如果存在这个类,就将这个类Import的次数加1
                        if(results.containsKey(str)){
                            int count = results.get(str) ;
                            results.put(str,count + 1) ;
                        }
                        // 第一次出现这个类,就记录这个类,并将类Import的次数置为1
                        else {
                            results.put(str , 1) ;
                        }
                    }
                    // 如果读取到开始定义类的时候就结束循环
                    if(str.startsWith("class") || str.startsWith("public")){
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(is != null) {
                        is.close();
                    }
                    if(br != null){
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return results ;
    }

    /**
     * 对统计好的Import类的次数排序,第一种方法,利用TreeMap进行排序
     * @param results
     * @return
     */
    public Map<String , Integer> SortImportByTreeMap(Map<String , Integer> results){
        //List<String> res = null ;
        ByValueCompare bvc = new ByValueCompare(results) ;
        // 第一种方法,利用TreeMap进行排序
        TreeMap tm = new TreeMap<String , Integer>(bvc) ;
        tm.putAll(results);
        // 第二种方法,将Map.Entry放入List中,然后对List进行排序
        // res = new ArrayList<String>(results.keySet()) ;
        // Collections.sort(res , bvc);
        return tm ;
    }

    /**
     * 对统计好的Import类的次数排序,第二种方法,将Map.Entry放入List中,然后对List进行排序
     * @param results
     * @return
     */
    public List<String> SortImport(Map<String , Integer> results){
        List<String> res = null ;
        ByValueCompare bvc = new ByValueCompare(results) ;
        res = new ArrayList<String>(results.keySet()) ;
        Collections.sort(res , bvc);
        return res ;
    }

    /**
     * 显示前十个最多的类名
     * @param results
     */
    public void DisplayMost(Map<String , Integer> results){
        int index = 0 ;
        Iterator it = results.entrySet().iterator() ;
        System.out.println("前十个被import最多的类");
        while (it.hasNext() && index <= 9){
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            int value = (Integer) entry.getValue();
            System.out.println("("+ ( index + 1 ) + ")" + key + "  , " +  "被import次数" + ":" + value);
            index++ ;
        }
    }

    public void DisplayMost(List<String> results){
        System.out.println("前十个被import最多的类");
        for (int i = 0; i < 10 ; i++) {
            System.out.println("("+ (i + 1) + ")" +results.get(i));
        }
    }

    /**
     * 实现了Comparator接口的类
     */
    static class ByValueCompare implements Comparator<String> {

        Map<String , Integer> baseMap = null ;

        public ByValueCompare(Map<String , Integer> baseMap){
            this.baseMap = baseMap ;
        }

        @Override
        public int compare(String o1, String o2) {
            if (!baseMap.containsKey(o1) || !baseMap.containsKey(o2)) {
                return 0;
            }
            if (baseMap.get(o1) < baseMap.get(o2)) {
                return 1;
            } else if (baseMap.get(o1) == baseMap.get(o2)) {
                // 如果出现的次数相同,则类名按字典顺序排序
                return o1.compareTo(o2);
            } else {
                return -1;
            }
        }
    }
}
