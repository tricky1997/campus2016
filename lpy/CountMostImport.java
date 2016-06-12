package task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


class ValueComparator implements Comparator<String>{
	Map<String,Integer> base;
	public ValueComparator(Map<String,Integer>base){
		this.base = base;
	}
	
	public int compare(String a,String b){
		if(base.get(a)>=base.get(b)){
			return -1;
		}else{
			return 1;
		}
	}
}

public class CountMostImport {

	/**
	 * @param args
	 * 用UTF-8编码方式读取所有文件的内容
	 * 统计指定路径的源文件夹中被import最多的类
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public List<String> count(List<String> list) throws IOException{
		List<String> fileList = list;
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		
		for(int i=0;i<list.size();i++){
			InputStream input = new FileInputStream(fileList.get(i));
			InputStreamReader reader = new InputStreamReader(input,"utf-8");
			BufferedReader readBuf = new BufferedReader(new InputStreamReader(input));
			try {
				String line = readBuf.readLine();
				line.trim();
				while(line !=null){
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
					line = readBuf.readLine();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
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
		CountMostImport ci = new CountMostImport();
		List<String> fileList  = ci.getFileList("D:\\Java\\MyEclipse\\Workspaces\\MyEclipse 8.5\\Algorithms\\src");
		try {
			List<String> results = ci.count(fileList);
			for(int i=0;i<results.size();i++){
				System.out.println(results.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
