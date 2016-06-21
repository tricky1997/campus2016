import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//1.并未从中国人民银行上爬数据。。。待修改

public class ExchangeRate {
    private ArrayList<String[]> s;

    private void read(){
        s=new ArrayList<>();
        s.add(new String[]{"DATE","USD","EUR","HKD"});
        String url="http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String start,end;
        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        end=dateFormat.format(date);
        calendar.add(Calendar.DATE,-30);
        date=calendar.getTime();
        start=dateFormat.format(date);
        System.out.println(start+" "+end);

        try {
            Document doc=Jsoup.connect(url).data("projectBean.startDate",start).data("projectBean.endDate",end).get();
            for (Element element : doc.getElementsByAttributeValue("class","first")) {
                String[] split=element.text().split(" ");
                s.add(new String[]{split[0],split[1],split[2],split[4]});

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String[] strings : s) {
            System.out.println("DATE:"+strings[0]+" USD:"+strings[1]+" EUR:"+strings[2]+"HKD:"+strings[3]);
        }
    }
    private void write(){
        try {
            WritableWorkbook writableWorkbook=Workbook.createWorkbook(new File("rate.xls"));
            WritableSheet sheet=writableWorkbook.createSheet("sheet1",0);
            for (int i = 0; i < s.size(); i++) {
                String[] strings=s.get(i);
                for (int j = 0; j < strings.length; j++) {
                    Label label=new Label(j,i,strings[j]);
                    sheet.addCell(label);
                }
            }
            writableWorkbook.write();
            writableWorkbook.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
    public void exchangeRate(){
        read();
        write();
    }
    public static void main(String args[]){
        ExchangeRate exchangeRate=new ExchangeRate();
        exchangeRate.exchangeRate();
    }

}
