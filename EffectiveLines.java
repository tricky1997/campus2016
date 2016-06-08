import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

/**
 * 统计一个Java文件的有效行数。
 *  1.有效不包括空行
 *  2.不考虑代码见有多行注释的情况
 *
 * Created by 欧阳晟 on 2016/6/4.
 * 运行于java8
 */
public class EffectiveLines {
    public static final String[] DEFAULT_FILENAMES = new String[] {"src/main/java/"};

    public static void main(String[] args) {

        String[] filenames = args.length > 0 ? args : DEFAULT_FILENAMES;    // 选择命令行参数或默认参数

        for (String filename : filenames) {
            try {

                printEffectiveLinesRecursive(Paths.get(filename));

            } catch (IOException e) {
                System.out.println(filename + " : not exist");
            }
        }
    }

    /**
     * 递归遍历文件
     *
     * @param file
     * @throws IOException
     */
    private static void printEffectiveLinesRecursive(Path file) throws IOException {
        Files.walk(file).forEach(EffectiveLines::printEffectiveLines);
    }

    /**
     * 计算文件行数
     *
     * @param file
     */
    private static void printEffectiveLines(Path file) {
        if (Files.isDirectory(file))
            return;

        try {
            Predicate<String> blankFilter = l -> !l.trim().isEmpty();     // 空行过滤
            Predicate<String> commentFilter = l -> !l.trim().startsWith("//");   // 单行注释过滤

            long count = Files.lines(file).filter(blankFilter.and(commentFilter)).count();

            System.out.println(file + " : " + count);
        } catch (IOException e) {
            System.out.println(file + " : read error");
        }

    }

}
