import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by miaosen on 2016/6/5.
 */
public class PoiExcel {
    private static Logger logger = Logger.getLogger(PoiExcel.class);
    //创建Execl表并写入数据
    public static void newExcel(ArrayList<RateBean> arrayList) {
        String[] title = {"日期", "美元", "欧元", "港币"};
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(); //创建工作簿
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("人民币汇率中间价");//创建工作表
        HSSFRow firstRow = hssfSheet.createRow(0);//创建第一行
        HSSFCell hssfCell = null;
        //插入第一行数据
        for (int i = 0; i < title.length; i++) {
            hssfCell = firstRow.createCell(i);
            hssfCell.setCellValue(title[i]);
        }
        //追加数据
        for (int i = 1; i <= arrayList.size(); i++) {
            HSSFRow nextRow = hssfSheet.createRow(i);
            HSSFCell cell0 = nextRow.createCell(0);
            cell0.setCellValue(arrayList.get(i - 1).getDate());
            HSSFCell cell1 = nextRow.createCell(1);
            cell1.setCellValue(arrayList.get(i - 1).getDollar());
            HSSFCell cell2 = nextRow.createCell(2);
            cell2.setCellValue(arrayList.get(i - 1).getEru());
            HSSFCell cell3 = nextRow.createCell(3);
            cell3.setCellValue(arrayList.get(i - 1).getHkDollar());
        }


        File file = new File("e:/poi_text.xls"); //创建一个文件,即输出文件路径
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = FileUtils.openOutputStream(file);
            hssfWorkbook.write(fileOutputStream);//写入数据
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
