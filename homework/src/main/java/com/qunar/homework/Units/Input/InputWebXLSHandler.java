package com.qunar.homework.Units.Input;

import com.qunar.homework.Units.Interface.InputHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class InputWebXLSHandler implements InputHandler{
    String xlsFilePath = "D:\\java\\workspace\\homework\\src\\main\\resources\\huilv.xls";
    public List<File> getInputFileList(String name) {
        String url = prepareURL();
        File file = downloadXLS(url);
        List<File> listFile = new ArrayList<File>();
        listFile.add(file);
        return listFile;
    }
    private String prepareURL(){
        String preURL = "http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endDay = simpleDateFormat.format(date.getTime());
        String startDay = simpleDateFormat.format(new Date(date.getTime() - 29*24*60*60*1000L));
        String parm = "projectBean.startDate=" + startDay + "&projectBean.endDate=" + endDay;
        return preURL + parm;
    }

    private File downloadXLS(String url){
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        File file = null;
        try {
            URL urlObject = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)urlObject.openConnection();
            httpURLConnection.connect();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            file = new File(xlsFilePath);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            int n;
            byte[] data = new byte[4096];
            while ((n = bufferedInputStream.read(data)) != -1) {
                bufferedOutputStream.write(data, 0, n);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(bufferedInputStream!=null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedOutputStream!=null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

}
