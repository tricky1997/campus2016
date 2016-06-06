package main.java.com.qunar.training.EffectiveLines;

import java.io.*;

/**
 * Created by shining.cui on 2016/6/5.
 */
public class FileLineCounter {
    public static File getFile() {
        File file = new File("F:\\gitRepo\\campus2016\\training4\\src\\main\\java\\com\\qunar\\training\\EffectiveLines\\HeapSortTest.java");
        return file;
    }

    public static BufferedReader getBufferedReader(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        return bufferedReader;
    }
}
