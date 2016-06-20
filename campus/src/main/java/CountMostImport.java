import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 统计被import最多的类
 *
 * Created by andrew on 2016/6/20.
 */
public class CountMostImport {
    private File pathFile;
    private List<File> allFileList = Lists.newArrayList();

    PriorityQueue<Map.Entry<String, Integer>> importClassQueue;
    {
        importClassQueue = new PriorityQueue<Map.Entry<String, Integer>>(100, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
    }

    public CountMostImport(final String path) {
        pathFile = new File(path);
    }

    protected List<File> listAllJavaFile(File pathFile){
        List<File> fileList = Lists.newArrayList();
        if (!pathFile.exists()){
            return Lists.newArrayList();
        }
        File[] javaFiles = pathFile.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        Collections.addAll(fileList, javaFiles);
        File[] dirFiles = pathFile.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File file : dirFiles){
            fileList.addAll(listAllJavaFile(file));
        }
        return fileList;
    }

    final static Pattern CLASS_PATTERN = Pattern.compile("\\bimport ([^;]+)\\b;", Pattern.CASE_INSENSITIVE);

    private List<String> selectImportClass() throws IOException {
        List<String> importClassList = Lists.newArrayList();
        for (File file : allFileList){
            String content = FileUtils.readFileToString(file);
            Matcher matcher = CLASS_PATTERN.matcher(content);

            while (matcher.find()){
                importClassList.add(matcher.group(1));
            }
        }
        return importClassList;
    }

    private boolean isAvailable(String importClass){
        if (StringUtils.isBlank(importClass)){
            return false;
        }
        //匹配遇到static 静态导入或者遇到java.util.*之类的默认为不合符要求
        return !(importClass.startsWith("static ") || ((char) importClass.indexOf(importClass.length() - 1) == '*'));
    }

    private Map<String, Integer> countClass2Map() throws IOException {
        Map<String, Integer> classCountMap = Maps.newHashMap();
        allFileList = listAllJavaFile(pathFile);
        List<String> importClassList = selectImportClass();

        for (String importClass : importClassList){
            Integer count;
            if ((count = classCountMap.get(importClass)) != null){
                count++;
                classCountMap.put(importClass, count);
                continue;
            }

            if (isAvailable(importClass)){
                classCountMap.put(importClass, 1);
            }
        }
        return classCountMap;
    }

    private void countClassMap2Queue() throws IOException {
        Map<String, Integer> importClassMap  = countClass2Map();

        for (Map.Entry entry : importClassMap.entrySet()) {
            importClassQueue.add(entry);
        }
    }
    public void printTop10() throws IOException {
        countClassMap2Queue();
        for (int i = 0; i < 10 && 0 < importClassQueue.size(); i++){
            Map.Entry<String, Integer> entry = importClassQueue.poll();
            System.out.println(entry.getKey() + "-------->" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        CountMostImport countMostImport = new CountMostImport("C:\\Users\\andrew\\IdeaProjects\\campus2016\\campus\\src\\main\\java");
        try {
            countMostImport.printTop10();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
