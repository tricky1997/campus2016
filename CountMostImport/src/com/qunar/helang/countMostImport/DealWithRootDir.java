package me.helang.countMostImport;


import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

//输入为一个 String rootDirName
//输出为一个 List<File> javaFileList


public class DealWithRootDir{

    public static void main(String[] args) {
        DealWithRootDir test= new DealWithRootDir();
        test.showFunction();
    }

    public void showFunction(){
    	String rootDirName = "/Users/lactic_h/Documents/Test/testDir";    
    	for(File f: getAllJaveFile(rootDirName)){
    		System.out.println(f.getPath());
    	}
    }


	
	String rootDirName=null;
	List<File> javaFileList;


    public List<File> getAllJaveFile(String rootDirName){

        List<File> javaFiles= new ArrayList<File>();
        Stack<File> dirStack= new Stack<File>();

        try{
            dirStack.push(new File(rootDirName));
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }

        File currentFile=null;
        while(!dirStack.isEmpty()){
            currentFile= dirStack.pop();
            for (File f:currentFile.listFiles(new JavaFileFilter())) {
                javaFiles.add(f);
            }
            for (File f:currentFile.listFiles(new DirFilter())) {
                dirStack.push(f);
            }
        }
        return javaFiles;
    }

}


class JavaFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname){
        if(pathname.getName().endsWith(".java"))
            return true;
        else
            return  false;
    }
}

class DirFilter implements FileFilter {
    @Override
    public boolean accept(File pathname){
        if(pathname.isDirectory())
            return true;
        else
            return  false;
    }
}
