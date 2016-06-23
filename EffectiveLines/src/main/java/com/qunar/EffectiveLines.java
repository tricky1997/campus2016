package com.qunar;

import java.io.*;

/**
 * Created by hanju on 2016/6/20.
 */
public class EffectiveLines {
    private String filePath;
    private int lineNumber;
    public EffectiveLines(String filePath)
    {
        this.filePath=filePath;
        this.lineNumber=0;
    }
    public int getEffectiveLine()
    {
        countEffectiveLine();
        return lineNumber;
    }

    private void countEffectiveLine()
    {
        if(!filePath.contains(".java"))
        {
            System.out.println("不是 java文件！！！");
            return;
        }
        File file=new File(filePath);
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            while((line=br.readLine())!=null)
            {
                String noEmptyLine=line.replaceAll(" ","");
                if(noEmptyLine.length()>0)
                {
                    if(noEmptyLine.contains("//")&&noEmptyLine.indexOf("//")==0||noEmptyLine.contains("/*")&&noEmptyLine.indexOf("/*")==0)
                        continue;;
                    lineNumber++;
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(br!=null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
