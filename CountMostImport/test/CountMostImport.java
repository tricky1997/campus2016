/**
 * Created by zhaoxin on 16-6-30.
 */
import java.io.File;
import java.lang.String;
import java.util.Scanner;
import java.lang.System;

public class CountMostImport {

    public static void main(String[] args)
    {
        Scanner scan=new Scanner(System.in);
        System.out.print("输入目录名：");
        String file_name=scan.next();
        File directory=new File(file_name);
        if(!directory.exists() || !directory.isDirectory())
        {
            System.out.println("目录不存在或错误");
            System.exit(0);
        }
        JavaFileFilter java_file_filter=new JavaFileFilter();
        File[] java_files=directory.listFiles(java_file_filter);
        CountImport cout_import=new CountImport();
        for (File file : java_files)
        {
            cout_import.count(file);
        }
        cout_import.getMostImport();
    }

}

