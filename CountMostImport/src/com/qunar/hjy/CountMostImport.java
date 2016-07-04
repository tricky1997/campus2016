package com.qunar.hjy;

/**
 * Created by lenovo on 2016/7/4.
 */

import java.io.*;
import java.util.*;

public class CountMostImport {


    private Map<String, Integer> importClassMap = new HashMap<String, Integer>();

    public static void main(String[] args) {
        CountMostImport ci = new CountMostImport();
        Scanner sca = new Scanner(System.in);
        System.out.println("Please input the path of project:");
        while (sca.hasNext()) {
            ci.traversalFiles(sca.nextLine());
            ci.countMostImport(10);
            System.out.println("Please input the path of project:");
        }
    }

    private void traversalFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File does not exist!");
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


    private void countFile(File file) {
        if (!file.getName().contains(".java")) {

            return;
        }
        BufferedReader br = null;
        System.out.println("File:" + file.getName());
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
                        System.out.println("Insert：" + line.substring(6).toString() + time);
                    } else {
                        importClassMap.put(line.substring(6), 1);
                        System.out.println("Insert：" + line.substring(6).toString() + 1);
                    }
                }

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


}

