import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by kuangchunyan on 2016/6/29.
 * 统计一个java文件的有效行数
 * 要求：
 * 1、有效行不包括空行、单行注释；
 * 2、不考虑代码间有多行注释的情况
 */
public class EffectiveLines {
    public static void main(String[] args){
        String path = "TestResource/Cluster.java";
        int lineNumber = new EffectiveLines().countEffectiveLines(path);
        System.out.println("有效行数为：" + lineNumber);

    }

    /**
    * 统计java文件中有效行数
    * @param filePath Java文件的路径
    * @return 返回文件的有效行数
    */
    public int countEffectiveLines(String filePath){
        int lineNumber = 0;//
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String str = null;
            String line = null;
            try{
                while((line = br.readLine())!= null){
                    str = line.trim();
                    if(str.isEmpty()||str.startsWith("//")||(str.startsWith("/*")&&str.endsWith("*/"))){
                        continue;
                    }
                    ++lineNumber;
                }
            }finally{
                br.close();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return lineNumber;
    }

}
