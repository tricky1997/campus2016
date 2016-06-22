package com.qunar;

import java.io.*;
import java.util.*;

/**
 * Created by hanju on 2016/6/20.
 */
public class CountMostImport {
    private String folderPath="E:\\Users\\HJH\\LDA4j-master";
    private Map<String,Integer> importClass;
    public CountMostImport()
    {
        this.importClass=new HashMap<String, Integer>();
    }
    public CountMostImport(String folderPath)
    {
        this.folderPath=folderPath;
        this.importClass=new HashMap<String, Integer>();
    }

    Comparator<Map.Entry<String,Integer>> myCom=new Comparator<Map.Entry<String, Integer>>(){

        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            if(o1.getValue()>o2.getValue())
                return 1;
            else if(o1.getValue()<o2.getValue())
                return -1;
            return o1.getKey().compareTo(o2.getKey());
        }
    };
    /**
     * 获得前10个
     * @return
     */
    public List<String> getTopTen()
    {
        countClass(folderPath);
        List<String > result=new ArrayList<String>();
        PriorityQueue<Map.Entry<String,Integer>> priorityQueue=new PriorityQueue<Map.Entry<String, Integer>>(10,myCom);
        for (Map.Entry<String, Integer> entry:importClass.entrySet()) {
            if(priorityQueue.size()<10)
                priorityQueue.add(entry);
            else
            {
                if(priorityQueue.peek().getValue()<entry.getValue())
                {
                    priorityQueue.poll();
                    priorityQueue.offer(entry);
                }
            }
        }
        while(!priorityQueue.isEmpty())
        {
            result.add(0,priorityQueue.peek().getKey());
            priorityQueue.poll();
        }
        return result;
    }

    /**
     * 统计每个类的import次数
     */
    private void countClass(String folderPath)
    {
        File folder=new File(folderPath);
        for(File file:folder.listFiles())
        {
            if(file.isDirectory())
                countClass(file.getPath());
            if(file.getName().contains(".java"))
            {
                //System.out.println("处理文件："+file.getName());
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                    String line;
                    while((line=br.readLine())!=null)
                    {
                        if(line.contains("import"))
                        {
                            line=line.replaceAll(";","");
                            line=line.replace("import","");
                            line=line.replace(" ","");
                            if(importClass.containsKey(line))
                            {
                                int time=importClass.get(line);
                                time++;
                                importClass.put(line,time);
                            }
                            else
                                importClass.put(line,1);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
