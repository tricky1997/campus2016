package org.qunar.exchangerate;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Created by zhang ruixiong on 2016/6/29 0003.
 */
public class NetConversation {
    //与网络交互获取中间价信息
    public String getResult(){
        Date currentDate = new Date();
        currentDate.setTime(System.currentTimeMillis());
        Date startDate = new Date();
        startDate.setTime(System.currentTimeMillis()-(long)30*24*60*60*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String param = "projectBean.startDate="+sdf.format(startDate)+"&projectBean.endDate="+sdf.format(currentDate);
        URL url = null;
        try {
            url = new URL("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action"+"?"+param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection hc = null;
        try {
            hc = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hc.setDoInput(true);

        Scanner scanner = null;
        try {
            hc.connect();
            scanner = new Scanner(hc.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()){
            sb.append(scanner.nextLine());}
        return sb.toString();
    }
}
