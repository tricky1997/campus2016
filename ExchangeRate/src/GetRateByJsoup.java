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
 * 采用Jsoup解析包来提取人民币汇率中间价信息
 *
 * @author  Andrew Qiu
 * @date    2016-6-18.
 */
public class GetRateByJsoup {
    // 信息列表对象
    private static Element table;
    // 汇率中间价（对应美元、欧元、港币）
    private static List<ExchangeRate> rateList = new ArrayList<>();

    /**
     * 主函数
     */
    public static void main(String[] args) {

        /* 通过Jsoup解析器获取网络资源 */
        try {
            getDocmentObj();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 从Html中提取汇率信息*/
        analyzeDocument();

        /* 写入将人民币汇率信息 */
        Path path = Paths.get("exchange_rate.xlsx").toAbsolutePath();
        if (writeToExcel(path))
            System.out.println("汇率信息已成功写入Excel文件 ^o^");
        else
            System.out.println("汇率信息获取失败！");
    }

    /**
     * 提交Post请求，将Html页面转换为Document对象处理
     * @throws IOException
     */
    public static void getDocmentObj() throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 当前时间
        Calendar curDate = Calendar.getInstance();
        // 前面30天
        Calendar preDate = Calendar.getInstance();
        preDate.add(Calendar.DAY_OF_MONTH, -30);

        // 获取DOM对象
        Document doc = (Document) Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action")
                .data("projectBean.endDate", sdf.format(curDate.getTime()))
                .data("projectBean.startDate", sdf.format(preDate.getTime()))
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
                .cookie("auth", "token").timeout(30*1000).post();

        // 如果获取成功，则提取主干信息
        if (null != doc && doc.childNodeSize() > 0)
            table = doc.getElementById("InfoTable").child(0);
    }

    /**
     * 解析Document对象，提取汇率信息
     */
    public static void analyzeDocument() {

        // 防止NullPointerException异常
        if (null == table)
            return ;

        // 由于第一行<tr>为属性描述，所以从第二个<tr>节点开始遍历
        Element elem = table.child(1);
        while (elem != table.lastElementSibling()) {
            // 美元、欧元、港币的每日汇率
            String[] rateForEachDay = new String[]{
                    elem.child(1).text().replace("&nbsp; ", ""),
                    elem.child(2).text().replace("&nbsp; ", ""),
                    elem.child(4).text().replace("&nbsp; ", "")
            };
            // 添加到列表中
            String date = elem.child(0).text().replace("&nbsp; ", "");
            rateList.add(new ExchangeRate(date, rateForEachDay));
            // 下一个节点
            elem = elem.nextElementSibling();
        }
    }

    /**
     * 将汇率信息写入指定的Excel文件
     *
     * @param path  Excel文件路径
     * @return  返回是否写入成功
     */
    private static boolean writeToExcel(Path path) {

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
        Sheet sheet = wb.getSheet("ExchangeRate");
        if (null == sheet)
            sheet = wb.createSheet("ExchangeRate");

        /* 在Excel首行写入列名 */
        Row row = sheet.createRow(0);
        writeRow(row, new String[]{
                "日期", "美元", "欧元", "港币"
        });
        /* 循环写入数据 */
        List<String> list;
        for (int i = 0; i < rateList.size(); ++i) {
            // 创建新行
            row = sheet.createRow(i+1);
            // 获取某天的汇率数据
            ExchangeRate rate = rateList.get(i);
            // 合并字符串数组
            String[] cells = new String[rate.getRates().length + 1];
            cells[0] = rate.getDate();
            System.arraycopy(rate.getRates(), 0, cells, 1, rate.getRates().length);
            // 写入一行数据
            writeRow(row, cells);
        }

        /* 将数据写入文件中 */
        try (OutputStream os = Files.newOutputStream(path)) {
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 写入一行数据
     *
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
