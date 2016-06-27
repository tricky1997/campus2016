import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * Created by meng fen zong
 */
public class ExchangeRate {
    private String url = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
    private String url_two = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html";
    public List<Double> dollar = new ArrayList<Double>() ;
    public List<Double> euro = new ArrayList<Double>();
    public List<Double> hk = new ArrayList<Double>();
    public String getUrl(){
        return this.url ;
    }
    public String getUrlTwo(){
        return this.url_two ;
    }
    public String getUrlFirst(){
        return this.url.substring(0,21) ;
    }
    public static void main(String[] args) {
        try {
            ExchangeRate er = new ExchangeRate();
            er.getData(er.getUrl(),1);//设置第一页爬取20条数据
            er.getData(er.getUrlTwo(),2);//设置第二页爬取10条数据
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getData(String url,int select){
        List<String> secondUrl = new ArrayList<String>() ;
        secondUrl = getUrlFromPage(url);
        int index = 0 ;
        int num = 10 ;
        for(String strUrl:secondUrl){
            //在List集合中的第一条链接不是我们所需要的，在这里进行判断
            if(index != 0){
                make(this.getUrlFirst()+strUrl);//爬取数据并且到集合中
            }
            //判断是否只用爬取10条数据
            if(select == 2 && index == num)
                break;
            index ++ ;
        }
        //把集合中的数据输出到Excel文件中
        WritableWorkbook w = null ;
        OutputStream os = null ;
        try{
            File file1 = new File("D:\\huilv.xls");
            // 设置文件
            WritableWorkbook workbook = Workbook.createWorkbook(file1);
            // 设置sheet名
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // 设置单元格
            Label label = new Label(0, 0, "1美元");
            // 追加单元格
            sheet.addCell(label);
            label = new Label(1,0,"1欧元");
            sheet.addCell(label);
            label = new Label(2,0,"1港元");
            sheet.addCell(label);
            int i = 0;
            for(Double db :dollar){
                label = new Label(0,1+i,String.valueOf(db));
                sheet.addCell(label);
                i++;
                if(i == 30)
                    break;
            }
            i = 0;
            for(Double db :euro){
                label = new Label(1,1+i,String.valueOf(db));
                sheet.addCell(label);
                i++;
                if(i == 30)
                    break;
            }
            i = 0;
            for(Double db :hk){
                label = new Label(2,1+i,String.valueOf(db));
                sheet.addCell(label);
                i++;
                if(i == 30)
                    break;
            }
            workbook.write();
            workbook.close();

        }catch (Exception ie){
            ie.printStackTrace();
        }

    }

    /*
    把html中需要的数据读到集合中
     */
    public void make(String secondUrl){
        HtmlPage page  = getHtmlPage(secondUrl);
        //获取存放所需要的数据的DIV
        HtmlDivision div = (HtmlDivision)page.getElementById("zoom");
        //对文本数据进行处理并添加到集合中
        String[] stemp = div.getTextContent().trim().split("：");
        String[] stemptwo = stemp[1].split("，");
        double d = Double.parseDouble(stemptwo[0].substring(7,stemptwo[0].length()-1));
        dollar.add(d);
        double e = Double.parseDouble(stemptwo[1].substring(7,stemptwo[0].length()-1));
        euro.add(e);
        double h = Double.parseDouble(stemptwo[3].substring(7,stemptwo[0].length()-1));
        hk.add(h);
    }
    /*
    从http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html这个链接的html中获取真正存放数据的链接地址
     */
    public List<String> getUrlFromPage(String url){
        HtmlPage page = getHtmlPage(url);
        //获取html中的所有链接地址
        List<HtmlAnchor> achList=page.getAnchors();
        List<String> secondUrl = new ArrayList<String>() ;
        String tempUrl ;
        for(HtmlAnchor htmlAnchor:achList){
            tempUrl = htmlAnchor.getHrefAttribute() ;
            //从所有的链接地址中筛选出所需要的地址
            if(tempUrl.matches("^/zhengcehuobisi/([0-9]+/){4}index.html$")){
                secondUrl.add(tempUrl);
            }
        }
        return secondUrl ;
    }


    /*
    获取html信息
     */
    public HtmlPage getHtmlPage(String url){
        HtmlPage page = null ;
        try {
            WebClient webClient = new WebClient();
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            page = webClient.getPage(url);
            webClient.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return page ;
    }
}
