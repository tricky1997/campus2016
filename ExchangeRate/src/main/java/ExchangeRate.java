import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ju on 2016/6/20.
 */
public class ExchangeRate {


    public static void main(String[] args)throws Exception{
        URL url=getUrl();
        getRate(url);
        save();
        for(int i=0;i<dataList.size();i++){
            List<String> list=dataList.get(i);
            for(int j=0;j<list.size();j++){
                System.out.print(list.get(j)+" ");
            }
            System.out.println();
        }

    }
    private static int[] Index = {0,1,2,4};//0:time 1:usd 2:eur 4:hkd
    private static List<List> dataList=new ArrayList<List>();

    /**
     *    1、 设置要爬取的网页的格式
     */
    public static URL getUrl() throws Exception{
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String endDate=format.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -100);
        String startDate=format.format(calendar.getTime());
        URL url=new URL("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate="+
                startDate+"&projectBean.endDate="+endDate);
        return url;
    }

    /**
     *    2、爬取网页当中的数据
     */
    public static void getRate(URL url)throws Exception{
        //解析地址，并设置超时时间
        Document document= Jsoup.parse(url, 1000000);
        Element result = document.getElementsByClass("list").get(0);
        //获取表头,并保存到titleList中
        Elements heads=result.select("th");
        List<String> titleList = new ArrayList<String>();
        for(int i:Index){
            titleList.add(heads.get(i).text().trim().substring(0, 2));
        }
        dataList.add(titleList);
        // 获取每一天的数据
        Elements rows = result.getElementsByClass("first");
        for(Element row:rows){
            Elements columns=row.getElementsByTag("td");
            List<String> rowList = new ArrayList<String>();
            for(int i:Index){
                String rowData = columns.get(i).text().trim();
                rowData=rowData.substring(0,rowData.length()-1);
                rowList.add(rowData);
            }
            dataList.add(rowList);
        }
    }
    /**
     *    3、将数据保存到excel中,假设保存到H://ExchangeRate.xlsx
     */
    public static void save() throws Exception{
        File file = new File("H://ExchangeRate.xls");
        WritableWorkbook book= Workbook.createWorkbook(file);
        WritableSheet sheet=book.createSheet("汇率", 0);
        for(int i=0;i<4;i++){
            for(int j=0;j<30;j++){
                List<String> list=dataList.get(j);
                String string=list.get(i);
                sheet.addCell(new Label(i,j,string));
            }
        }
        book.write();
        book.close();
    }
}
