package Qunar_Teste.com;

import java.io.*;
import java.util.Scanner;

/**
 ��Ŀ��ͳ��һ��Java�ļ�����Ч������
    1����Ч����������
    2�������Ǵ������ж���ע�͵����
 */
public class EffictiveLines_Main {

     public static void main(String[] args) {
		 //ͨ�������������ͳ�Ƶ�Java�ļ�·��  ,������exit��������
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
			 //����Java�ļ�
             File f=new File(input_s);
           //   File f=new File("F:\\EffectiveLines\\File\\TestCase1.java");
             FileReader s=new FileReader(f);
             BufferedReader Java_File=new BufferedReader(s);
             int Effictive_Line=0;
			 //����Java�ļ�����Ч�У���������Ч����
             Effictive_Line= Statistics_EffictiveLines.Compute_Effictive_Lines(Java_File);
             System.out.println(Effictive_Line);
             Java_File.close();

         }
         catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("û��ָ���ļ�");
        }
        catch(IOException e) {
            e.printStackTrace();
        }

     }

    
}
