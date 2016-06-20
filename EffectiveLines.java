import java.io.*;
//1.暂未考虑形如/**/的注释
//2.使用FileReader只能处理与程序相同编码(UTF-8)的文件
//3.不考虑传入参数不正确的情况

public class EffectiveLines {
    public static int effectiveLines(String fileName){
        int cnt=0;
        try {
            FileReader fileReader=new FileReader(fileName);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String s;
            while ((s=bufferedReader.readLine())!=null){
                s=s.trim();
                if(!s.isEmpty()&&!s.startsWith("//"))
                    cnt++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cnt;
    }
    public static void main(String[] args){
        int n=effectiveLines(args[0]);
        System.out.println(n);
    }
}
