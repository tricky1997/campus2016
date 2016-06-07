package main.java.com.qunar.training.EffectiveLines;

import java.io.*;

/**
 * Created by shining.cui on 2016/6/5.
 */
public class FileLineCounter {
    public File getFile() {
        File file = new File("F:\\gitRepo\\campus2016\\training4\\src\\main\\java\\com\\qunar\\training\\EffectiveLines\\HeapSortTest.java");
        return file;
    }

    public BufferedReader getBufferedReader(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        return bufferedReader;
    }

    public int getEffectiveLines(BufferedReader bufferedReader) {
        String readLine = null;

        int effectiveLines = 0;
        try {
            while ((readLine = bufferedReader.readLine()) != null) {
                String trimedLine = readLine.trim();
                //若每行以"//"则为注释，非有效行。trim后长度为0则证明为空白行，非有效行。
                if (!trimedLine.startsWith("//") && trimedLine.length() != 0) {
                    effectiveLines++;
                }
            }
        } catch (IOException e) {
            System.out.println("文件读取错误");
        }
        return effectiveLines;
    }
}
