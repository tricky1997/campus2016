package com.qunar.exercise;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @dest
 * 统计一个java文件的有效行数
 * 1.有效行数不包括空行
 * 2.不考虑代码间有多行注释的情况
 * @author lipingyu
 */
public class EffectiveLines {
	
	/*
	 * 统计java文件的有效行数
	 * @param fiePath
	 * @return 有效行数
	 * */
    public int effectiveLines(String filePath){
    	//创建一个File对象
		File file = new File(filePath);
		//创建字节输入流
		InputStream input = null;
		//将字节输入流转换成字符
		InputStreamReader isReadr = null;
		//创建包装类BufferedReader的对象，缓冲读取字符
		BufferedReader readBuf = null;
		//用于统计有效行数
		int lineNum = 0;
		try {
			input = new FileInputStream(file);
			readBuf = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while((line=readBuf.readLine()) !=null){
				Pattern p = Pattern.compile("\\s*|\t");
				Matcher m = p.matcher(line);
				//去除字符串中的空格、制表符
				line = m.replaceAll("");
				if (line.startsWith("//")|| line.equals("")||(line.startsWith("/*")&&line.endsWith("*/")))
					continue;
				lineNum++;
			}
			
		}catch (FileNotFoundException e) {
			System.out.println("未找到指定的文件");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			//关闭文件流
			try{
				if(readBuf != null)
					readBuf.close();
				if(isReadr != null)
					isReadr.close();
				if(input != null)
					input.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return lineNum;
	}
	
	public static void main(String[] args) {
		    
            Scanner sca = new Scanner(System.in);
            
            System.out.println("请输入文件路径！");
            String filePath = sca.nextLine();
            int result = new EffectiveLines().effectiveLines(filePath);
            System.out.println("总的有效行数为："+result+"行!");
	}
}
