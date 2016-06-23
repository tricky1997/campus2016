import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.imooc.db.DBUtil;
import com.imooc.model.Goddess;
import com.imooc.db.DBUtil;
import com.imooc.db.DBUtil;
import com.imooc.db.DBUtil;
import com.imooc.model.Goddess;
import com.imooc.model.Goddess;
import com.imooc.model.Goddess;
import java.io.date;
import java.io.date;
import java.io.date;
import java.io.date;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.lang.reflect.Array;


package com.qunar.com;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class CountMostImportClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("className.java");
		if(f.exists()) {
			System.out.println("className.java existed!");
		}
		else {
			System.out.println("className.java do not exist!");
		}
		String rs = getMostImportClass(f);
		System.out.println("import最多的类是：" + rs);
//String str = "import java.io*";
//System.out.println(str.substring(7, str.length() - 1));
	}

	public static String getMostImportClass(File f) {
		String rs = "";
		Map<String, Integer> map = new HashMap<String, Integer>();
		FileReader fr = null;
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					if(line.trim().startsWith("class")||line.trim().startsWith("public")||
					   line.trim().startsWith("private")) {
						break;
					}else if(line.trim().startsWith("import")) {
						String className = line.trim().substring(7,line.trim().length() - 1);
						Integer val = map.get(className);
						if(val == null) {
							map.put(className, 1);
						}else {
							map.put(className, val + 1);
						}
						
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = sort(map);
		return rs;
	}
	
	public static String sort(Map<String, Integer> map) {
		String rs = "";
		int maxNum = Integer.MIN_VALUE;
		for(Map.Entry<String, Integer> entry:map.entrySet()) {
			String key = entry.getKey();
			int val = (int)entry.getValue();
			if(val > maxNum) {
				maxNum = val;
				rs = key;
			}
		}
		return rs;
	}

}
