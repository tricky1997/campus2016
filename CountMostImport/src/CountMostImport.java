
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created by ZhangMiaosen on 2016/6/19.
 */
/* 要求：根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。*/
public class CountMostImport {
    private final static String filePath = "D:\\JAVA\\homeWork\\CountMostImport";
    private final static int topClassNum = 10;//所要取出的个数
    private static Logger logger = Logger.getLogger(CountMostImport.class);
    public static void main(String[] args) {

        count(getAllClassName(findAllFile(filePath)));

    }

    //取得所有源文件
    public static ArrayList<File> findAllFile(String filePath) {
        ArrayList<File> list = new ArrayList<File>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                list = findAllFile(files[i].getAbsolutePath());
            else {
                String fileName = files[i].getName();
                if (isJava(fileName)) {
                    list.add(files[i]);
                }
            }
        }
        return list;
    }

    // 判断是否是源文件
    public static boolean isJava(String string) {
        boolean flag = false;
        if (string.endsWith(".java"))
            flag = true;
        return flag;
    }

    // 获取所有类名及其出现的次数 并将其放入hashmap中
    public static HashMap<String, Integer> getAllClassName(ArrayList<File> list) {
        BufferedReader bufferedReader = null;
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

        try {
            for (int i = 0; i < list.size(); i++) {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(list.get(i))));
                String line = null;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.trim().startsWith("import")) {
                            String className = getClassName(line);
                            if (hashMap.containsKey(className)) {
                                hashMap.put(className, hashMap.get(className) + 1);
                            } else {
                                hashMap.put(className, 1);
                            }
                        }
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }

        return hashMap;
    }


    //获取类名
    public static String getClassName(String s) {
        String className;
        s = s.replaceFirst("import", "      ");
        s = s.trim();
        s = s.substring(0, (s.length() - 1));
        className = s.trim();
        return className;
    }

    //排序，取出前10
    public static void count(HashMap<String, Integer> hashMap) {

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> map1, Map.Entry<String, Integer> map2) {
                return (map1.getValue()).compareTo(map2.getValue());
            }
        });

        // 对HashMap中的 value 进行排序后  显示排序结果

        if (list.size()<topClassNum){
            for (int i = list.size()-1; i >=0; i--) {
                logger.debug(list.get(i).getKey());
            }
        }
        else{
            for (int i = list.size()-1; i >list.size()-1-topClassNum; i--) {
                logger.debug(list.get(i).getKey());
            }
        }

    }
}
