/**
 * Created by Administrator on 2016/6/4 0004.
 * 用以统计之用，当前类中只有统计有效行数的方法
 */
public class Statistics {
    //内容过滤器
    private ContentFilter contentFilter;
    //文件读入内存工具
    private FileToString fileToString;

    /**
     * 带有文件参数的构造函数
     * @param contentFilter
     * 内容过滤器
     * @param fileToString
     * 文件读入内存工具
     */
    public Statistics(ContentFilter contentFilter,FileToString fileToString){
        this.contentFilter = contentFilter;
        this.fileToString=fileToString;
    }

    /**
     * 默认构造函数
     */
    public Statistics(){
        this(new BlankAndCommentFilter(),new FileToString("C:\\Users\\Administrator\\Desktop\\HomeWork\\EffectiveLines\\src\\Main.java"));
    }

    /**
     * 统计有效行数
     * @return
     * 有效行的数目
     */
    public int effectiveLineCount(){
        //过滤后的结果
        String filteredResult=contentFilter.filter(fileToString.readToString());
        //System.out.println(filteredResult);
        //使用行切分
        String[] splitResult=filteredResult.split("\n");
        //经常会出现首行空行的情况（split导致的），这里处理下
        if(splitResult[0].equals("")){
            return splitResult.length-1;
        }
        return splitResult.length;
    }
}
