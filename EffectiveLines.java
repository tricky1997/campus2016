import java.io.*;
import java.util.Scanner;


/**
 * Created by meng fen zong
 */
public class EffectiveLines {
    public static void main(String[] args){
        EffectiveLines el = new EffectiveLines();
        System.out.println("请输入有效的Java文件(如：d:/test.java)：");
        Scanner in = new Scanner(System.in);
        String filename = in.next();
        File f = new File(filename);
        while(!f.exists()||!f.isFile()){
            System.out.println("输入文件名错误，请输入有效的Java文件(如：d:/test.java)：");
            filename = in.next() ;
            f = new File(filename) ;
        }
        System.out.println("该Java文件的有效行数为："+ el.CountLines(f));

    }
    /*
    处理java文件统计有效行数
     */
    public int CountLines(File f){
        int countlines = 0 ;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            StringBuilder sb = new StringBuilder();
            String str = "" ;
            //把文件读取到缓冲流中并把内容放到字符串s中
            while((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
            String s = sb.toString() ;
            //把单行注释用“”替换掉
            s = s.replaceAll("\\/\\/[^\n]*", "");
            //把多行注释用“”替换掉
            s = s.replaceAll("\\/\\*(\\s|.)*?\\*\\/","");
            //把多行空白行用“”替换掉
            s = s.replaceAll("(?m)^\\s*\\n","");
            System.out.println(s);
            //最后把字符串用“\n”进行分割，分割成的字符串数组的长度就是java文件的有效行数
            String[] slines = s.split("\\n");
            br.close();
            return slines.length ;
        }catch (IOException ioe){
            System.out.println("error");
            ioe.printStackTrace();
        }
        return countlines ;
    }
}