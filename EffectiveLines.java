package iotest;
import java.io.*;
import java.util.Scanner;

// 注释
/**
 * Created by qlf on 2016/7/2.
 */
public class EffectiveLines {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        scanner.close();
        int count = efflines(path);
        System.out.println("有效行数"+count);


    }
    public static int efflines(String filePath) {

        int count = 0;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                if (!s.trim().isEmpty() && !s.trim().startsWith("//")) {
                    count++;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  count;
    }


}
