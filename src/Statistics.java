import java.io.File;
import java.util.List;
/**
 * Created by Administrator on 2016/6/6 0006.
 * @author Administrator
 * 此类用于对用户指定的内容进行统计
 */
public class Statistics {
    //将文件内容读入字符串的工具
    private FileToString fileToString;
    //文件内容过滤工具
    private ContentFilter contentFilter;
    //有效import类维护对象
    private DataHolder dataHolder;

    /**
     * 有参构造函数
     * @param fileToString
     * 需要设置的将文件内容读入字符串的工具
     * @param contentFilter
     * 需要设置的内容过滤器
     * @param dataHolder
     * 需要设置的维护有效import类的对象
     */
    public Statistics(FileToString fileToString,ContentFilter contentFilter,DataHolder dataHolder){
        this.dataHolder=dataHolder;
        this.fileToString=fileToString;
        this.contentFilter=contentFilter;
    }

    /**
     * 对指定目录下所有文件进行排查，如果目录下包含目录，则递归的进行统计
     * @param directory
     * 要搜索最常引入类的目录
     */
    public void mostImportCount(File directory){
        if(!directory.isDirectory()){
            System.out.println("该路径不是一个目录！");
            System.exit(0);
        }
        for(File file:directory.listFiles()) {
            //如果是目录则递归的进行统计
            if(file.isDirectory()){
                mostImportCount(file);
            }
            else{
                //将文件内容读入字符串中，并对读入内容进行过滤
                String filteredString=contentFilter.filter(fileToString.readToString(file));
                for(String classPath:splitImport(filteredString)){
                    //split经常会出现一些空项目，删除
                    if(classPath.equals("")){
                        continue;
                    }
                    //加入数据维护对象中
                    dataHolder.addClass(classPath);
                }
            }
        }
    }

    /**
     * 从数据维护对象dataHolder中取得top N个import类
     * 直接打印，没有返回值
     */
    public void topImport(){
        List<DataHolder.Items> itemsList=dataHolder.getTop();
        System.out.println("一共有"+dataHolder.getCounter()+"个类，前十分别为：");
        for(int i=itemsList.size()-1;i>=0;--i){
            if(itemsList.get(i).getClassCount()>0){
                System.out.println(itemsList.get(i));
            }
        }
    }

    /**
     * 对多个import语句进行切分
     * @param mutiImport
     * 需要切分的多个import
     * @return
     * 按换行符进行切分后的字符串数组
     */
    private String[] splitImport(String mutiImport){
        return mutiImport.split("\n");
    }
}
