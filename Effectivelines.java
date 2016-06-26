
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class effective {
    public static int normalLines = 0;  //有效程序行数

    public static void main(String[] args) throws IOException{

        File file = new File("D://test");
        if (file.exists()) {
            statistic(file);

        }
        else {
            System.out.println("no such file...");
            System.exit(-1);
        }
        System.out.println("java文件中有效代码行数: " + normalLines);

    }
    private static void statistic(File file) throws IOException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                statistic(files[i]);
            }
        }
        if (file.isFile()) {
            //统计扩展名为java的文件
            if(file.getName().matches(".*\\.java")){
                parse(file);
            }
        }
    }
    public static void parse(File file) {
        BufferedReader br = null;
        // 判断此行是否为注释行
        boolean comment = false;


        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches("^[//s&&[^//n]]*$")) {
                    // 空行
                }
                else if (line.startsWith("//")) {
                    // 单行注释行

                }
                else{
                    // 有效代码行
                    normalLines++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
