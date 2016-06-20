import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by 93493 on 2016/6/15.
 */
public class EffectiveLines {
   public static void main(String[] args){
       //参数检测
       if(args.length != 1) {
           System.out.println("输入Java文件路径作为参数");
           return;
       }
       if(!args[0].endsWith(".java")) {
           System.out.println("输入文件不是Java文件");
           return;
       }
       if(!(new File(args[0]).exists())){
           System.out.println("输入文件不存在");
           return;
       }
       //用来保存有效行数
       int effectiveLines = 0;
       //统计有效行
       Scanner fileScanner = null;
       try {
           fileScanner = new Scanner(Paths.get(args[0]));

           while(fileScanner.hasNextLine()) {
               String line = fileScanner.nextLine();
               if(isEffectiveLine(line))
                   effectiveLines++;
           }

           System.out.println("共包含有效代码 " + effectiveLines + " 行");
       } catch (IOException e) {
           System.out.println("无法读取文件");
       }finally {
           fileScanner.close();
       }

   }

    /*
    *判断是否为有效行
    */
    private static boolean isEffectiveLine(String line){
        if(line == null || line.length() == 0)
            return  false;

        String trimLine = line.trim();
        if(trimLine.startsWith("//") || trimLine.length() == 0)
            return  false;

        return true;
    }
}
