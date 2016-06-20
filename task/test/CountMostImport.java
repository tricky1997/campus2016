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

        getAllFiles(new File("test.txt"), importCount);

        for(String key : importCount.keySet()){
            System.out.println(key + ":" + importCount.get(key));
        }
    }

    private static void getAllFiles(File file, Map<String, Integer> importCount){

        if (file.isFile()) {
            //TODO 统计import数
            Scanner fileScanner;
            try {
                fileScanner = new Scanner(file);
                while(fileScanner.hasNextLine()){
                    String line = fileScanner.nextLine();

                    if(line.indexOf("import ") == -1)
                        continue;

                    String pakageName = line.trim().substring(line.indexOf("import")+"import".length());
                    System.out.println(pakageName);
                    System.out.println("hello");
                    if(importCount.containsKey(pakageName))
                        importCount.replace(pakageName, importCount.get(pakageName), importCount.get(pakageName)+1);
                    else
                        importCount.put(pakageName, 1);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
