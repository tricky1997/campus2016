import com.sun.rowset.internal.Row;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by xuxingbo on 2016/6/20.
 * 使用Jsoup抓取网络数据
 * 使用jxl将数据写入到execl中
 */
public class ExchangeRate {
    private List<Record> resultList;

    public ExchangeRate() {
        resultList = new ArrayList<>();
    }

    public ExchangeRate(List<Record> resultList) {
        this.resultList = resultList;
    }

    private void analysisUrl(String url) {
        //该网页中的日期格式为yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        Calendar currentDate = Calendar.getInstance();
        //获取当前日期前30天的日期
        Calendar prevDate = Calendar.getInstance();
        prevDate.add(prevDate.DATE, -30);
        Record record = new Record("日期", "美元", "欧元", "港币");
        resultList.add(record);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .data("projectBean.startDate", sdf.format(prevDate.getTime()))
                    .data("projectBean.endDate", sdf.format(currentDate.getTime()))
                    .get();

            //分析网页格式后发现，数据均位于class="first"的th中
            Elements elements = doc.getElementsByClass("first");
            for (Element elem : elements) {
                /*
                分析结构后发现在html页面的每一行中，日期数据位于第一个，美元数据位于第二个
                欧元数据位于第三个，港币数据位于第五个
                */
                Record rec = new Record();
                String date = elem.child(0).text();
                String dollar = elem.child(1).text();
                String euro = elem.child(2).text();
                String hk = elem.child(4).text();
                rec.setDate(date);
                rec.setDollar(dollar);
                rec.setEuro(euro);
                rec.setHk(hk);
                resultList.add(rec);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void transformToExecl(String execlName) {
        if(resultList.isEmpty())
            return;
        File execlFile = new File(execlName);
        //如果文件存在就删除
        if (execlFile.exists())
            execlFile.delete();
        try {
            WritableWorkbook book = Workbook.createWorkbook(execlFile);
            WritableSheet sheet = book.createSheet("第一页", 0);
            //依次设置每一列的宽度为自适应
            CellView cellView = new CellView();
            cellView.setAutosize(true);
            for(int i=0;i<4;i++)
                sheet.setColumnView(i,cellView);
            sheet.setColumnView(1,cellView);

            //向sheet中写入数据，由于表头我们已经加入链表中，所以不需要单独考虑
            for (int j = 0; j < resultList.size(); j++) {
                Label label1 = new Label(0, j, resultList.get(j).getDate());
                Label label2 = new Label(1, j, resultList.get(j).getDollar());
                Label label3 = new Label(2, j, resultList.get(j).getEuro());
                Label label4 = new Label(3, j, resultList.get(j).getHk());
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
            }
            book.write();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startTransform(String url,String execlName){
        analysisUrl(url);
        transformToExecl(execlName);
    }
}
