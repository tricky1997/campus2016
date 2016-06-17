import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 统计指定项目目录下，被import最多的类名
 *
 * @author  Andrew QIU
 * @date    2016-6-14
 */
public class Processor {
    // 统计列表
    private static Map<String, Integer> classList = new HashMap<>();

    /**
     * 主函数
     */
    public static void main(String[] args) {
        // 待扫描文件的储存目录
        Path lib = Paths.get("target").toAbsolutePath();
        /* 扫描指定目录下的java文件 */
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(lib)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)
                        && file.toString().endsWith(".java")) {
                    scanSelectedFile(file);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 对每个文件的import次数进行排序 */
        List<Map.Entry<String, Integer>> list
                = new ArrayList<>(classList.entrySet());
        // 创建比较器
        EntryComparator comparator = new EntryComparator();
        // 按照导入次数进行排序比较
        Collections.sort(list, comparator);

        /* 打印排名前十的结果 */
        Iterator iter = list.iterator();
        for (int i = 1; iter.hasNext() && i < 20; ++i) {
            Map.Entry<String, Integer> pairs
                    = (Map.Entry<String, Integer>) iter.next();
            System.out.println(pairs.getKey());
            System.out.println("被导入" + pairs.getValue() + "次");
        }
    }

    /**
     * 扫描指定文件（假设文件都符合规范）
     *
     * @param path  待检测文件路径
     */
    public static void scanSelectedFile(Path path) {
        Integer count;

        //System.out.println("当前文件：" + path.getFileName());

        try (Reader fr = new FileReader(path.toAbsolutePath().toString())) {
            BufferedReader br = new BufferedReader(fr);
            /* 读取文件 */
            while (br.ready()) {
                // 读入当前行内容（除去两端的空格、制表符等）
                String line = br.readLine().trim();
                // 如果为import语句，则继续
                if (line.startsWith("import")) {
                    // 获取导入类名（不包含末尾的分号）
                    String className = line.substring(7, line.length()-1);
                    // 如果不存在，则添加到列表;否则，导入次数加一
                    count = classList.putIfAbsent(className, 1);
                    if (null != count) {
                        classList.put(className, ++count);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
