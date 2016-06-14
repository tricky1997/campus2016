import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/6/14.
 */
public class ExchangeRateTable { //历史汇率数据信息类

    public static SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd"); //日期字符串格式
    public static int[] ID = {0, 1, 2, 4}; //日期，美元，欧元，港元对应的列号

    public List<String> titles; //标题行
    public List<Line> lines; //数据

    public class Line {
        public Date date; //数据日期
        public List<Double> exchangRates = new ArrayList<Double>(); //汇率数据

        public Line(Date d, List<Double> r) {
            date = d;
            exchangRates = r;
        }
    }

    // 利用网络读取表单数据分析识别后选择美元、欧元和港币构造历史汇率信息类
    public ExchangeRateTable(Element table) {

        System.out.println("开始分析解读数据...");
        titles = new ArrayList<String>();
        lines = new ArrayList<Line>();
        Elements Etitles = table.getElementsByClass("table_head");
        Elements Elines = table.getElementsByClass("first");
        for (int i : ID) {
            String Stitle = Etitles.get(i).text();
            titles.add(Stitle);
        }
        for (Element Eline : Elines) {
            Elements Erates = Eline.getElementsByTag("td");
            Date date = null;
            List<Double> rates = new ArrayList<Double>();
            for (int i : ID) {
                String text = Erates.get(i).text();
                text = text.substring(0, text.length() - 1);
                if (i == 0) {
                    try {
                        date = sim.parse(text);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    rates.add(new Double(text));
                }
            }
            lines.add(new Line(date, rates));
        }
        System.out.println("分析数据完毕...");
    }
}
