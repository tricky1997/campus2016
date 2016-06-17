import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yecheng Li on 2016/6/11 0011.
 * 此类用于过滤页面中的汇率及时间内容
 */
public class ExchangeRateFilter implements ContentFilter {
    //人民币对美元的正则过滤规则
    private Matcher RMBToDollarMatcher= Pattern.compile("1美元对人民币(\\d+.\\d+)元").matcher("");
    //人民币对欧元的正则过滤规则
    private Matcher RMBToEuroMatcher=Pattern.compile("1欧元对人民币(\\d+.\\d+)元").matcher("");
    //人民币对港元的正则过滤规则
    private Matcher RMBToHKDMatcher=Pattern.compile("1港元对人民币(\\d+.\\d+)元").matcher("");
    //时间正则过滤规则
    private Matcher timeMatcher=Pattern.compile("(\\d+)年(\\d+)月(\\d+)日银行间外汇市场人民币汇率中间价为").matcher("");

    /**
     * 重写的ContentFilter的方法
     * @param string
     * 待过滤的字符串
     * @return
     * 过滤结果，结果以&符号分割
     */
    @Override
    public String filter(String string) {
        StringBuffer stringBuffer=new StringBuffer(100);
        timeMatcher.reset(string);
        RMBToDollarMatcher.reset(string);
        RMBToEuroMatcher.reset(string);
        RMBToHKDMatcher.reset(string);
        //返回结果中加入时间
        while(timeMatcher.find()){
            stringBuffer.append(timeMatcher.group(1));
            stringBuffer.append("&");
            stringBuffer.append(timeMatcher.group(2));
            stringBuffer.append("&");
            stringBuffer.append(timeMatcher.group(3));
            stringBuffer.append("&");
        }
        //返回结果中加入美元汇率
        while(RMBToDollarMatcher.find()){
            stringBuffer.append(RMBToDollarMatcher.group(1));
            stringBuffer.append("&");
        }
        //返回结果中加入欧元汇率
        while (RMBToEuroMatcher.find()){
            stringBuffer.append(RMBToEuroMatcher.group(1));
            stringBuffer.append("&");
        }
        //返回结果中加入港元汇率
        while (RMBToHKDMatcher.find()){
            stringBuffer.append(RMBToHKDMatcher.group(1));
        }
        return stringBuffer.toString();
    }
}
