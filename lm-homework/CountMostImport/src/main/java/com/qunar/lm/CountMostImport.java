package com.qunar.lm;

import java.io.*;
import java.util.*;


/**
 * 问题分析
 * 输入：java源文件目录
 * 输出：前十个被import最多的类
 * 思路：1遍历文件方法+2文件统计类方法+3最终输出显示方法
 * 细节：1递归遍历思路代码简单，性能受文件路径深度影响，
 * 非递归遍历性能稳定，更优。
 * 2注意*替代符，正文import开头，判定全面
 * 3排序，前N个，适于优先队列，注意比较器消除并列。
 * Created by lm on 2016/7/1 0001.
 */
public class CountMostImport {

    private Map<String, Integer> importClassMap = new HashMap<String, Integer>();

    /**
     * 非递归遍历文件方法
     */
    private void traversalFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在!");
            return;
        }
        Stack<File> stack = new Stack();
        stack.push(file);
        while (!stack.empty()) {
            file = stack.peek();
            stack.pop();
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    countFile(f);
                } else if (f.isDirectory()) {
                    stack.push(f);
                }
            }
        }
    }

    /**
     * 计数文件中的import类方法
     */
    private void countFile(File file) {
        if (!file.getName().contains(".java")) {
            //System.out.println("跳过文件："+file.getName());
            return;
        }
        BufferedReader br = null;
        System.out.println("处理文件：" + file.getName());
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            boolean flag = false;
            while ((line = br.readLine()) != null) {
                //忽略含有“*”情况
                if (line.contains("*")) {
                    continue;
                }
                if (line.trim().startsWith("import")) {
                    flag = true;
                    if (importClassMap.containsKey(line.substring(6))) {
                        int time = importClassMap.get(line.substring(6));
                        time++;
                        importClassMap.put(line.substring(6), time);
                        System.out.println("插入：" + line.substring(6).toString() + time);
                    } else {
                        importClassMap.put(line.substring(6), 1);
                        System.out.println("插入：" + line.substring(6).toString() + 1);
                    }
                }
                // 重要：不再连续遇到import开头的非空行，终止
                else if (flag && !line.trim().isEmpty())
                    break;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示最多被引用的类方法
     */
    private void countMostImport(int N) {
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(N + 1,
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        if (o1.getValue() != (o2.getValue())) {
                            return o1.getValue().compareTo(o2.getValue());
                        } else {
                            return o1.getKey().compareTo(o2.getKey());
                        }
                    }
                }
        );
        for (Map.Entry<String, Integer> entry : importClassMap.entrySet()) {
            priorityQueue.add(entry);
            if (priorityQueue.size() > N) {
                priorityQueue.poll();
            }
        }
        while (!priorityQueue.isEmpty()) {
            Map.Entry<String, Integer> entry = priorityQueue.poll();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        CountMostImport ci = new CountMostImport();
        Scanner sca = new Scanner(System.in);
        System.out.println("请输入项目目录的绝对路径！");
        while (sca.hasNext()) {
            ci.traversalFiles(sca.nextLine());
            ci.countMostImport(10);
            System.out.println("请输入项目目录的绝对路径！");
        }
    }
}
