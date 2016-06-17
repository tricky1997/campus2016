import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newDirectoryStream;

/**
 * 统计Java源文件的有效行数
 *
 * @author  Andrew QIU
 * @date    2016-6-15
 */
public class CalcCodeLines {

    /**
     * 主函数
     */
    public static void main(String[] args) {
        // 待扫描文件的储存目录
        Path lib = Paths.get("target").toAbsolutePath();
        /* 扫描指定目录下的java文件 */
        try (DirectoryStream<Path> stream = newDirectoryStream(lib)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)
                        && file.toString().endsWith(".java")) {
                    // 打印文件名称
                    System.out.println(file.getFileName());
                    // 打印有效行数
                    System.out.println("有效行数：" + scanSelectedFile(file));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描指定文件（假设注释都符合规范）
     *
     * @param path  待检测文件路径
     * @return      返回有效行数
     */
    private static int scanSelectedFile(Path path) {
        // 文件有效行数
        int lineNum = 0;

        try (Reader fr = new FileReader(path.toAbsolutePath().toString())) {
            BufferedReader br = new BufferedReader(fr);
            /* 读取文件 */
            while (br.ready()) {
                // 读入当前行内容（除去两端的空格、制表符等）
                String line = br.readLine().trim();

                // 如果为空行或单行注释，则忽略
                if (line.isEmpty() || line.startsWith("//")
                        || (line.startsWith("/*") && line.endsWith("*/")))
                    continue;
                // 有效行数加1
                ++lineNum;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineNum;
    }

}
