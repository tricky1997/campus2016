package com.imooc;
import java.io.*;

public class EffectiveLines {



    public static int normalLines = 0;  //有效程序行数

    public static void main(String[] args) throws IOException{

        File file = new File("d://test.java");
        if (file.exists()) {
            comput(file);
        }
        System.out.println("总有效代码行数: " + normalLines);

    }

    private static void comput(File file) throws IOException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                comput(files[i]);
            }

        }
        if (file.isFile()) {

            if(file.getName().matches(".*\\.java")){
                userful(file);
            }
        }
    }

    public static void userful(File file) {
        BufferedReader br = null;

        boolean comment = false;

        int temp_normalLines = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches("^[//s&&[^//n]]*$")||line.startsWith("//")||
                        (comment == true && !line.endsWith("*/"))) {

                } else if (comment == true && line.endsWith("*/")) {

                    comment = false;
                }else if (line.startsWith("/*") && !line.endsWith("*/")) {

                    comment = true;
                }else {
                    normalLines++;
                    temp_normalLines++;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
