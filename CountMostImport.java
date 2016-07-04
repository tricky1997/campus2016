import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class CountMostImport {
    public static void main(String[] args) {
        CountMostImport cm = new CountMostImport();
        Scanner scan = new Scanner(System.in);
        System.out.println("input java project path:");
        File file = new File(scan.nextLine());
        //HashMap 进行统计
        HashMap<String,Integer> mapImportNum =new HashMap<String, Integer>();
        if (file.isDirectory()) {
            //遍历各个文件并进行统计
            cm.filesList(file, mapImportNum);
            //打印结果
            cm.printResult(mapImportNum);
        }
    }
    public void filesList(File file, HashMap<String, Integer> mapImportNum) {
        File[] files = file.listFiles();
        for(File fileName : files) {
            //递归遍历
            if(fileName.isDirectory()) {
                filesList(fileName, mapImportNum);
            }
            else {
                String type = fileName.getName().substring(fileName.getName().lastIndexOf(".") + 1);
                //判断是否是java文件
                if(type.equals("java")) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                        String importStr = null;
                        while((importStr = br.readLine()) != null){
                            String importTrim = importStr.trim();
                            if(importTrim.indexOf("import ") == -1 || !importTrim.trim().startsWith("import ")) {
                                continue;
                            }
                            //提取类
                            String importClassName = importTrim.substring(  importTrim.indexOf("import ")+"import ".length()  ).trim();
                            if(mapImportNum.containsKey(importClassName))
                                mapImportNum.put(importClassName, mapImportNum.get(importClassName)+1);
                            else
                                mapImportNum.put(importClassName, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(br != null) {
                               br.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
    public void printResult(HashMap<String, Integer> mapImportNum) {
        //key-value 按照value排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(mapImportNum.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String,Integer> o2) {
                if(o2.getValue().compareTo(o1.getValue())==0){
                    //引用次数相等，按照字典序排序
                    String str1 = o1.getKey().toLowerCase();
                    String str2 = o2.getKey().toLowerCase();
                    return str1.compareTo(str2);
                }
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });
        //遍历输出被import最多前10个的类
        int count = 0;
        for (Entry<String, Integer> map : list) {
            if( count < 10 ){
                System.out.println(map.getKey() + "引用次数：" + map.getValue());
                count++;
            } else {
                break;
            }
        }
    }
}