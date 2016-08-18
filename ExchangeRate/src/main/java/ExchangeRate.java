import okhttp3.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KL on 2016/6/26.
 */
public class ExchangeRate {

    private static final String requestURL = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
    private List<String[]> data = new ArrayList<>();
    private final String excelPath = "xlstest.xls";
    private String startDate = "2016-06-01";
    private String endDate = "2016-06-26";


    public String getExchangeRate() {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("projectBean.startDate", startDate)
                .add("projectBean.endDate", endDate)
                .add("queryYN", "true")
                .build();
        Request request = new Request.Builder()
                .url(requestURL)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36")
                .header("Referer", "")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void parseHtml(String htmlContent) {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties properties = cleaner.getProperties();

        try {
            TagNode nodes = cleaner.clean(htmlContent);
            Object[] tables = nodes.evaluateXPath("//*[@id=\"InfoTable\"]/tbody");
            if (tables.length < 1) {
                System.out.println("parse error");
                return;
            }
            TagNode table = (TagNode) tables[0];
            List rows = table.getChildTagList();
            for(int i = 0; i < rows.size(); i++) {
                TagNode row = (TagNode) rows.get(i);

                List cols = row.getChildTagList();
                TagNode date = (TagNode) cols.get(0);
                TagNode dollar = (TagNode) cols.get(1);
                TagNode euro = (TagNode) cols.get(2);
                TagNode hkDollar = (TagNode) cols.get(4);

                data.add(new String[]{
                        StringEscapeUtils.unescapeHtml3((date.getText().toString().trim())),
                        StringEscapeUtils.unescapeHtml3((dollar.getText().toString().trim())),
                        StringEscapeUtils.unescapeHtml3((euro.getText().toString().trim())),
                        StringEscapeUtils.unescapeHtml3((hkDollar.getText().toString().trim())),
                });
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
    }

    public void writeExcel() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("中国银行汇率中间价");

        for (int i = 0; i < data.size(); i++) {
            String[] rowData = data.get(i);
            Row r = sheet.createRow(i);
            for (int j = 0; j < rowData.length; j++) {
                r.createCell(j).setCellValue(rowData[j]);
            }
        }

        try(FileOutputStream outputStream = new FileOutputStream(excelPath)) {  // Java7 try with resources
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String htmlContent = getExchangeRate();
        parseHtml(htmlContent);
        writeExcel();
    }

    public static void main(String[] args) {
        new ExchangeRate().run();
    }
}
