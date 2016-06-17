import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/4 0004.
 * 此类用于对输入内容进行过滤，具体是过滤掉文件中的注释及空行，输出文件的有效内容
 */
public class BlankAndCommentFilter implements ContentFilter {
    //过滤space类型字符
    private Matcher blankLine=Pattern.compile("\\s*$",Pattern.MULTILINE).matcher("");
    //过滤单行注释
    private Matcher oneLineComment=Pattern.compile("//.*$",Pattern.MULTILINE).matcher("");
    //过滤多行注释及文本注释,其中星号后面的问好表示Reluctant惰性匹配，很有必要
    private Matcher multiLineComment=Pattern.compile("/\\*.*?\\*/",Pattern.DOTALL).matcher("");

    /**
     * 过滤掉输入字符串中的注释及空行，其中注释包括文档注释、多行注释、单行注释
     * @param string
     * 待过滤字符串
     * @return
     * 过滤后的结果
     */
    @Override
    public String filter(String string) {
        //过滤单行注释
        oneLineComment.reset(string);
        String tempResult=oneLineComment.replaceAll("");
        //过滤多行注释及文档注释
        multiLineComment.reset(tempResult);
        String tempResult1=multiLineComment.replaceAll("");
        //过滤空行
        return blankLine.reset(tempResult1).replaceAll("");
    }
}
