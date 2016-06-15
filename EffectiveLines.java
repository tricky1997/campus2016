/**
 * 一、统计一个Java文件的有效行数。（作业命名：EffectiveLines）
 *1、有效不包括空行
 *2、不考虑代码见有多行注释的情况
 * Created by MaNa on 2016/6/13.
 */
import java.io.*;
import java.nio.file.Files;
import java.util.List;


public class EffectiveLines {

    public static void main(String[] args) throws IOException {
        String filename="F:/ITResManager.java";
        EffectiveLines ef=new EffectiveLines();
        ef.countLines(filename);
    }
    /**
     *
     * 统计有效行(去除空行及单行注释)
     */
    public void countLines(String filename) throws IOException {
        File file=new File(filename);
        int countNumber=0;
        String line=null;
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            while((line=br.readLine())!=null){
                String everyLine=line.trim();
                if(everyLine.startsWith("//")||everyLine.equals("")) continue;
                countNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print(countNumber);
    }
}
