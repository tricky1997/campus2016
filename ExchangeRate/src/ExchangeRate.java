import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Wang on 2016/6/19.
 */
//添加了jxl.jar，httpclient类库,htmlparser类库,git上没添加
public class ExchangeRate {
    public static final String URL = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
    private String today;
    private String thirtyDaysBefore;
    private int dateColumn=0;
    //三种汇率在查询的网站的表格中的行数
    private int dollarColumn=0;
    private int euroColumn=0;
    private int HKDColumn=0;

    public static void main(String[] args) {
        ExchangeRate exchangeRate = new ExchangeRate();
        String html = exchangeRate.postFormToGetHtml();
        exchangeRate.parserHtml(html);
    }

    public String postFormToGetHtml() {
        String html = "";
        initDate();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
        formParams.add(new BasicNameValuePair("projectBean.startDate", thirtyDaysBefore));
        formParams.add(new BasicNameValuePair("projectBean.endDate", today));
        formParams.add(new BasicNameValuePair("queryYN", "true"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(uefEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try{
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    html = EntityUtils.toString(entity, "UTF-8");
                 //   System.out.println(html);
                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return html;
    }

    private void initDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        today = sf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        thirtyDaysBefore = sf.format(calendar.getTime());
    }

    private void parserHtml(final String html) {
        //[0-9]{4}//-[0-9]{2}//-[0-9]{2}
        assignThreeExchangeRateColumn(html);
        getAndWriteRatesToExcel(html);
    }

    //赋值3种汇率在表格中的列数给参数
    private void assignThreeExchangeRateColumn(String html) {
        Parser parser = null;
        try {
            parser = new Parser(html);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        NodeFilter filter = new HasAttributeFilter("id", "comtemplatename");
        NodeList nodes = null;
        try {
            nodes = parser.extractAllNodesThatMatch(filter);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        int i=0;
        for(Node node: nodes.toNodeArray()) {
            i++;
            String nodePlainText = node.toPlainTextString().trim();
            if(nodePlainText.startsWith("美元"))
                dollarColumn = i;
            else if(nodePlainText.startsWith("欧元"))
                euroColumn = i;
            else if(nodePlainText.startsWith("港元"))
                HKDColumn = i;
            else if(nodePlainText.startsWith("日期"))
                dateColumn = i;
        }
    }

    //取得并写入汇率到Excel
    private void getAndWriteRatesToExcel(String html) {
        WritableWorkbook book = null;
        WritableSheet sheet = null;
        try {
            book = Workbook.createWorkbook(new File("./ExchangeRate/汇率.xls"));
            sheet = book.createSheet("firstPage", 0);
            writeCellToExcel(0, 0, "日期", sheet);
            writeCellToExcel(0, 1, "美元", sheet);
            writeCellToExcel(0, 2, "欧元", sheet);
            writeCellToExcel(0, 3, "港元", sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Parser parser = new Parser(html);
            NodeFilter filter = new HasAttributeFilter("class", "first");
            NodeList nodes = parser.extractAllNodesThatMatch(filter);
            int row = 0;
            for(Node node : nodes.toNodeArray()) {
                row++;
                int column=0;
                int childrenColumn=0;
                for(Node nodeChildren: node.getChildren().toNodeArray()) {
                    String rate = nodeChildren.toPlainTextString().trim().replace("&nbsp;", "");
                    childrenColumn++;
                    if(childrenColumn == dateColumn * 2)
                        writeCellToExcel(row, column++, rate, sheet);
                    else if(childrenColumn == dollarColumn * 2)
                        writeCellToExcel(row, column++, rate, sheet);
                    else if(childrenColumn == euroColumn * 2)
                        writeCellToExcel(row, column++, rate, sheet);
                    else if(childrenColumn == HKDColumn * 2)
                        writeCellToExcel(row, column++, rate, sheet);
                }
            }
            book.write();
            System.out.println("汇率写入完成");
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                book.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

    //将数据提交到Excel单元格
    private void writeCellToExcel(int row, int column, String content, WritableSheet sheet) {
        Label label = new Label(column, row, content);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

}

