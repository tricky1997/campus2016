package com.fadying.qunar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 * <p>
 * 货币代码
 * CNY ¥ 人民币
 * USD $ 美元
 * EUR € 欧元
 * HKD HK$ 港币
 * <p>
 * 人民银行授权中国外汇交易中心于每个工作日上午9时15分对外公布当日人民币对美元、欧元、日元和港币汇率中间价。
 * <p>
 * 中国货币网（中国外汇交易中心）http://www.chinamoney.com.cn/index.html 接口申请联系方式 4009787878-2-5
 * 国际外汇管理局统计数据列表 http://www.safe.gov.cn/wps/portal/sy/tjsj_hlzjj_inquire 数据来自中国外汇交易中心
 *   查询请求 http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action
 *   excel请求 http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action
 *   参数 projectBean.startDate=2016-05-06&projectBean.endDate=2016-06-06&queryYN=true
 * 聚合网汇率数据api https://www.juhe.cn/docs/api/id/23 或80 免费 数据来源聚合数据、360搜索
 * <p>
 * 使用jsoup解析html, joda-time处理日期数据,poi写excel文件
 *
 * Created by 欧阳晟 on 2016/6/5.
 */
public class ExchangeRate {
    static Logger log = LoggerFactory.getLogger(ExchangeRate.class);

    public static final String QUERY_URL_FORMAT = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate=%s&projectBean.endDate=%s&queryYN=true";
    public static final String OUTPUT_EXCEL_PATH = "exchange_rate.xls";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final List<String> QUERY_EXCHANGE = Arrays.asList( "美元", "欧元", "港元" );
    public static final int[] EXCHANGE_POSITION = new int[]{ 1, 2, 4 };

    public static void main(String[] args) {

        DateTime endDate = DateTime.now();
        DateTime startDate = endDate.minusDays(30);

        String query = String.format(QUERY_URL_FORMAT, startDate.toString(DATE_FORMAT), endDate.toString(DATE_FORMAT));
        log.debug("Query: " + query);

        List<ExchangeRateData> dataList = null;
        try {
            dataList = getExchangeRateDataList(query);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("read data error", e);
        }

        try {
            saveExchangeRateToExcel(dataList, OUTPUT_EXCEL_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("save file error", e);
        }

    }

    /**
     * 从国际外汇管理局网站的汇率统计页面读取多天的汇率数据,保存到 ExchangeRateData 的列表中,ExchangeRateData 中的汇率数据
     * 使用 Map 格式存储
     *
     * @param query 包含查询时间参数的数据请求地址
     * @return ExchangeRateData 列表
     * @throws IOException
     */
    public static List<ExchangeRateData> getExchangeRateDataList(String query) throws IOException {
        Document doc = Jsoup.connect(query).get();
        Element table = doc.getElementById("InfoTable");

        List<ExchangeRateData> data = new ArrayList<ExchangeRateData>();
        for (Element row : table.select("tr")) {
            if (row.select("td").size() == 0)
                continue;

            Element dateElement = row.select("td").get(0);
            String trim = removeLastBlank(dateElement.text());
            ExchangeRateData exchange = new ExchangeRateData(DateTime.parse(trim).toDate());
            data.add(exchange);

            for (int i = 0; i < QUERY_EXCHANGE.size(); i++) {
                String text = row.select("td").get(EXCHANGE_POSITION[i]).text();
                Double rate = Double.parseDouble(removeLastBlank(text));
                exchange.getExchangeRate().put(QUERY_EXCHANGE.get(i), rate);
            }

        }
        log.debug("Data: " + data);
        return data;
    }

    /**
     * 把 ExchangeRateData 列表数据保存到 excel 文件
     * @param dataList
     * @param excelPath
     * @throws IOException
     */
    public static void saveExchangeRateToExcel(List<ExchangeRateData> dataList, String excelPath) throws IOException {
        HSSFWorkbook excelWorkbook = new HSSFWorkbook();
        Sheet sheet = excelWorkbook.createSheet("exchange rate");

        Row titleRow = sheet.createRow(0);      // 首行标题行
        titleRow.createCell(0).setCellValue("日期");
        for (int i = 0; i < QUERY_EXCHANGE.size(); i++) {
            titleRow.createCell(i + 1).setCellValue(QUERY_EXCHANGE.get(i));
        }

        sheet.setColumnWidth(0, 12 * 256);      // 日期列宽度12个字符
        CellStyle dateStyle = excelWorkbook.createCellStyle();  // 日期单元格格式
        dateStyle.setDataFormat(excelWorkbook.createDataFormat().getFormat(DATE_FORMAT));
        int size = dataList == null ? 0 : dataList.size();      // 获取数据失败时
        for (int i = 0; i < size; i++) {
            Row dataRow = sheet.createRow(i + 1);
            ExchangeRateData data = dataList.get(i);
            Cell dateCell = dataRow.createCell(0);
            dateCell.setCellStyle(dateStyle);
            dateCell.setCellValue(data.getDate());

            for (int j = 0; j < QUERY_EXCHANGE.size(); j++) {   // 按照标题顺序填入数据
                String exchange = QUERY_EXCHANGE.get(j);
                dataRow.createCell(j + 1).setCellValue(data.getExchangeRate().get(exchange));
            }
        }
        FileOutputStream excelOutPutStream = new FileOutputStream(excelPath);
        excelWorkbook.write(excelOutPutStream);
    }

    /**
     * remove &nbsp;
     * @param s
      */
    private static String removeLastBlank(String s) {
        return  (s.endsWith("" + (char) 160)) ? s.substring(0, s.length()-1) : s;
    }

    /**
     * 获取目标汇率所在列
     * @param doc
     */
    public static void getExchangePosition(Document doc) {
        Element table = doc.getElementById("InfoTable");

        Elements thead = table.select("th");
        for (int i = 0; i < thead.size(); i++) {
            String exchange = thead.get(i).text();
            exchange = exchange.substring(0, exchange.length()-1);        // remove &nbsp;
            int index = QUERY_EXCHANGE.indexOf(exchange);
            if (index >= 0 )
                EXCHANGE_POSITION[index] = i;
        }
    }


}

class ExchangeRateData {
    private Date date;
    private Map<String, Double> exchangeRate;

    public ExchangeRateData(Date date) {
        this.date = date;
        this.exchangeRate = new HashMap<String, Double>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, Double> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Map<String, Double> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public String
    toString() {
        return "ExchangeRateData{" +
                "date=" + date +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
