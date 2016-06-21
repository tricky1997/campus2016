import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
/**
 * Created by miaosen on 2016/6/8.
 */
public class PostData {
    private static Logger logger = Logger.getLogger(PostData.class);
    //发送Post请求，将起止时间传入，获取相应时间范围的HTML
    public static String sendPost() {
        String result = "";// 返回的结果
        URL url = null;//请求的网址
        HttpURLConnection http = null;
        PrintWriter out = null;
        BufferedReader bufferedReader=null;
        try {
            url = new URL(JsoupH.url);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setConnectTimeout(15000);//设置连接超时时间15s
            http.setReadTimeout(15000);//设置读取数据超时时间15s
            http.setInstanceFollowRedirects(true);
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDefaultUseCaches(false);//不使用缓存
            http.setDoOutput(true);//设置向HttpUrlConnection输出
            //将起止时间传入
            String queryString = "projectBean.startDate=" + DateArr().get(0) + "&projectBean.endDate=" + DateArr().get(1) + "&queryVN=true";
            out = new PrintWriter(http.getOutputStream());
            out.print(queryString);//传入参数
            out.close();
            http.connect();//连接
            bufferedReader = new BufferedReader(new InputStreamReader(http.getInputStream()));//返回流
            String line;
            // 读取返回的内容
            while ((line = bufferedReader.readLine()) != null) {
                result += line + "\r\n";
            }
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return result;

    }


    //获取存有格式化的起止日期链表
    public static ArrayList<String> DateArr() {
        ArrayList<String> dateArrayList;
        Calendar calendar;
        String dateFormate = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat;
        String startDate;//开始日期
        String endDate;//截止日期
        dateArrayList = new ArrayList<String>();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, JsoupH.iDate);
        simpleDateFormat = new SimpleDateFormat(dateFormate);
        endDate = simpleDateFormat.format(new Date());
        startDate = simpleDateFormat.format(calendar.getTime());
        dateArrayList.add(0, startDate);
        dateArrayList.add(1, endDate);
        return dateArrayList;
    }
}
