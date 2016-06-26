import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/*
 * 考虑一行可能多个import语句的情况：
 * 例如： import java.io.File; import java.util.List;
 * 考虑一个import语句中间换行的情况：
 * 例如： import java.
 * io.File;
 * 考虑可能出现的注释
 * 使用HashMap存储
 */

public class CountMostImport {
	private HashMap<String, Integer> map;
	
	public CountMostImport(){
		map = new HashMap<String, Integer>();
	}
	
	public void count_import(String filepath){
		File in = new File(filepath);
		if(in.isDirectory()){
			File files[] = in.listFiles();
			for(int i = 0; i < files.length; ++i){
				if(files[i].getName().endsWith(".java")){
					BufferedReader br = null;
					String total = "";
					String str = null;
					try{
					br = new BufferedReader(new InputStreamReader(new FileInputStream(files[i])));
					while((str = br.readLine()) != null  ){
						if(str.contains("{")){
							String tmp = str.split("\\{")[0];
							String tmp1 = tmp.split(";")[0];
							total = total + tmp1;
							break;
						}
							total = total + str;
						
					}
					String split[] = total.split(";");
					for(int j = 0; j < split.length; ++j){
						if(split[j].startsWith("import")){
							String tmp = split[j].split("[ ]{1,}")[1];
							Integer inte = map.get(tmp);
							if(inte == null)
								map.put(tmp, 1);
							else
								map.put(tmp, inte + 1);
						}
						else
							continue;
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		else{
			System.out.println("输入的不是一个目录");
		}
	}
	
	public void print_top_10(){
		List<HashMap.Entry<String, Integer>> list = new ArrayList<HashMap.Entry<String, Integer>>(map.entrySet()); 
		Collections.sort(list, new Comparator<HashMap.Entry<String, Integer>>()  
			      {   
			          public int compare(HashMap.Entry<String, Integer> o1, HashMap.Entry<String, Integer> o2)  
			          {  
			           if(o2.getValue()!=null&&o1.getValue()!=null&&o2.getValue().compareTo(o1.getValue())>0){  
			            return 1;  
			           }else{  
			            return -1;  
			           }  
			              
			          }  
			      });  
		int m = (10 < list.size())? 10 : list.size();
		System.out.println("Top "+ m +":");
		for(int i = 0; i < m; ++i){
			HashMap.Entry<String, Integer> en = list.get(i);
			System.out.println(en.getKey()+"  "+en.getValue());
		}
	}
	
	public static void main(String args[]) throws IOException{
		CountMostImport c = new CountMostImport();
		System.out.println("请输入目录绝对路径：");
		Scanner s = new Scanner(System.in);
		String path = s.next();
		c.count_import(path);
		s.close();
		c.print_top_10();
	}
}
