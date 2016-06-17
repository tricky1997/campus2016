import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yecheng Li on 2016/6/11 0011.
 * 此类用于过滤页面中某天汇率页面的URL
 */
public class URLForADayFilter implements ContentFilter{
    //url过滤规则
    private Matcher singleDayMatcher= Pattern.compile("href=\"(/zhengcehuobisi/\\d+/\\d+/\\d+/\\d+/index.html)\"").matcher("");
    //网站前缀
    private String urlPrefix="http://www.pbc.gov.cn";
    @Override
    public String filter(String string) {
        StringBuffer stringBuffer=new StringBuffer(1000);
        singleDayMatcher.reset(string);
        while(singleDayMatcher.find()){
            stringBuffer.append(urlPrefix+singleDayMatcher.group(1));
           /* stringBuffer.append("|");
            stringBuffer.append(singleDayMatcher.group(2));
            stringBuffer.append("|");
            stringBuffer.append(singleDayMatcher.group(3));*/
           /* stringBuffer.append("|");
            stringBuffer.append(singleDayMatcher.group(4));*/
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
