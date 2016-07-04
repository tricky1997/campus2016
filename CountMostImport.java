package com.fadying.qunar;

import com.google.common.util.concurrent.AtomicLongMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么
 * 使用了 Guava 里的 AtomicLongMap 类来实现并发计数,计数完成后使用最小堆统计 top n 的包
 * import 格式可以在符号处插入换行、注释,这里只考虑格式化的文件
 *
 * Created on 16/6/4.
 *
 * @author fadying
 */
public class CountMostImport {
    static Logger log = LoggerFactory.getLogger(CountMostImport.class);

    public static final String[] DEFAULT_FILENAMES = new String[] {"src/main/java/"};

    public static int MAX_SIZE = 10;
    public static MaxCounter counter;

    public static void main(String[] args) {

        String[] filenames = args.length > 0 ? args : DEFAULT_FILENAMES;    // 选择命令行参数或默认参数

        for (String filename : filenames) {
            try {
                counter = new MaxCounter(MAX_SIZE);
                recuresiveVisitFath(Paths.get(filename), CountMostImport::countImport);

                // 倒序打印
                counter.countMaxValue().descendingIterator().forEachRemaining(x ->
                        System.out.println(x + ":" + counter.getNumber(x)));

            } catch (IOException e) {
                System.out.println(filename + " : not exist");
            }
        }
    }

    /**
     * 递归遍历文件
     *
     * @param path
     * @throws IOException
     */
    private static void recuresiveVisitFath(Path path, Consumer<Path> action) throws IOException {
        Files.walk(path).forEach(action);
    }

    /**
     * 读取文件中的每行类引入,将其统计到 MaxCounter 中
     * 
     * @param path
     */
    public static void countImport(Path path) {
        if (Files.isDirectory(path))
            return;

        Predicate<String> importFilter = l -> l.trim().startsWith("import");    // 单行import提取
        Function<String, String> importGet = x -> x.replaceAll("import|;", "").trim();  // 提取引入类名,
        try {
            Files.lines(path).filter(importFilter).map(importGet).forEach(counter::addValue);
        } catch (IOException e) {
            System.out.println(path + " : read error");
            e.printStackTrace();
        }
    }
}

/**
 * 计数器,同时获得数量排名前几的数值
 */
class MaxCounter {
    private int size;       // maxValueContainer 的容量
    private AtomicLongMap<String> counter;      // 支持并发计数
    private LinkedList<String> maxValueContainer; // 最小堆,这里使用链表的简易实现,后期可以扩展至堆实现

    public MaxCounter(int size) {
        this.size = size;
        this.counter = AtomicLongMap.create();
        this.maxValueContainer = new LinkedList<>();
    }

    public long addValue(String value) {

        return counter.incrementAndGet(value);
    }

    public long getNumber(String value) {
        return counter.get(value);
    }

    /**
     * 将当前计算值添加至最小堆中,并调整位置
     * 
     * @param value
     * @param count
     */
    public void mergeValue(String value, Long count) {
        int index = maxValueContainer.indexOf(value);
        if (index == -1 && maxValueContainer.size() < size) {
            maxValueContainer.addFirst(value);
            index = 0;
        }

        while (index + 1 < maxValueContainer.size()) {
            String nextValue = maxValueContainer.get(index + 1);
            if (counter.get(nextValue) < count) {
                maxValueContainer.set(index + 1, value);
                if (index != -1)
                    maxValueContainer.set(index, nextValue);
            }
            index++;
        }
    }

    /**
     * 完成统计后,生成 top n 列表
     * 
     * @return
     */
    public LinkedList<String> countMaxValue() {
        this.maxValueContainer = new LinkedList<>();

        for (Map.Entry<String, Long> entry : counter.asMap().entrySet()) {
            mergeValue(entry.getKey(), entry.getValue());
        }

        return this.maxValueContainer;
    }
}