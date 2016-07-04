
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




/**
 * Created by kuangchunyan on 2016/7/3.
 * 分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，
 * 得到人民币对美元、欧元、港币的汇率，形成excel文件输出
 */
public class GrabRateByJsoup {
    // 信息列表对象
    public Element table;
    // 汇率中间价（对应美元、欧元、港币）
    public List<ExchangeRateEntity> rateList = new ArrayList<>();

    /**
     * 主函数
     */
    public static void main(String[] args) {

        GrabRateByJsoup GRBY = new GrabRateByJsoup();
        /* 通过Jsoup解析器获取网络资源 */
        try {
            GRBY.getDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* 从Html中提取汇率信息*/
        GRBY.analyzeDocument();
        /* 写入将人民币汇率信息 */
        Path path = Paths.get("F:/campus2016/ExchangeRate/exchangeRate.xlsx").toAbsolutePath();
        if (GRBY.writeToExcel(path))
            System.out.println("汇率信息已成功写入Excel文件.");
        else
            System.out.println("汇率信息获取失败！");
    }

    /**
     * 提交请求，将Html页面转换为Document对象处理
     * @throws IOException
     */
    public  void getDocument() throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 得到当前时间
        Calendar curDate = Calendar.getInstance();
        // 向前推30天
        Calendar preDate = Calendar.getInstance();
        preDate.add(Calendar.DAY_OF_MONTH, -30);

        // 获取DOM对象
        Document doc = (Document) Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action")
                .data("projectBean.endDate", sdf.format(curDate.getTime()))//请求参数
                .data("projectBean.startDate", sdf.format(preDate.getTime()))
                .userAgent("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36")
                .cookie("auth", "token")
                .timeout(100000)//设置超时时间
                .post();

        // 如果获取成功，则提取主干信息
        if (null != doc && doc.childNodeSize() > 0)
            table = doc.getElementById("InfoTable").child(0);//通过索引得到元素的子元素
    }

    /**
     * 解析Document对象，提取相应的汇率信息
     */
    public  void analyzeDocument() {

        // 防止NullPointerException异常
        if (null == table)
            return ;

        Element elem = table.child(1);
        while (elem != table.lastElementSibling()) {
            // 获取美元、欧元、港币的每日汇率
            String[] rateForEachDay = new String[]{
                    elem.child(1).text().replace("&nbsp; ", ""),
                    elem.child(2).text().replace("&nbsp; ", ""),
                    elem.child(4).text().replace("&nbsp; ", "")
            };
            // 将日期和汇率添加到列表中
            String date = elem.child(0).text().replace("&nbsp; ", "");
            rateList.add(new ExchangeRateEntity(date, rateForEachDay));
            // 转换到下一个节点
            elem = elem.nextElementSibling();//获取下一个
        }
    }

    /**
     * 将解析出来的汇率信息写入指定的Excel文件
     * @param path  Excel文件路径
     * @return  返回是否写入成功
     */
    private  boolean writeToExcel(Path path) {

        /* 如果汇率列表为空，则表示获取信息失败 */
        if (rateList.isEmpty())
            return false;

        /* 如果Excel文件存在，则删除掉 */
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建XSSFWork(for .xslx)对象
        Workbook wb = new XSSFWorkbook();
        // 如果不存在指定的Sheet，则主动创建
        Sheet sheet = wb.getSheet("ExchangeRateEntity");
        if (null == sheet)
            sheet = wb.createSheet("ExchangeRateEntity");

        /* 在Excel首行写入列名 */
        /*Row row = sheet.createRow(0);
        String[] oneCell = {"日期", "美元", "欧元", "港币"};
        // 单元格对象
        Cell cell = null;

        for (int i = 0; i < oneCell.length; ++i) {
            // 新建单元格
            cell = row.createCell(i, Cell.CELL_TYPE_STRING);
            // 赋值单元格
            cell.setCellValue(oneCell[i]);
        }*/
        /* 在Excel首行写入列名 */
        Row row = sheet.createRow(0);
        writeRow(row, new String[]{
                "日期", "美元", "欧元", "港币"
        });

        /* 循环写入数据 */

        for (int i = 0; i < rateList.size(); i++) {
            // 创建新行
            row = sheet.createRow(i+1);
            // 获取某天的汇率数据
            ExchangeRateEntity rate = rateList.get(i);
            // 合并字符串数组
            String[] cells = new String[rate.getRates().length + 1];
            cells[0] = rate.getDate();
            System.arraycopy(rate.getRates(), 0, cells, 1, rate.getRates().length);
            // 写入一行数据
            writeRow(row, cells);

            /*for (int j = 0; j < cells.length; j++) {
                // 新建单元格
                cell = row.createCell(j, Cell.CELL_TYPE_STRING);
                // 赋值单元格
                cell.setCellValue(cells[j]);
            }*/
        }

        /* 将数据写入文件中 */
        OutputStream br=null;

        try {
            br = Files.newOutputStream(path);
            wb.write(br);
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(br!=null){
                    br.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 写入一行数据
     * @param row   待写入数据的Row对象
     * @param cells 单元格数据
     */
    private static void writeRow(Row row, String[] cells) {

        // 单元格对象
        Cell cell;
        /**/
        for (int i = 0; i < cells.length; ++i) {
            // 新建单元格
            cell = row.createCell(i, Cell.CELL_TYPE_STRING);
            // 赋值单元格
            cell.setCellValue(cells[i]);
        }
    }

}
