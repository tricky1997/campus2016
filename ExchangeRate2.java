/**
 * 二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，
 * 得到人民币对美元、欧元、港币的汇率，形成excel文件输 出。汇率数据找相关的数据源，
 * 自己爬数据分析。
 * Created by jianghp on 2016-06-15.
 * handle excel with apache poi tools
 *
 <dependency>
 <groupId>org.apache.poi</groupId>
 <artifactId>poi</artifactId>
 <version>3.12</version>
 </dependency>
 */

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExchangeRate2 {
    private static String classPath = ExchangeRate.class.getResource("/").getFile();
    public static void main(String[] args) {
        getExcel("temp.xls");
        handleExcel("temp.xls","ExchangeRate.xls");
        File file = new File(classPath+"temp.xls");
        file.delete();
    }

    public static void handleExcel(String srcFileName,String destFileName){

        try {
            InputStream inp = new FileInputStream(classPath+srcFileName);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            int rowStart = Math.min(15, sheet.getFirstRowNum());
            int rowEnd = Math.max(1400, sheet.getLastRowNum());
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row r = sheet.getRow(rowNum);
                if (r == null) {
                    continue;
                }
                int lastColumn = Math.max(r.getLastCellNum(), 4);
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                    if (cn==3){
                        Cell c2 = r.getCell(cn+1);
                        if (rowNum==0){
                            c.setCellValue(c2.getStringCellValue());
                        }else {
                            c.setCellValue(Double.parseDouble(c2.getStringCellValue()) / 100);
                        }
                    }else if (cn>3){
                        r.removeCell(c);
                    }else if (cn>0&&cn<3&&rowNum>0){
                        c.setCellValue(Double.parseDouble(c.getStringCellValue())/100);
                    }

                }
            }
            //Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(classPath+destFileName);
            wb.write(fileOut);
            fileOut.close();
        }catch (IOException e){
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * get a excel table of last 30 days' exchange rate
     * @param fileName , file name of the output excel file
     */
    public static void getExcel(String fileName){
        FileOutputStream out = null;
        URLConnection con = null;
        InputStream in = null;
        Calendar c = Calendar.getInstance();
        Date endDay= c.getTime();
        c.add(Calendar.DATE,-30);
        Date startDay = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //起始时间字符串 2015-11-03
        String startDayString = sdf.format(startDay);
        //结束时间字符串 2015-10-08
        String endDayString = sdf.format(endDay);
        //拼接URL，可以直接导出excel的网站
        String url="http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?projectBean.startDate="+
                startDayString+"&projectBean.endDate="+endDayString;
        try {
            //打开连接
            con = new URL(url).openConnection();
            //获取输入流
            in = (InputStream) con.getContent();
            //下面为输出文件
            out = new FileOutputStream(classPath+fileName);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))!=-1){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
