

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by ZhangMiaosen on 2016/6/17.
 */

/*统计一个Java文件的有效行数。
1、有效不包括空行
2、不考虑代码见有多行注释的情况
*/

public class EffectiveLinesNew {
    private static Logger logger = Logger.getLogger(EffectiveLinesNew.class);

    public static void main(String[] args) {
        String filepath = "D:\\JAVA\\homeWork\\EffectiveLines\\src\\EffectiveLinesNew.java";
        int n = EffectiveLinesNew.count(filepath);
        logger.debug("有效行数共"+n+"行");
    }

    public static int count(String file) {
        BufferedReader bufferedReader = null;
        String line = null;
        int sumcount = 0;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (isEffective(line))
                    sumcount++;
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
        }
        return sumcount;
    }

    public static boolean isEffective(String line) {
        boolean flag = true;
        //判断是否是空行
        if (line.equals(""))
            flag = false;
        //判断是否是注释行
        else if (line.startsWith("/"))
            flag = false;

        return flag;
    }
}
