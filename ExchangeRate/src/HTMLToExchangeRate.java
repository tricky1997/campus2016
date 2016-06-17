import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Yecheng Li on 2016/6/15 0015.
 * 此类用于得到某天的汇率页面并将其中需要的汇率内容过滤出来加入用于存放汇率的队列
 */
public class HTMLToExchangeRate implements Runnable{
    //继承于LoadAdapter实现个性化定制响应事件，此类用于处理某天汇率页面load完后，对页面的过滤处理
    private class MyLoadAdapter extends LoadAdapter {
        //对当前处理的是第几个页面内容计数
        private int counter=0;

        /**
         * 重写的LoadAdapter类的方法
         * @param event
         * 传入的事件类
         */
        @Override
        public void onFinishLoadingFrame(FinishLoadingEvent event){
            if(event.isMainFrame()){
                //如果当前还没有读入足够的天数
                if(counter<recentNDays) {
                    //得到加载内容
                    String html = event.getBrowser().getHTML();
                    //过滤
                    String filteredResult = exchangeRateFilter.filter(html);
                    try {
                        //加入汇率队列中
                        exchangeRateQueue.put(filteredResult);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                    try {
                        //重新加载下一页
                        browser.loadURL(urlForADayQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //已经读入充足的天数
                else{
                    //刚读到要求的天数的时候，需要将窗口注销
                    if(counter==recentNDays){
                        frame.dispose();
                        counter++;
                    }
                }
            }
        }

    }
    //某天汇率页面的URL
    private BlockingQueue<String> urlForADayQueue;
    //过滤完汇率及时间后的结果存放队列
    private BlockingQueue<String> exchangeRateQueue;
    //用于加载页面的Browser对象
    private Browser browser = new Browser();
    private BrowserView browserView = new BrowserView(browser);
    private JFrame frame = new JFrame();
    private ContentFilter exchangeRateFilter;
    //统计前N天的汇率
    private int recentNDays;

    /**
     * 有参构造函数
     * @param urlForADayQueue
     * 存储有某天汇率网页的URL队列
     * @param exchangeRateQueue
     * 用于存储过滤出的汇率结果的队列
     * @param exchangeRateFilter
     * 过滤出汇率工具
     * @param recentNDays
     * 统计前N天的汇率
     */
    public HTMLToExchangeRate(BlockingQueue<String> urlForADayQueue,BlockingQueue<String> exchangeRateQueue,ContentFilter exchangeRateFilter,int recentNDays){
        this.recentNDays=recentNDays;
        this.exchangeRateFilter=exchangeRateFilter;
        this.urlForADayQueue=urlForADayQueue;
        this.exchangeRateQueue=exchangeRateQueue;

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        browser.addLoadListener(new MyLoadAdapter());
    }

    /**
     * 重写Runnable接口的方法
     */
    @Override
    public void run(){
        try {
            //从URL队列中取出某天的汇率网页的URL
            String urlForADay=urlForADayQueue.take();
            //将此网页载入browser中
            browser.loadURL(urlForADay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
