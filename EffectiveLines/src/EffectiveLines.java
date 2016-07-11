/**
 * Created by zhaoxin on 16-6-30.
 */
import java.lang.String;
import java.util.Scanner;
import java.lang.System;
import java.io.File;
public class EffectiveLines {
    public static void main(String[] args)
    {
        System.out.println("用户的当前工作目录:/n"+System.getProperty("user.dir"));
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入要检测的java文件");
        String java_name=scan.next();
        File java_file=new File(java_name);
        if (!java_file.exists())             //如果输入文件名无效
        {
            System.out.println("输入文件不存在");
            System.exit(0);
        }
        int num=0;
        try
        {
            scan=new Scanner(java_file);
            while(scan.hasNextLine())
            {
                String line=scan.nextLine();
                line.trim();
                if(!line.isEmpty() && !line.startsWith("//"))   //检测是否有单行注释
                {
                    num++;
                }
            }

        }
        //
        //
        catch(java.io.FileNotFoundException exc)
        {
            System.out.println("输入文件未找到");
            System.exit(0);
        }
        String result=String.format("文件中的有效行数为%d行",num);
        System.out.println(result);
    }
}
