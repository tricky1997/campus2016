import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yecheng Li on 2016/6/11 0011.
 * 此类用于过滤页面中下一页的URL
 */
public class NextPageFilter implements ContentFilter {
    //下一页的过滤规则
    private Matcher nextPageMatcher;
    //初始化的index的后缀
    private int pageCounter=2;
    //URL的前缀
    private String urlPrefix="http://www.pbc.gov.cn";

    /**
     * 重写的ContentFilter的方法
     * @param string
     * 待过滤的字符串
     * @return
     * 过滤结果
     */
    @Override
    public String filter(String string) {
        StringBuffer stringBuffer=new StringBuffer(1000);
        nextPageMatcher=Pattern.compile("tagname=\"(/zhengcehuobisi/\\d+/\\d+/\\d+/\\d+/index"+pageCounter+".html)\"").matcher(string);
        while(nextPageMatcher.find()){
            stringBuffer.append(urlPrefix+nextPageMatcher.group(1));
            stringBuffer.append("\n");
        }
        pageCounter++;
        return stringBuffer.toString();
    }
}
