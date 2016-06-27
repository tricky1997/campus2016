package CountMostImport;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 获取src文件夹中所有Java文件的路径
 */

public class JavaFilesPath
{

       List<String> m_JavaFilesPathList = new LinkedList<>();     //src目录中所有Java文件路径
       LinkedList<File> m_ScanFileList = new LinkedList<>();    //要浏览的目录,用栈存储（列表当栈用）
       
       
       //获得所有java文件路径
       public  List<String> GetJavaFliesPath(String srcPath){
    	       RecordFilesPath(srcPath);     //记录所有Java文件路径
    	   return m_JavaFilesPathList;
       }
       
   	   /* 扫描并保存指定目录srcPath下，所有java文件路径 */
       public void RecordFilesPath(String srcPath)
       {
    	  File srcFile = new File(srcPath);
    	  m_ScanFileList.push(srcFile);
    	  
    	  //开始遍历目录
    	 while(m_ScanFileList.size() != 0)
    	  {
    		  File p_RootFile =  m_ScanFileList.pop();    //访问栈顶元素并弹出
    		  File[] files = p_RootFile.listFiles();     //当前目录下所有子文件对象
    		  for(File file:files){
    			  if(file.isDirectory()){    //是目录
    				  m_ScanFileList.push(file);     //压入目录栈的栈顶
    			  }
    			  else{     //不是目录，是文件
    				String p_FilePath = file.toString();
  	                if (p_FilePath.endsWith(".java"))     //是java文件
  	                {
  	                	m_JavaFilesPathList.add(p_FilePath);    //将java文件路径添加到路径列表
  	                }
    			  }				  
    		  }
    	  }  	   
       }
}
