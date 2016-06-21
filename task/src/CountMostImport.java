import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 93493 on 2016/6/18.
 */
public class CountMostImport {
    public static void main(String[] args){

        //保存引用的包，以及引用次数
        Map<String, Integer> importCount = new HashMap<>();

        //检测参数
        if(args == null || args.length == 0 || args.length > 1) {
            System.out.println("请输入一个文件路径作为参数");
            return;
        }
        //获取各个文件下import包
        getAllFiles(new File(args[0]), importCount);

        if(importCount.size() == 0){
            System.out.println("所给路径有错误或没有包被引用");
        }
        //保存引用最多的10个包
        String result[] = new String[10];
        for(int i = 0; i < 10 && importCount.size() > 0; i++) {
            Integer max = 0;
            String maxKey = "";
            for (String key : importCount.keySet()) {
                if(importCount.get(key) > max){
                    max = importCount.get(key);
                    maxKey = key;
                }
            }
            result[i] = maxKey + " " + max;
            importCount.remove(maxKey);
            System.out.println(result[i]);
        }
    }
    /*
    *   遍历所给路径下的每个文件，找到引用包
    */
    private static void getAllFiles(File file, Map<String, Integer> importCount){

        //如果是文件，直接统计import数
        if (file.isFile()) {
            Scanner fileScanner = null;
            try {
                fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    String line = fileScanner.nextLine();
                    //判断是否为引用包语句
                    if(line.indexOf("import ") == -1 || !line.trim().startsWith("import "))
                        continue;

                    String pakageName = line.substring(line.indexOf("import ")+"import ".length()).trim();
                    if(importCount.containsKey(pakageName))
                        importCount.put(pakageName, importCount.get(pakageName)+1);
                    else
                        importCount.put(pakageName, 1);
                }
            } catch (FileNotFoundException e) {
                System.out.println("打开文件错误");
            }finally {
                if(fileScanner != null)
                    fileScanner.close();
            }
        }
        //如果是文件夹，遍历每个文件和文件夹
        else if (file.isDirectory()) {
            File files[] = file.listFiles();
            if(files == null)
                return;
            for(File dir : files){
                getAllFiles(dir, importCount);
            }
        }
    }
}
