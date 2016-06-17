import java.io.File;
/**
 * Created by Administrator on 2016/6/5 0005.
 * 此程序用于统计指定目录下所有文件的import的类的数目，并输出指定的前N多引用的类
 * 其中FileToString类用于将指定文件的内容读入字符串中处理
 * ContentFilter类用于将读入的内容进行有效性过滤，此类声明为接口因为其他两个作业需要使用其他逻辑的过滤方法
 * DataHolder类维护一个N个元素的小顶堆，这样找出前N个元素的时间复杂度大概只有n*logN其中n表示所有元素的个数
 * Statistics类用于统计用户指定内容
 */
public class Main {

    public static void main(String[] args){
        //声明将文件内容读入字符串的工具
        FileToString fileToString=new FileToString();
        //声明数据维护对象
        DataHolder dataHolder=new DataHolder(10);
        //声明内容过滤对象
        ContentFilter contentFilter=new ImportClassFilter();
        //声明统计对象
        Statistics statistics=new Statistics(fileToString,contentFilter,dataHolder);
        //指定搜索目录并进行统计
        statistics.mostImportCount(new File("C:\\Users\\Administrator\\Desktop\\HomeWork\\CountMostImport\\src"));
        //输出topN个import的类
        statistics.topImport();
        //System.out.println("java.util.regex.Pattern".compareTo("java.util.regex.Matcher"));
    }
}
