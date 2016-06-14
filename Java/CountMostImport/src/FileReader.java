import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/6/12.
 */
public class FileReader { //读取文件类

  public static  List<String> targetFileList; //读取文件结果列表

    //在指定路径下path递归读取指定后缀名endName的文件
    private static void readEndnameFileRec(String path,String endName) {
        File file = new File(path);

        if (!file.exists()) {
            return;
        }

        File[] fileList = file.listFiles();
        for (File fileTemp :fileList) {
            if (fileTemp.isFile()) {
                if (fileTemp.getName().toLowerCase().endsWith(endName)) {
                    targetFileList.add(fileTemp.getAbsolutePath());
                }
            }
            if(fileTemp.isDirectory()) {
                String pathTempPath = fileTemp.getAbsolutePath();
                readEndnameFileRec(pathTempPath, endName);
            }
        }
    }

    //在指定路径下path读取指定后缀名endName的文件，结果在targetFileList中
    public static boolean readEndnameFile(String path,String endName) {
        targetFileList = new ArrayList<String>();
        File file = new File(path);
        if (file.exists()) {
            readEndnameFileRec(path,endName);
            return true;
        } else {
            return false;
        }
    }

}
