package com.qunar.CountMostImport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.RuleBasedCollator;
import java.util.*;

/**
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么.
 */
public class CountMostImport {
    public static final Logger log = LoggerFactory.getLogger(CountMostImport.class);

    public static List<String> MostImportedClass(String path) throws IOException {
        List<File> filteredFiles = iteratorDirAllfiles(path);
        //遍历每一个java源文件，得出import结果。
        HashMap<String, Integer> importedClass = iteratorSourceFile(filteredFiles);
        //排序
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(importedClass.entrySet());
        //1.采用堆排序的思想，求topk，不新建集合，原集合的前k个元素即为最大
        getTopKByHeapsort(list, 10);
        //2.Collections提供的sort方法简单排序。
        //getTopK(list); //降序

        //取top10
        int count = list.size() < 10 ? list.size() : 10;
        List<String> topTen = new ArrayList<String>(count);
        for (int i = 0; i < count; i++) {
            log.info(list.get(i).getKey() + ": " + list.get(i).getValue());
            topTen.add(list.get(i).getKey());
        }
        return topTen;
    }

    /**
     * 返回指定路径下的所有文件
     *
     * @param path 文件路径
     * @throws IOException
     */
    protected static List<File> iteratorDirAllfiles(String path) throws IOException {
        File file = new File(path);
        //外部路径必须是文件夹，且存在
        if (!file.exists() || !file.isDirectory()) {
            throw new IOException("the directory error!,check your file path.");
        }
        List<File> srcFiles = new ArrayList<File>();
        List<File> subDir = new ArrayList<File>();
        File[] files = file.listFiles();
        String fileString = null;
        for (File f : files) {
            if (f.isDirectory()) {
                //log.debug("Dir AbsolutePath:" + f.getAbsolutePath());
                subDir.add(f);
            } else {
                fileString = f.toString();
                //不论是哪种源文件，不考虑顶级目录下既有class也有java的情况，虽然不影响结果。
                if (fileString.endsWith(".java") || fileString.endsWith(".class")) {
                    srcFiles.add(f);
                }
            }
        }
        File tFile = null;
        //一边遍历，一边添加元素，不用迭代器
        for (int i = 0; i < subDir.size(); i++) {
            tFile = subDir.get(i);
            files = tFile.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    //log.debug("Dir AbsolutePath:" + f.getAbsolutePath());
                    subDir.add(f);
                } else {
                    //log.debug("File AbsolutePath:" + f.getAbsolutePath());
                    fileString = f.toString();
                    //不论是哪种源文件，不考虑顶级目录下既有class也有java的情况，虽然不影响结果。
                    if (fileString.endsWith(".java") || fileString.endsWith(".class")) {
                        srcFiles.add(f);
                    }
                }
            }
        }

        return srcFiles;
    }

    //将每一个文件中的类都存到hashmap中
    private static HashMap<String, Integer> iteratorSourceFile(List<File> filteredFiles) throws IOException {
        HashMap<String, Integer> importedClass = new HashMap<String, Integer>();
        //遍历每一个文件，提取import类
        for (File f : filteredFiles) {
            ScanFile(f, importedClass);
        }

        return importedClass;
    }

    private static void ScanFile(File file, HashMap<String, Integer> importedClass) throws IOException {
        FileInputStream fStream = null;
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            fStream = new FileInputStream(file);
            reader = new InputStreamReader(fStream, "UTF-8");//将InputSteam转换成Reader
            in = new BufferedReader(reader);    //只提供缓冲区的包装方法
            StatisticImportedClass(in, importedClass);
        } catch (FileNotFoundException e) {
            log.info("file not found :" + e.getMessage());
            throw e;
        } catch (SecurityException e) {
            log.info("something maybe security :" + e.getMessage());
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.info("encoding wrong :" + e.getMessage());
            throw e;
        } catch (IOException e) {
            log.info("check please:" + e.getMessage());
            throw e;
        } finally {
            try {
                if (fStream != null) fStream.close();
            } catch (IOException e) {
                log.warn("FileInputStream close failed");
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                log.warn("InputStreamReader close failed");
            }
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                log.warn("BufferedReader close failed");
            }
        }
    }

    /**
     * 统计所有import的类，不考虑不规范编码
     *
     * @param buffin
     * @param importedClass
     * @throws IOException
     */
    private static void StatisticImportedClass(BufferedReader buffin, HashMap<String, Integer> importedClass) throws IOException {
        String buffer, strLine, subString;
        while ((buffer = buffin.readLine()) != null) {
            strLine = buffer.trim();
            //import java.io.*;这种情况也认为是一种类
            if (strLine.matches("^import.*;$")) {
                //获得类的全称
                subString = strLine.substring(7, strLine.indexOf(';'));
                if (importedClass.containsKey(subString)) {
                    //log.debug("hashmap 包含：" + subString);
                    //已有，则自增1
                    importedClass.replace(subString, importedClass.get(subString) + 1);
                } else {
                    //log.debug("hashmap 不包含：" + subString);
                    //不包含，加进去。
                    importedClass.put(subString, 1);
                }
            }
            //不遍历整个文件，当类开始时，结束遍历
            if (strLine.contains("class")) {
                break;
            }
        }
    }

    /**
     * 给list做简单的排序
     *
     * @param list 待排序集合
     */
    protected static void getTopK(List<Map.Entry<String, Integer>> list) {
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    return RuleBasedCollator.getInstance(Locale.ENGLISH).compare(o1.getKey(), o2.getKey());
                } else {
                    return o1.getValue() - o2.getValue();
                }
            }
        });
        Collections.reverse(list);
    }

    /**
     * 在list集合中创建小根堆，list集合中前k个元素即为结果
     *
     * @param list 待排序集合
     * @param k    取最大的k个元素
     */
    protected static void getTopKByHeapsort(List<Map.Entry<String, Integer>> list, int k) {
        if (list == null || list.size() < k) {
            return;
        }
        //先将前k个元素建成小根堆
        buildMinHeap(list, k);
        int count = list.size();
        for (int i = k; i < count; i++) {
            if (list.get(0).getValue() < list.get(i).getValue()) {
                Collections.swap(list, 0, i);
                //小根堆的再平衡
                minHeapShift(list, 0, k);
            }
        }
    }

    /**
     * 在原数组中将前k个元素建成一个最小堆，以0开始。
     *
     * @param list 要排序的集合
     * @param k    前k个元素
     */
    private static void buildMinHeap(List<Map.Entry<String, Integer>> list, int k) {
        for (int i = k / 2 - 1; i >= 0; i--) {
            minHeapShift(list, i, k);
        }
    }

    /**
     * 调整堆节点，该方法只调整指定的index节点,没有考虑值相等按照字符排序
     *
     * @param list  要排序的集合
     * @param index 要调整的节点
     * @param k     堆的大小
     */
    private static void minHeapShift(List<Map.Entry<String, Integer>> list, int index, int k) {
        int i, j;
        boolean finished = false;
        Map.Entry<String, Integer> temp = list.get(index);
        i = index;
        j = i * 2 + 1;
        while (j < k && !finished) {
            if ((j + 1) < k && list.get(j).getValue() > list.get(j + 1).getValue()) {
                j++;
            }
            if (temp.getValue() <= list.get(j).getValue()) {
                finished = true;
            } else {
                list.set(i, list.get(j));
                i = j;
                j = 2 * i+1;
            }
        }
        list.set(i, temp);
    }
}
