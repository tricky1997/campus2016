package Qunar_Teste.com;

import java.io.*;
import java.util.Scanner;

/**
 题目：统计一个Java文件的有效行数。
    1、有效不包括空行
    2、不考虑代码中有多行注释的情况
 */
public class EffictiveLines_Main {

     public static void main(String[] args) {
		 //通过命令行输入待统计的Java文件路径  ,当输入exit结束输入
        Scanner in=new Scanner(System.in);
        String input_s="";
         while(true)
         {
             String str = in.nextLine();
             if(str.equals("exit"))
                 break;
               input_s=str;
         }
         try{
			 //加载Java文件
             File f=new File(input_s);
           //   File f=new File("F:\\EffectiveLines\\File\\TestCase1.java");
             FileReader s=new FileReader(f);
             BufferedReader Java_File=new BufferedReader(s);
             int Effictive_Line=0;
			 //计算Java文件的有效行，并返回有效行数
             Effictive_Line= Statistics_EffictiveLines.Compute_Effictive_Lines(Java_File);
             System.out.println(Effictive_Line);
             Java_File.close();

         }
         catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("没有指定文件");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

     }

    
}
