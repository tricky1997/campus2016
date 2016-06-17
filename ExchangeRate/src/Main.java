import jxl.write.WriteException;
import java.io.*;
import java.util.concurrent.*;

/**
 * 此程序用于统计近N天内中国人民银行于其官网上公布的人民币汇率中间价
 * 爬的网站为http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html
 * 此网站内容比较难爬，主要是其对普通的爬虫做了拒绝处理，一般爬虫只能爬得一堆Js
 * 因此在此程序中使用了JxBrowser，它相当于一个浏览器，会按照浏览器的方式将一个网页加载完
 * 我们只需要其完全加载完后的html结果。
 * 但是其得到页面是以异步方式返回结果的，因此在此程序中通过多线程的方式对其处理
 * 写完此程序后，才发现还有其他网站可以查询汇率中间价。并且通过一般方式就可以进行爬虫，更简单一些。是我蠢了。。。
 * JxBrowser需要License，我用的是试用30天的，超过30天就不能用了，也就意味着此程序到7月14号后就不能运行啦ORZ
 */
public class Main {
    //官网上每页列出多少天的汇率
    private static final int ITEMS_PER_PAGE=20;
    //需要统计多少天的汇率
    private static final int RECENT_N_DAY=30;
    public static void main(String[] args) throws WriteException, IOException {
        //下一页的URL过滤器
        ContentFilter nextPageFilter=new NextPageFilter();
        //某天汇率中间价详细信息页面的URL过滤器
        ContentFilter urlForADayFilter=new URLForADayFilter();
        //在详细页面过滤出汇率的过滤器
        ContentFilter exchangeRateFilter=new ExchangeRateFilter();
        //用于存放某天详细汇率中间价页面的URL队列
        BlockingQueue<String> urlForADayQueue=new ArrayBlockingQueue<String>(30);
        //用于存放汇率及时间的队列
        BlockingQueue<String> exchangeRateQueue=new ArrayBlockingQueue<String>(30);
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(new HTMLToPageURL(urlForADayQueue,urlForADayFilter,nextPageFilter,Math.ceil((double)RECENT_N_DAY/ITEMS_PER_PAGE)));
        executorService.execute(new HTMLToExchangeRate(urlForADayQueue,exchangeRateQueue,exchangeRateFilter,RECENT_N_DAY));
        executorService.execute(new ExcelWriter(exchangeRateQueue,".\\ExchangeRate.xls",RECENT_N_DAY));
        executorService.shutdown();
    }
}