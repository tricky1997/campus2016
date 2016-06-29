package EffectiveLines;

import java.io.*;

/**
 * Created by lz on 16-6-24.
 */
public class EffectiveLines
{
    public String filePath = "";
    EffectiveLines(String str)
    {
        this.filePath = str;
        this.countLines();
    }

    public void countLines()
    {
        File javaFile = new File(filePath);
        if(!javaFile.exists())
            return ;

        BufferedReader bf = null;
        Integer lines = 0;

        try
        {
            bf = new BufferedReader(new FileReader(javaFile));
            String lineStr="";
            while((lineStr=bf.readLine())!=null)
                if(!lineStr.matches("^[\\s&&[^\\n]]*$"))//非空行
                    lines++;
        }
        catch(IOException ee)
        {
            System.out.println("IO异常");
        }

        finally
        {
            if(bf!=null)
            {
                try
                {
                    bf.close();
                    bf=null;
                }
                catch(Exception e)
                {
                    System.out.println("关闭时出错");
                }
            }
        }
        System.out.println(lines);
    }

    public static void main(String[] args)
    {
        EffectiveLines el = new EffectiveLines("/home/lz/IdeaProjects/QunarJavaHomework/src/EffectiveLines/EffectiveLines.java");
    }
}
