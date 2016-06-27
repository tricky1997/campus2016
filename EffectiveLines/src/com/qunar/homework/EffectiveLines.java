package com.qunar.homework;

import java.io.*;

/**
 * Created by Jerry on 6/27/2016.
 */
public class EffectiveLines {
    static int codeLines = 0;
    public static void main(String[] args) {
        File file = new File("src/Test.java");
        computeLines(file);
        System.out.println("EffectiveLines is "+codeLines);
    }

    public static void computeLines(File file)
    {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line = "";
            while((line=bf.readLine())!=null)
            {
                if(line.trim().equals(""))
                    continue;
                else if(line.trim().startsWith("//")||line.trim().startsWith("/*"))
                    continue;
                codeLines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
