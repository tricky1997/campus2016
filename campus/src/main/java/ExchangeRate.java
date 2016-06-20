import com.google.common.collect.Lists;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 中间价是从国家外汇管理局：http://www.safe.gov.cn/wps/portal/sy/tjsj_hlzjj_inquire获取
 * Created by andrew on 2016/6/19.
 */
public class ExchangeRate {
    private String todayStr;
    private String lastMonthDayStr;

    private List<? extends Header> headerList;          //Http请求头部
    private CookieStore cookieStore;                    //  cookie

    private List<Monetary> monetaryList = Lists.newArrayList();     //存储30天中间价
    private List<String>  headerInfo = Lists.newArrayList("日期", "美元", "欧元", "港币");      //excel header信息

    {
        headerList = Lists.newArrayList(new BasicHeader("Referer","http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action"),
                new BasicHeader("Upgrade-Insecure-Requests", "1"));
        cookieStore = new BasicCookieStore();
        cookieStore.addCookie(new BasicClientCookie("JSESSIONID", "0000GVLqCqnAMZKPQAfDZR32rJH:15rir7bv2"));
        cookieStore.addCookie(new BasicClientCookie("JSESSIONID2", "0000mxaMby_jWt8P7mTj59eDShL:168ptcb4l; _gscu_507870342=66342669w3h1do12; _gscs_507870342=66389478qkv22d11|pv:1; _gscbrs_507870342=1"));
    }



    private void init(){
        DateFormat fmtDataFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        todayStr = fmtDataFormat.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        lastMonthDayStr = fmtDataFormat.format(calendar.getTime());
    }

    private void acquireMiddlePrice(){
        CloseableHttpClient httpClient = HttpClients.custom().
                setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36").
                setDefaultHeaders(headerList).setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost httpPost = new HttpPost("http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action");
            List<NameValuePair> paramsList = Lists.newArrayList();
            paramsList.add(new BasicNameValuePair("projectBean.startDate", lastMonthDayStr));
            paramsList.add(new BasicNameValuePair("projectBean.endDate", todayStr));
            paramsList.add(new BasicNameValuePair("queryYN", "true"));
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramsList, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            try {
                Workbook workbook = new HSSFWorkbook(httpResponse.getEntity().getContent());
                Sheet sheet = workbook.getSheetAt(0);
                int rownum = sheet.getLastRowNum();
                for (int i = 1; i <= rownum; i++){
                    Row row = sheet.getRow(i);
                    Monetary monetary = new Monetary();
                    int j = row.getFirstCellNum();
                    Cell date = row.getCell(j);
                    monetary.setDateStr(date.getStringCellValue());
                    Cell doller = row.getCell(j+1);
                    monetary.setDoller(Double.parseDouble(doller.getStringCellValue()));
                    Cell euro = row.getCell(j+2);
                    monetary.setEuro(Double.parseDouble(euro.getStringCellValue()));
                    Cell hkd = row.getCell(j+4);
                    monetary.setHkd(Double.parseDouble(hkd.getStringCellValue()));
                    monetaryList.add(monetary);
                }
            }finally {
                httpResponse.close();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double acquireExchangeRate(double middlePrice){
        return middlePrice/100;
    }

    private void write2Excel(String pathname) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        CellStyle cellStyle = getHeaderCellStyle(workbook);
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);

        for (int i = 0; i < headerInfo.size(); i++){
            Cell cell = row.createCell(i);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerInfo.get(i));
        }

        for (int i = 0; i < monetaryList.size(); i++){
            Row rowData = sheet.createRow(i+1);
            rowData.setHeight((short)300);
            Monetary monetary = monetaryList.get(i);
            writeMonetary2Row(rowData, monetary);
        }
        sheet.autoSizeColumn((short)0);

        File file = new File(pathname);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.flush();
        workbook.write(outputStream);
        outputStream.close();
    }

    private void writeMonetary2Row(Row row, Monetary monetary){
        Cell dateCell = row.createCell(0);
        dateCell.setCellType(Cell.CELL_TYPE_STRING);
        dateCell.setCellValue(monetary.dateStr);

        Cell doller = row.createCell(1);
        doller.setCellType(Cell.CELL_TYPE_NUMERIC);
        doller.setCellValue(acquireExchangeRate(monetary.doller));

        Cell euro = row.createCell(2);
        euro.setCellType(Cell.CELL_TYPE_NUMERIC);
        euro.setCellValue(acquireExchangeRate(monetary.euro));

        Cell hkd = row.createCell(3);
        hkd.setCellType(Cell.CELL_TYPE_NUMERIC);
        hkd.setCellValue(acquireExchangeRate(monetary.hkd));
    }

    private CellStyle getHeaderCellStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 10);  //字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);            //加粗
        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);     //让单元格居中
        style.setWrapText(true);            //自动换行
        style.setFont(font);
        return style;
    }

    public void acquireLastMonthExchangeRate(String filePath) throws IOException {
        init();
        acquireMiddlePrice();
        write2Excel(filePath);
    }

    public static class Monetary{
        private String dateStr;
        private double doller;  //美元
        private double euro;    //欧元
        private double hkd;     //港币

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public double getDoller() {
            return doller;
        }

        public void setDoller(double doller) {
            this.doller = doller;
        }

        public double getEuro() {
            return euro;
        }

        public void setEuro(double euro) {
            this.euro = euro;
        }

        public double getHkd() {
            return hkd;
        }

        public void setHkd(double hkd) {
            this.hkd = hkd;
        }

        @Override
        public String toString() {
            return "Monetary{" +
                    "dateStr='" + dateStr + '\'' +
                    ", doller=" + doller +
                    ", euro=" + euro +
                    ", hkd=" + hkd +
                    '}';
        }
    }
/*    public static void main(String[] args) {
        *//*DateFormat fmtDataFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 0);
        System.out.println(fmtDataFormat.format(calendar.getTime()));*//*
        ExchangeRate exchangeRate = new ExchangeRate();
        try {
            exchangeRate.acquireLastMonthExchangeRate("rate.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
