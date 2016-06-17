import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/6 0006.
 * 此类用于对字符串内容进行过滤，具体是过滤出输入字符串中import的内容
 */
public class ImportClassFilter implements ContentFilter {
    //过滤出import后面的内容
    private Matcher importMatcher= Pattern.compile("import\\s+(.+?);",Pattern.MULTILINE).matcher("");
    //对过滤出的内容进行space类型字符的删除
    private Matcher blankMatcher=Pattern.compile("\\s+").matcher("");

    /**
     * 过滤出输入字符串中import的内容
     * @param string
     * 待过滤的字符串
     * @return
     * 过滤后的内容
     */
    @Override
    public String filter(String string) {
        StringBuffer filteredResult=new StringBuffer(1000);
        importMatcher.reset(string);
        String[] splits;
        while(importMatcher.find()){
            //通过group取出import后面的内容
            String classPath=importMatcher.group(1);
            blankMatcher.reset(classPath);
            //对过滤后的内容中的space类字符过滤
            String withNoBlank=blankMatcher.replaceAll("");

            if(!withNoBlank.startsWith("static")){
                if(!withNoBlank.endsWith("*")){
                    filteredResult.append(withNoBlank);
                    filteredResult.append("\n");
                }
            }
        }

        return filteredResult.toString();
    }
}
