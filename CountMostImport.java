import java.io.*;
import java.util.*;
import java.util.Map.Entry;



public class CountMostImport {

    static HashMap<String,Integer> importCount;
    //输出import最多的前NUMBER个类
    public static final int NUMBER = 10;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入java项目的路径:");
        importCount = new HashMap<String,Integer>();
        countMostImport(scan.nextLine());
    }

    public static void countMostImport(String path) {
        File file = new File(path);

        if (!file.isDirectory())
        { //目录不存在 
            System.out.println("java项目路径不存在！");
            return;
        }
        //遍历各个文件并进行统计
        filesList(file, importCount);

        //对统计结果进行排序并打印
        printImportClass(importCount);

    }
    public static void printImportClass(HashMap<String, Integer> importCount) {
        //对对HashMap按值进行排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(importCount.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String,Integer> o2) {
                if(o2.getValue().compareTo(o1.getValue())==0){
                    //如果值相等，则按照字典序列进行排序
                    String str1 = o1.getKey().toLowerCase();//转换成小写是因为在ASICC中大写字符一定在小写字母前面，与字典序列不符
                    String str2 = o2.getKey().toLowerCase();
                    return str1.compareTo(str2);
                }
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });

        //遍历输出被import最多前NUMBER个的类
        int count = 0;
        for (Entry<String, Integer> mapping : list) {
            if( count<NUMBER ){
                System.out.println(mapping.getKey() + "******被import的次数为******" + mapping.getValue());
                count++;
            }
            else  return;
        }
    }
    public static void filesList(File file, HashMap<String, Integer> importCount) {
        File[] files = file.listFiles();
        for(File fileName : files) {
            //如果是文件夹，遍历每个文件和文件夹
            if(fileName.isDirectory()) {
                filesList(fileName, importCount);
            }
            else {
                String suffix = fileName.getName().substring(fileName.getName().lastIndexOf(".") + 1);
                //判断是否是java文件
                if(suffix.equals("java")) {
                    BufferedReader bufferedReader = null;
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                        String sentence = null;
                        while((sentence = bufferedReader.readLine()) != null){
                            String trimSentence = sentence.trim();
                            //判断是否为引用包语句
                            if(trimSentence.indexOf("import ") == -1 || !trimSentence.trim().startsWith("import "))
                                continue;

                            String importClassName = trimSentence.substring(  trimSentence.indexOf("import ")+"import ".length()  ).trim();
                            if(importCount.containsKey(importClassName))
                                importCount.put(importClassName, importCount.get(importClassName)+1);
                            else
                                importCount.put(importClassName, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}