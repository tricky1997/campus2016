package task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @dest
 * 统计一个java文件的有效行数
 * @author li
 * @throws FileNotFoundException 
 */
public class EffectiveLines {
   
    private static int lineNum = 0;
	
    public static void count(String fileDir,String fileName) throws FileNotFoundException{
		File file = new File(fileDir+"\\"+fileName+".java");
		InputStream input = new FileInputStream(file);
		BufferedReader readBuf = new BufferedReader(new InputStreamReader(input));
		try {
			String line = readBuf.readLine();
			line.trim();
			while(line !=null){
				Pattern p = Pattern.compile("\\s*|\t");
				Matcher m = p.matcher(line);
				//去除字符串中的空格、制表符
				line = m.replaceAll("");
				if (!line.startsWith("//")&& !line.equals("")){
					lineNum++;
				}
				line = readBuf.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		    
            Scanner sca = new Scanner(System.in);
            
            System.out.println("请输入文件路径！");
            String fileDir = sca.nextLine();
            System.out.println("请输入文件名！");
            String fileName = sca.nextLine();
            try {
				count(fileDir,fileName);
				System.out.println(fileName+"总的有效行数为："+lineNum+"行");
			} catch (FileNotFoundException e) {
				System.out.println("未找到指定的文件！");
			}
	}
}
