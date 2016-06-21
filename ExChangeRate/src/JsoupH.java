
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by ZhangMiaosen on 2016/6/17.
 */
/*分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，
形成excel文件输出。汇率数据找相关的数据源，自己爬数据分析。*/

public class JsoupH {
    public static final String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";//请求地址
    public static final int iDate = -30;//间隔时间

    public static void main(String[] args) {

        PoiExcel.newExcel(JsoupHTML(PostData.sendPost()));
    }
    //解析发送Post请求后返回的HTMl,并将解析出的数据返回
    public static ArrayList JsoupHTML(String html) {
        Document doc = (Document) Jsoup.parse(html);
        Elements tables = doc.select(".first");
        ArrayList<RateBean> rateBeanArrayList = new ArrayList();//存放RateBean的ArrayList

        //将数据依次解析
        for (Element elememt : tables) {
            String date = elememt.select("td").get(0).text();
            String dollar = elememt.select("td").get(1).text();
            String eru = elememt.select("td").get(2).text();
            String hkdollar = elememt.select("td").get(4).text();
            RateBean rateBean = new RateBean();
            rateBean.setDate(date);
            rateBean.setDollar(dollar);
            rateBean.setEru(eru);
            rateBean.setHkDollar(hkdollar);
            rateBeanArrayList.add(rateBean);
        }
        return rateBeanArrayList;
    }

}
