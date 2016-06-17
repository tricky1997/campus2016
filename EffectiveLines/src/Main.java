/**
 * Created by Administrator on 2016/6/4 0004.
 * 该程序用于统计指定文件的有效行行数，其中不包括文档注释、空行、单行注释、多行注释。
 */
public class Main {
    public static void main(String[] args){
        //定义将文件读入内存的工具
        FileToString fileToString=new FileToString("C:\\Users\\Administrator\\Desktop\\HomeWork\\EffectiveLines\\src\\FileToString.java");
        //定义判断行是否有效的的规则
        ContentFilter contentFilter =new BlankAndCommentFilter();
        //定义统计类
        Statistics statistics=new Statistics(contentFilter,fileToString);
        System.out.println("当前文件为："+fileToString.getFile().getAbsolutePath());
        System.out.println("有效行数为："+statistics.effectiveLineCount());
        /*System.out.println("abccd");
        System.out.println(1234);*/
    }
}