/**
 * Created by xuxingbo on 2016/6/20.
 */

public class Main {
    public static void main(String[] args) {
        //需要抓取数据的网址
        String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.startTransform(url,"exchangeRate.xls");
    }
}
