package com.qunar.www;
import java.io.*;
public class CountEffectiveLines {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("test.java");
		if(f.exists()) {
			System.out.println("test.java existed!");
		}
		else {
			System.out.println("test.java do not exist!");
		}
		int rs = getEffectiveLines(f);
		System.out.println("effective lines:" + rs);
	}
	
	public static int getEffectiveLines(File f) {
		int effectiveLinesNum = 0;
		int notEffectiveLinesNum = 0;
		try {
			FileReader fr = new FileReader(f);
			BufferedReader bfr = new BufferedReader(fr);
			String line ="";
			Boolean flag = false;
			try {
				
				while( (line = bfr.readLine()) != null) {
					if(line.trim().equals("") == false) {
						if(line.trim().startsWith("/*") == true&&line.trim().endsWith("*/") == true) {
							notEffectiveLinesNum++;
						}else if(line.trim().startsWith("/*") == true&&line.trim().endsWith("*/") == false) {
							flag = true;
							notEffectiveLinesNum++;
						}else if(line.trim().startsWith("/*") == false&&flag == true&&line.trim().endsWith("*/") == false) {
							  notEffectiveLinesNum++;
						}else if(line.trim().startsWith("/*") == false&&flag == true&&line.trim().endsWith("*/")) {
							  notEffectiveLinesNum++;
							  flag = false;
						}else if(line.trim().startsWith("//")) {
							  notEffectiveLinesNum++;
						}else {
							  effectiveLinesNum++;
						}
					}else {
						notEffectiveLinesNum++;
					}
					
				}
				bfr.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return effectiveLinesNum;
	}

}


