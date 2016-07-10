/**
 * Created by zhaoxin on 16-7-9.
 */
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.htmlparser.Parser;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExchangeRate {

    private String url_home="http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105";

    private ArrayList<String> link_url_list =new ArrayList<String>();   //一级目录ｌｉｓｔ

    private ArrayList<String> data_url_list =new ArrayList<String>();   //二级目录ｌｉｓｔ

    private ArrayList<String> USDollarExchange=new ArrayList<String>();

    private ArrayList<String> HongKongDollarExchange=new ArrayList<String>();

    private ArrayList<String> EuropeDollarExchange=new ArrayList<String>();

    WritableWorkbook xls;

    private int url_num_perpage=20;   //一页有20条有效的url

    public static void  main(String[] args)
    {
        ExchangeRate exchange_rate=new ExchangeRate();
        exchange_rate.getUrlDataList(30);
        exchange_rate.parseData();
    }

    private void parseData()
    {
        try
        {
            WritableWorkbook xls= Workbook.createWorkbook(new File("ExchangeRate.xls"));
            WritableSheet sheet=xls.createSheet("FirstSheet",0);
            for (Iterator<String> ite= data_url_list.iterator();ite.hasNext();)
            {
                try
                {
                    WebClient client=new WebClient();
                    client.getOptions().setCssEnabled(true);
                    client.getOptions().setJavaScriptEnabled(true);
                    HtmlPage page=client.getPage(ite.next());
                    DomElement table=page.getElementById("zoom");
                    DomElement first_chiled=table.getFirstElementChild();
                    String data=first_chiled.getTextContent();
                    String[] paterns=data.split("，");
                    for (String tem:paterns)
                    {
                        int index=tem.lastIndexOf("人民币");
                        if(tem.contains("美元")) {
                            USDollarExchange.add(tem.substring(index+3,tem.length()));
                           // System.out.println(tem);
                        }
                        else if(tem.contains("欧元")) {
                            EuropeDollarExchange.add(tem.substring(index+3,tem.length()));
                           // System.out.println(tem);
                        }
                        else if(tem.contains("港元")) {
                            HongKongDollarExchange.add(tem.substring(index+3,tem.length()));
                            //System.out.println(tem);
                        }
                    }
                }
                catch(Exception e)
                {
                    System.out.println("error");
                    continue;
                }
            }
//            System.out.println("共："+USDollarExchange.size()+"条数据");
//            for (Iterator<String> ite=USDollarExchange.iterator();ite.hasNext();)
//            {
//                System.out.println(ite.next());
//            }
         //   String[] array=USDollarExchange.toArray(new String[0]);
        //    System.out.println("美元中间值是："+);
            Label label=new Label(0,0,"人民币对美元汇率");
            sheet.addCell(label);
            label=new Label(0,1,binarySearch(USDollarExchange.toArray(new String[0]),0,29,14));
            sheet.addCell(label);
            label=new Label(1,0,"人民币对港币汇率");
            sheet.addCell(label);
            label=new Label(1,1,binarySearch(HongKongDollarExchange.toArray(new String[0]),0,29,14));
            sheet.addCell(label);
            label=new Label(2,0,"人民币对欧元汇率");
            sheet.addCell(label);
            label=new Label(2,1,binarySearch(EuropeDollarExchange.toArray(new String[0]),0,29,14));
            sheet.addCell(label);
            xls.write();
            xls.close();
        }
        catch(Exception e)
        {
            System.out.println("error"+e.getMessage());
            System.exit(0);
        }
    }
    private void getUrlDataList(int data_url_num) //获取二级url.    data_url_num所需二级url数目.
    {
        getLinkUrlList(30);
        Parser parser=new Parser();
        WebClient webclient=new WebClient();
        webclient.getOptions().setJavaScriptEnabled(true);
        webclient.getOptions().setCssEnabled(true);
        for(Iterator<String> ite= link_url_list.iterator();ite.hasNext();)
        {
            try
            {
                String tt=ite.next();
                System.out.println("正在parser "+tt);
                parser.setEncoding("utf-8");
                HtmlPage page=webclient.getPage(tt);
                List<HtmlAnchor> anchors=page.getAnchors();
                int flag=0;
                for (Iterator<HtmlAnchor> iterator=anchors.iterator();iterator.hasNext();)
                {
                    String temp=iterator.next().getHrefAttribute();
                    if (temp.matches("/zhengcehuobisi/125207/125217/125925/[0-9]{7}/index.html"))
                    {
                        flag++;
                        if(1==flag)
                            continue;     //匹配到的第一个URL是无效的.
                        data_url_list.add("http://www.pbc.gov.cn" + temp);
                        System.out.println(temp);
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("在parsser fetchURL时发生错误:");
                System.exit(0);
            }

        }

    }
    private void getLinkUrlList(int data_url_num)  //获取一级url　　data_url_num所需二级url数目.
    {
        int url_fetch_num=0;
        if(data_url_num %26==0)
            url_fetch_num= data_url_num /this.url_num_perpage;
        else
            url_fetch_num= data_url_num /this.url_num_perpage+1;
        for (int i=1;i<url_fetch_num+1;i++)
        {
            link_url_list.add(url_home + "/index" + i + ".html");
        }
        for(Iterator<String> ite= link_url_list.iterator();ite.hasNext(); )
        {
            System.out.println(ite.next());
        }
    }
    String binarySearch(String[] list , int start , int tail , int destination)    //在ｌｉｓｔ中寻求排序位于ｄｅｓｔｉｎａｔｉｏｎ位置的值
    {
        System.out.println("running");
        int index_head=start;
        int index_tail=tail;
        String tem=list[start];
        while(index_head<index_tail)
        {
            for (;index_head<index_tail;index_tail--)
            {
                if(list[index_tail].compareTo(tem)<=0)
                {
                    list[index_head]=list[index_tail];
                    index_head++;
                    break;
                }
            }
            for(;index_head<index_tail;index_head++)
            {
                if(list[index_head].compareTo(tem)>=0)
                {
                    list[index_tail]=list[index_head];
                    index_tail--;
                    break;
                }
            }
        }
        list[index_head]=tem;
        if(index_head==destination)
            return list[index_head];
        else if(index_head<destination)
            return binarySearch(list,index_head+1,tail,destination);
        else
            return binarySearch(list,start,index_head-1,destination);
    }
//    public class LinkVister extends NodeVisitor
//    {
//        public LinkVister()
//        {
//            super();
//        }
//        public void visitTag(Tag tag)
//        {
//            if(tag instanceof LinkTag)
//            {
//                System.out.println(((LinkTag) tag).getLink());
//            }
//        }
//    }
}
