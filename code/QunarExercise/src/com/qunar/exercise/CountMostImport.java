package com.qunar.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by lipingyu on 16/6/20.
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么
 */
public class CountMostImport {

	/**
	 * @param args
	 * 用UTF-8编码方式读取所有文件的内容
	 * 统计指定路径的源文件夹中的类被import的次数，返回前十个
	 * @throws IOException 
	 */
	public List<String> count(List<String> list) throws IOException{
		List<String> fileList = list;
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		for(int i=0;i<list.size();i++){
			InputStream input = new FileInputStream(fileList.get(i));
			InputStreamReader reader = new InputStreamReader(input,"utf-8");
			BufferedReader readBuf = new BufferedReader(new InputStreamReader(input));
			String line = null;
			try {
				while((line = readBuf.readLine()) !=null){
					line.trim();
					if(line.startsWith("import")){
						//提取类名
						int indexOfSpace = line.indexOf(" ");
						int indexOfSymbol = line.indexOf(";");
						String className = line.substring(indexOfSpace+1,indexOfSymbol);
						if(!countMap.containsKey(className)){
							countMap.put(className, new Integer(1));
						}else{
							countMap.put(className,countMap.get(className)+1);
						}
						
					}
					//不读取类文件
					if(line.startsWith("class")||line.startsWith("public")
						|| line.startsWith("private") || line.startsWith("interface")
                        || line.startsWith("final") || line.startsWith("abstract class"))
	                        break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				//关闭文件流
				try{
					if(readBuf != null)
						readBuf.close();
					if(reader != null)
						reader.close();
					if(input !=null)
						input.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				
			}
		}
		
		ValueComparator vc = new ValueComparator(countMap);
		TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(vc);
	    sorted_map.putAll(countMap);
		
		
		//取前十个记录，保存到results中
		List<String> results = new ArrayList<String>();
		int index = 0;
		Iterator iter = sorted_map.entrySet().iterator();
		while(iter.hasNext() && index < 10){
			Map.Entry entry =(Map.Entry)iter.next();
			results.add((String)entry.getKey());
			index++;
		}
		return results;
	}
	
	/*
	 * 获取一个文件夹下的所有文件，要求：后缀名为java
	 * @param dir 表示文件夹的路径
	 * @return List<String>  返回文件夹下所有的.java文件的绝对路径
	 * */
	public List<String> getFileList(String dir){
		List<String> fileList = new ArrayList<String>();
		File file = new File(dir);
		if(file.exists()){
			 LinkedList<File> folderList = new LinkedList<File>();
	         File[] files = file.listFiles();
	         for (File file2 : files) {
	                if (file2.isDirectory()) {
	                    //如果是文件夹，则添加到folderList中
	                	folderList.add(file2);
	                }else if(file2.getName().indexOf(".java") > -1){
	                	fileList.add(file2.getAbsolutePath());
	                }
		     }
	         File temp_file;
	         //依次遍历每一个子文件夹
	         while (!folderList.isEmpty()) {
	                temp_file = folderList.removeFirst();
	                files = temp_file.listFiles();
	                for (File file2 : files) {
	                    if (file2.isDirectory()) {
	                        folderList.add(file2);
	                    } else if(file2.getName().indexOf(".java") > -1){
	                    	fileList.add(file2.getAbsolutePath());
	            			}
	                    }
	                }
		}else{
			 System.out.println("文件不存在!");
		}
		return fileList;
	} 

	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		System.out.println("请输入项目目录的绝对路径！");
		while(sca.hasNext()){
			CountMostImport ci = new CountMostImport();
			List<String> fileList  = ci.getFileList(sca.nextLine());
			try {
				List<String> results = ci.count(fileList);
				System.out.println("前十个被import最多的类为：");
				for(int i=0;i<results.size();i++){
					System.out.println(results.get(i));
				}
				System.out.println("请输入项目目录的绝对路径！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}

/**
 * 实现Comparator接口
 * */
class ValueComparator implements Comparator<String>{
	Map<String,Integer> base = null;
	
	public ValueComparator(Map<String,Integer>base){
		this.base = base;
	}
	
	@Override
	public int compare(String a,String b){
		if(!base.containsKey(a) || !base.containsKey(b))
			return 0;
		if(base.get(a) > base.get(b))
			return -1;
		else if(base.get(a) == base.get(b)){
			//如果出现次数相同，则按类名的字典顺序排序
			return a.compareTo(b);
		}else{
			return 1;
		}
	}
}