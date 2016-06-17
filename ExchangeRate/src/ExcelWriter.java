import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Yecheng Li on 2016/6/15 0015.
 * 此类用于将数据写入Excel文件之中
 */
public class ExcelWriter implements Runnable {
    //该队列用于存放前一个流程过滤出的汇率及对应日期
    private BlockingQueue<String> exchangeRateQueue;
    //工作表句柄
    private WritableWorkbook writableWorkbook;
    //sheet句柄
    private WritableSheet writableSheet;
    //读取最近几天的汇率
    private int recentNDays;

    /**
     * 有参构造函数
     * @param exchangeRateQueue
     * 汇率中间价的队列，用于从其中取出汇率及时间
     * @param filePath
     * 存放的文件路径
     * @param recentNDays
     * 读取最近几天的汇率
     * @throws IOException
     * 抛出IOException
     */
    public ExcelWriter(BlockingQueue<String> exchangeRateQueue,String filePath,int recentNDays) throws IOException {
        File file=new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        writableWorkbook= Workbook.createWorkbook(file);
        writableSheet=writableWorkbook.createSheet("First Sheet",0);
        this.exchangeRateQueue=exchangeRateQueue;
        this.recentNDays=recentNDays;
    }

    /**
     * 重写Runnable接口的方法
     */
    @Override
    public void run() {
        //用于计数
        int i=0;
        //写入文件头
        Label title=new Label(0,0,"日期");
        Label RMBToDollar=new Label(1,0,"人民币对美元");
        Label RMBToEuro=new Label(2,0,"人民币对欧元");
        Label RMBToHKD=new Label(3,0,"人民币对港币");
        try {
            writableSheet.addCell(title);
            writableSheet.addCell(RMBToDollar);
            writableSheet.addCell(RMBToEuro);
            writableSheet.addCell(RMBToHKD);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        //取出前N天的汇率并写入文件中
        while(i<recentNDays){
            try {
                String exchangeRateItem=exchangeRateQueue.take();
                System.out.println(exchangeRateItem);
                String[] split=exchangeRateItem.split("&");
                System.out.println("...1:"+split[0]+"...2:"+split[1]+"...3:"+split[2]);
                Label time=new Label(0,i+1,""+split[0]+"."+split[1]+"."+split[2]);
                Label rToDollar=new Label(1,i+1,split[3]);
                Label rToEuro=new Label(2,i+1,split[4]);
                Label rToHKD=new Label(3,i+1,split[5]);
                try {
                    writableSheet.addCell(time);
                    writableSheet.addCell(rToDollar);
                    writableSheet.addCell(rToEuro);
                    writableSheet.addCell(rToHKD);
                }catch (WriteException e){
                    e.printStackTrace();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        //回写并关闭文件
        try {
            writableWorkbook.write();
            writableWorkbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (WriteException e){
            e.printStackTrace();
        }
    }
}
