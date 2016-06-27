package CountMostImport;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么
 * 思路：先把源文件目录中所有java文件找出，然后统计这些文件中，import次数排前10的类
 */

public class Main {

	 private static List<Map.Entry<String, Integer>> m_MaxImportList;     //存储impot最多的类
	 private static List<String> m_JavaFilesPath;      //存储源文件目录下所有java文件路径
	
	public static void main(String[] args) {
		String srcPath = Paths.get("src").toAbsolutePath().toString(); // 源目录src的路径
		m_JavaFilesPath =  new JavaFilesPath().GetJavaFliesPath(srcPath); // 获取目录src下所有JAVA文件的路径
	
		m_MaxImportList = new CountImportNum().getMaxImportClass(m_JavaFilesPath);    //统计import次数
		
		//输出import次数前10的结果
		  for(int i=0;i < 10 && i<m_MaxImportList.size();i++)
	        {
	            System.out.println("class: "+m_MaxImportList.get(i).getKey()
	            		+ "   value:"+m_MaxImportList.get(i).getValue());
	        
	        }

	}

}
