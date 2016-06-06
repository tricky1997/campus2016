/**
 * 4、统计附件中的StringUtils.java文件的有效代码行数。
 * 1) 有效不包括空行、注释
 * 2) 考虑代码里有多行注释的情况
 */

/*  示例
 *  //
 *  //sfsdf
 *  /*
 *
 *
 *
 */

// /*

import java.io.*;
import java.util.Scanner;

/**
 * @author JiangHP
 */
public class EffectiveLines {

    public static void main(String[] args) {
        Scanner in = null;
        PrintWriter out = null;
        boolean blockCommentted = false;
        int lineCount = 0;
        String classPath = EffectiveLines.class.getResource("/").getFile();
        try {
            in = new Scanner(new FileInputStream(classPath+"StringUtils.java"));
            out = new PrintWriter(new FileOutputStream(classPath+"StringUtils2.java"));
            while (in.hasNextLine()) {
                String str = in.nextLine();
                System.out.println(str);
                if (blockCommentted) {
                    if (str.trim().indexOf("*/") != -1) {
                        blockCommentted = false;
                    }
                    continue;
                }
                if (!blockCommentted && str.trim().startsWith("/*")) {
                    blockCommentted = true;
                    continue;
                }
                if (str.trim().startsWith("//")) {
                    continue;
                }
                if (str.trim().equals("")) {
                    continue;
                }
                lineCount++;
                out.println(str);
            }
            System.out.println(lineCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}



