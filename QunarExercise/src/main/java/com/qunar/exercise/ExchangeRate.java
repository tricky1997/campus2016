package com.qunar.exercise;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangxueling on 16/6/8.
 *
 */
public class ExchangeRate {
    public static void main(String[] args) {
        // 创建一个日历类用于获取日期
        Calendar calendar = Calendar.getInstance();
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime() ;
        String endDate = sdf.format(date);
        calendar.add(calendar.DATE,-30);
        date = calendar.getTime();
        String startDate = sdf.format(date) ;

        ExchangeRate exchangeRate = new ExchangeRate();
        Map<String , List<String>> results = exchangeRate.parserHtmlTable(startDate , endDate);
        exchangeRate.writeExcel(results);
    }

    /**
     * 通过第三方库Htmlpaser解析HTML中表格的数据,数据来源于国家外汇管理局http://www.safe.gov.cn/wps/portal/sy/tjsj_hlzjj_inquire
     * @param startDateStr 起始时间
     * @param endDateStr 结束时间
     * @return 存放解析出来的数据
     */
    public Map<String, List<String>> parserHtmlTable(String startDateStr , String endDateStr){
        // 查询数据的URL
        String urlPath = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?"+"projectBean.startDate="
                + startDateStr + "&projectBean.endDate=" + endDateStr;
        URL url = null;
        // 保存提取到的结果
        Map<String , List<String>> results = new LinkedHashMap<String, List<String>>();
        try {
            System.out.println("开始获取汇率数据......");
            url = new URL(urlPath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // parser类的初始化
            Parser parser = new Parser(con);
            NodeList tableList = null;
            NodeFilter tableFilter = null;
            tableFilter = new NodeClassFilter(TableTag.class);
            NodeFilter filterID = new HasAttributeFilter("id","InfoTable") ;
            NodeFilter filter = new AndFilter(filterID , tableFilter) ;
            tableList = parser.extractAllNodesThatMatch(filter);
            //System.out.println(tableList.size());

            //NodeList filteredTl = tableList.extractAllNodesThatMatch(filterID) ;
            //System.out.println(filteredTl.size());
            TableTag table = (TableTag) tableList.elementAt(0);
            //取得表中的行集
            TableRow[] rows = table.getRows();
            // 从第一行得到表头的内容,得到美元,欧元,港币所在的列
            int col_USD = 0, col_HK = 0 , col_EUR = 0 ;
            TableRow th = rows[0] ;
            TableHeader[] headers = th.getHeaders();
            for (int j = 0; j < headers.length; j++) {
                if(headers[j].getStringText().trim().contains("美元")){
                    col_USD = j ;
                }
                if(headers[j].getStringText().trim().contains("欧元")){
                    col_EUR = j ;
                }
                if(headers[j].getStringText().trim().contains("港元")){
                    col_HK = j ;
                }
            }
            //遍历每行
            for (int r = 1; r < rows.length; r++) {
                TableRow tr = rows[r];
                //行中的列
                TableColumn[] td = tr.getColumns();
                List<String> value = new ArrayList<String>() ;
                String Date_Str = td[0].getStringText().trim().replaceAll("&nbsp;","");
                String USD_str = td[col_USD].getStringText().trim().replaceAll("&nbsp;","");
                String EUR_str = td[col_EUR].getStringText().trim().replaceAll("&nbsp;","");
                String HK_str = td[col_HK].getStringText().trim().replaceAll("&nbsp;","");
                value.add(USD_str) ;
                value.add(EUR_str) ;
                value.add(HK_str) ;
                results.put(Date_Str , value) ;
            }
            System.out.println("完成汇率数据的获取");
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results ;
    }

    /**
     * 将解析出来的数据写入Excel文件中
     * @param results
     */
    public void writeExcel(Map<String , List<String>> results){
        WritableWorkbook workbook = null ;
        System.out.println("开始将数据写入Excel......");
        try {
            // 创建工作薄
            workbook = Workbook.createWorkbook(new File("src/main/resources/exchangeRate.xls"));
            // 创建新的一页
            WritableSheet sheet = workbook.createSheet("fisrt Sheet" , 0) ;
            // 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
            Label element = new Label(0,0,"日期") ;
            sheet.addCell(element);
            element = new Label(1,0,"美元") ;
            sheet.addCell(element);
            element = new Label(2,0,"欧元") ;
            sheet.addCell(element);
            element = new Label(3,0,"港币") ;
            sheet.addCell(element);

            // 遍历Map
            Iterator iter = results.entrySet().iterator() ;
            int row_index = 0 ;
            while(iter.hasNext()){
                row_index++ ;
                Map.Entry entry = (Map.Entry) iter.next();
                String date_str = (String) entry.getKey();
                element = new Label(0,row_index , date_str) ;
                sheet.addCell(element);
                List<String> value = (List<String>) entry.getValue();
                element = new Label(1,row_index , value.get(0)) ;
                sheet.addCell(element);
                element = new Label(2,row_index , value.get(1)) ;
                sheet.addCell(element);
                element = new Label(3,row_index , value.get(2)) ;
                sheet.addCell(element);
            }
            workbook.write();
            System.out.println("写入数据完成");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }finally {
            try {
                if(workbook != null)
                    workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }
}
