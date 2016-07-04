package com.qunar.hjy;

/**
 * Created by lenovo on 2016/7/4.
 */

import java.io.*;
import java.util.Scanner;

public class EffectiveLines {
    private static int codeLines;

    public static void main(String[] args) {
        EffectiveLines el = new EffectiveLines();
        Scanner sca = new Scanner(System.in);
        System.out.println("Please input the path of your file：");
        while (sca.hasNext()) {
            File file = new File(sca.nextLine());
            el.computeLines(file);
            System.out.println("Effectice Lines： " + codeLines);
            codeLines = 0;
            System.out.println("Please input the path of your file：");
        }
    }

    public void computeLines(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                else if (line.trim().startsWith("//"))
                    continue;
                codeLines++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can not find the files!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
