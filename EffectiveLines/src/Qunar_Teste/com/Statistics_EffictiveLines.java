package Qunar_Teste.com;

import java.io.BufferedReader;
import java.io.IOException;


 //统计Java文件的有效行数，并输入对应的有效行数
 

public class Statistics_EffictiveLines
{
    public static int  Compute_Effictive_Lines(BufferedReader File_Reader)
    {
		//有效行数
        int Row_Number=0;
        String s="";
        try {
            while((s=File_Reader.readLine())!=null)
        {
             String File_Line=s.trim();
			 //由于不考虑多行注释，去除空行和单行注释即是有效行数
             if((File_Line.isEmpty())||File_Line.startsWith("//")|| (File_Line.startsWith("/*") && File_Line.endsWith("*/")))
                  continue;
             else
                  Row_Number++;

         }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Row_Number;
    }
}
