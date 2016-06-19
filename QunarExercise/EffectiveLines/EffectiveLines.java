/**
 * Created by xuxingbo on 2016/6/14.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 题目：统计一个Java文件的有效行数。
    1、有效不包括空行
    2、不考虑代码中有多行注释的情况
 思路：java中的注释有三种：单行注释，块注释和javadoc注释，由于不考虑多行注释，所以
       只需要在文件中去除单行注释（以“//”开头）和空行即可

 参数：通过args来接受java文件的完整路径
 */
public class EffectiveLines {
    public static void main(String[] args){
        if(args.length != 1 || !args[0].endsWith(".java")){
            System.out.println("输入的参数不合法，请输入正确的java文件名");
            return;
        }
        int count = 0;//用来统计文件的有效行
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            //用来接收行数据
            String str=null;
            while((str=br.readLine())!= null){
                //去掉字符串首尾的空格
                String trimStr = str.trim();
                //空行和单行注释不统计
                if(!trimStr.equals("") && !trimStr.startsWith("//"))
                    count++;
            }
            System.out.println("文件的有效行数为："+ count);
        } catch (IOException e) {
            System.out.println("文件读取错误");
        }
    }
}
