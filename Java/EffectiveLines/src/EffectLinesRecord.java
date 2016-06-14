import java.io.*;

/**
 * Created by TOSHIBA on 2016/6/12.
 */

public class EffectLinesRecord {

    //识别空行（仅含空白符的情况也视为空行）
    public static boolean isEmptyLine(String line){
        return line.matches("^[\\s&&[^\\n]]*$");
    }

    //识别注释行（仅含注释内容，注释前可能存在空白符）
    public static boolean isCommentLine(String line) {
        return line.matches("^[\\s&&[^\\n]]*//.*?$") || line.matches("^[\\s&&[^\\n]]*/\\*.*?\\*/$");
    }

    //扫描路径为filePath的Java文件统计有效行数
    public static int scanFileForEffectLines(String filePath) {
        if (!filePath.toLowerCase().endsWith(".java")) {
            System.out.println("非Java文件！");
            return 0;
        }
        File file = new File(filePath);
        BufferedReader bf = null;
        int effectLines = 0;
        try {
            bf = new BufferedReader(new FileReader(file));
            String lineStr = null;
            while ((lineStr = bf.readLine()) != null) {
                if (!isEmptyLine(lineStr) && !isCommentLine(lineStr)) {
                    effectLines++;
                    //System.out.println(lineStr);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件没有找到");
        } catch (IOException ee) {
            System.out.println("输入输出异常　");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                    bf = null;
                } catch (Exception e) {
                    System.out.println("关闭BufferReader时出错");
                }
            }
        }
        System.out.println("包含有效代码 " + effectLines + " 行");
        return effectLines;
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Java文件路径：");
        try {
            String strPath = br.readLine();
            int result = scanFileForEffectLines(strPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
