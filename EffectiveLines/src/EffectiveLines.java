import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by ju on 2016/6/16.
 * 判断java文件的有效行数
 */
public class EffectiveLines {
    //统计有效行数变量
    private int lineCount=0;

    //判断文件是否是java文件
    private  boolean isJavaFile(File file){
        if(file.getName().endsWith(".java")){
            return true;
        }
        return false;
    }

    //统计文件的有效行数
    public int effectiveLines(File file){
        if(isJavaFile(file)){
            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String line;
                while((line=br.readLine())!=null){
                    line=line.trim();
                    if(!(("".equals(line))||line.startsWith("//")||
                            (line.startsWith("/*")&&(line.endsWith("*/"))))){
                        lineCount++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lineCount;
    }
    public static void main(String [] args){
        //以Test.java为例进行简单测试
        File file=new File("H:\\Test.java");
        EffectiveLines lines=new EffectiveLines();
        int count=lines.effectiveLines(file);
        System.out.println(count);
    }
}
