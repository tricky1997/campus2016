import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//统计一个Java文件的有效行数。
//有效不包括空行
//不考虑代码见有多行注释的情况, 默认UTF-8编码

public class EffectiveLines {
public static int countEffectiveLines(String filepath){
	File infile = new File(filepath);
	FileInputStream instream = null;
	InputStreamReader instreamreader = null;
	BufferedReader br = null;
	String str = null;
	int count = 0;
	try {
		instream = new FileInputStream(infile);
	} catch (FileNotFoundException e) {
		System.out.println("文件"+filepath+"不存在");
		e.printStackTrace();
	}
	try{
		instreamreader = new InputStreamReader(instream,"UTF-8");
		br = new BufferedReader(instreamreader);
		while((str = br.readLine()) != null){
			str.trim();
			if(!str.isEmpty() && !str.startsWith("//"))
				count++;
			}
		}catch(IOException e){
			System.out.println("读取时发生I/O错误");
			e.printStackTrace();
		}finally{
				try {
					br.close();
					instreamreader.close();
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	return count;
	}

	public static void main(String args[]){
		Scanner s = new Scanner(System.in);
		String filepath = null;
		System.out.println("请输入文件的绝对路径:");
		filepath = s.next();
		s.close();
		int n = countEffectiveLines(filepath);
		System.out.println("共有" + n + "行有效的代码");
	}
}
