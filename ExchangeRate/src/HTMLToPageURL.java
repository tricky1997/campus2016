import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Yecheng Li on 2016/6/4 0004.
 * 用以读取某些天汇率列表的页面，并将其中某些天的汇率详细页面的URL过滤出来存储于队列中
 */
public class HTMLToPageURL implements Runnable{
    //继承于LoadAdapter实现个性化定制响应事件，此类用于处理某些天汇率列表页面并将其中某天的汇率详细列表的URL过滤出来存储于队列中
    private class MyLoadAdapter extends LoadAdapter{
        //用于记录当前过滤到第几页
        private int counter=0;

        /**
         * 重写的LoadAdapter类的方法
         * @param event
         * 传入的事件对象
         */
        @Override
        public void onFinishLoadingFrame(FinishLoadingEvent event){
            if(event.isMainFrame()){
                if(counter<pages) {
                    //得到html文件内容
                    String html = event.getBrowser().getHTML();
                    //System.out.println(html);
                    //过滤出某些天的具体url
                    String filteredResult = urlForADayFilter.filter(html);
                    /*System.out.println("开始打印过滤结果");
                    System.out.println(filteredResult);*/
                    //过滤出下一页的url
                    String urlForNextPage = nextPageFilter.filter(html);
                    String[] splitString = filteredResult.split("\n");
                    //第一个url不是页面的url
                    splitString[0] = "";
                    for (String string : splitString) {
                        if (!string.equals("")) {
                            try {
                                //将某天汇率的url放入队列中
                                urlForADayQueue.put(string);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    counter++;
                    //重新载入页面
                    browser.loadURL(urlForNextPage);
                }
                else {
                    if(counter==pages){
                        frame.dispose();
                        counter++;
                    }
                }
            }
        }

    }
    //需要载入多少页
    private double pages;
    //用于存放某天汇率页面URL的队列
    private BlockingQueue<String> urlForADayQueue;
    //用于过滤某天页面URL的过滤器
    private ContentFilter urlForADayFilter;
    //用于过滤下一页URL的过滤器
    private ContentFilter nextPageFilter;
    Browser browser = new Browser();
    BrowserView browserView = new BrowserView(browser);
    JFrame frame = new JFrame();

    /**
     * 有参构造函数
     * @param urlForADayQueue
     * 存放某天汇率页面URL的队列
     * @param urlForADayFilter
     * 用于过滤某天页面URL的过滤器
     * @param nextPageFilter
     * 用于过滤下一页URL的过滤器
     * @param pages
     * 需要载入多少页
     */
    public HTMLToPageURL(BlockingQueue<String> urlForADayQueue, ContentFilter urlForADayFilter, ContentFilter nextPageFilter, double pages){
        this.pages=pages;
        this.urlForADayQueue=urlForADayQueue;
        this.urlForADayFilter=urlForADayFilter;
        this.nextPageFilter=nextPageFilter;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        browser.addLoadListener(new MyLoadAdapter());
    }

    /**
     * 重写的Runnable接口的方法
     */
    @Override
    public void run(){
        //汇率首页的URL
        browser.loadURL("http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html");
    }
}
