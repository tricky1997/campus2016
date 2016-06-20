package com.qunar.homework.Units.File;

import com.qunar.homework.Units.Interface.FileHandler;
import com.qunar.homework.Units.Interface.WorkHandler;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class XLSParser implements FileHandler{
    String xlsResultPath = "D:\\java\\workspace\\homework\\src\\main\\resources\\result.xls";
    public Object getWorkDone(List<File> fileList, WorkHandler workHandler) throws IOException {
        File inputFile = fileList.get(0);
        File outputFile = new File(xlsResultPath);

        Workbook workBookInput = null;
        WritableWorkbook workBookOutput = null;
        try{
            workBookInput = Workbook.getWorkbook(inputFile);
            workBookOutput = Workbook.createWorkbook(outputFile);
            Sheet sheetInput = workBookInput.getSheet(0);

            int numRows = sheetInput.getRows();
            WritableSheet sheetOutput = workBookOutput.createSheet("第一页", 0);

            List<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(1);
            list.add(2);
            list.add(4);

            for(int i=0;i<numRows;i++){
                int k = 0;
                for(int j:list){
                    addCell(sheetOutput,k,i,
                            getCellContext(sheetInput,j,i));
                    k++;
                }
            }
            workBookOutput.write();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(workBookInput!=null){
                workBookInput.close();
            }
            if(workBookOutput!=null){
                try {
                    workBookOutput.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private String getCellContext(Sheet sheet,int row,int col){
        Cell cell = sheet.getCell(row,col);
        return cell.getContents();
    }
    private void addCell(WritableSheet sheet,int row,int col,String context){
        Label label = new Label(row,col,context);
        try {
            sheet.addCell(label);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
