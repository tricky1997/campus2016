
package me.helang.countMostImport;

import java.io.IOException;
import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


//输入为一个pkgName
//输出为一个Set<String> classnames

public class DealWithPkg{

    public static void main(String[] args){
        
        DealWithPkg test= new DealWithPkg();
        test.showFunction();
    }




    public void showFunction(){

        String pkgName="java.util.regex";
        Set<String> jarResult= getClassFromPkg(pkgName);

        System.out.println("--------I am just a line!------");
        System.out.println(pkgName);


        for(String s: jarResult){
            System.out.println("\t\t\t"+s);
        }

        String dirPkgName="me.lactic.testPkgDir";
        Set<String> dirResult= getClassFromPkg(dirPkgName);

        System.out.println("--------I am just a line!------");
        System.out.println(dirPkgName);

        for(String s: dirResult){
            System.out.println("\t\t\t"+s);
        }
    }


    Set<JarFile> jfs; 
    public DealWithPkg(){

        //getJARs()在整个dealWithPkg中只需要执行一次，因为返回的结果都是那些JarFile
        //所以把 jfs设计为全局变量
        jfs= getJARs();
    }



    public Set<String> getClassFromPkg(String pkgName){

        String pkgPath = pkgName.replace(".", "/");
        // String packagePath = packageName.replace(".", "/")+"/";

        Set<String> classNames= new HashSet<String>();

        ClassLoader loader= Thread.currentThread().getContextClassLoader();
        URL url= loader.getResource(pkgPath);

        //如果ClassLoader能搜索到pkgPath
        if(url!=null){ 
            String type = url.getProtocol();
            
            //如果通过目录路径搜索到pkgPath
            if (type.equals("file")) {  
                classNames = searchFromDir(url.getPath(), pkgName);
            }

            //如果通过Jar文件搜索到pkgPath
            else if (type.equals("jar")) {
                try {
                    JarFile jarFile = new JarFile(url.getPath());
                    searchFromJAR(jarFile, pkgName, classNames);
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        //如果ClassLoader搜索不到pkgPath，通过遍历JARs来搜索。
        else{
            searchFromJARs(pkgPath, classNames);
            //返回从所以JARS中获取的pkg中的classnames
        }
        return classNames;
    }



    public Set<String> searchFromDir(String pkgPath, String pkgName){
        Set<String> classNames= new HashSet<String>();

        File pkgDir= new File(pkgPath);
        String filename;
        //System.out.println(pkgDir.isDirectory());
        for(File f: pkgDir.listFiles(new ClassFileFilter())){
            filename= pkgName+"."+f.getName().replace(".class","");
            classNames.add(filename);
            // System.out.println(filename);
        }

        return classNames;
    }



    //因为searchFromJAR()需要返回一个boolean值来判断该jar包是否包含该pkgPath
    //所以searchFromJAR()的返回值不能为一个Set<String>，所以该函数的参数需要传入一个Set<String>
    public boolean searchFromJAR(JarFile jf, String pkgPath, Set<String> classNames){
        boolean flag= false;
      //    String pkgPath= pkgName.replace(".","/")+"/";
        Enumeration<JarEntry> jarEntries= jf.entries();
        while(jarEntries.hasMoreElements()){
            JarEntry jarEntry= jarEntries.nextElement();
            if(!jarEntry.isDirectory()){
                String entryName= jarEntry.getName();
                if(entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(pkgPath)){
                    // System.out.println(entryName);
                    // classNames.add(entryName.replace(".class","").replace("/","."));

                    entryName=entryName.replace(".class","").replace("/",".");
                    //  System.out.println(entryName);
                    classNames.add(entryName);
                    flag= true;
                }
            }
        }
        return flag;
    }



    public void searchFromJARs(String pkgPath, Set<String> classNames){
        for(JarFile jf : jfs){
            if(searchFromJAR(jf, pkgPath, classNames))
                //如果某个jar中找到pkgName，就不用继续搜索其它jar包了，所以返回true，然后break
                break;
        }
    }


    private Set<JarFile> getJARs(){
        Set<String> jars=new HashSet<String>();
        Set<String> dirs=new HashSet<String>();

        String bsl=System.getProperty("sun.boot.class.path");
        String ecl=System.getProperty("java.ext.dirs");
        String acl=System.getProperty("java.class.path");
        String searchPath= bsl+":"+ecl+":"+acl;
       
        for(String s: searchPath.split(":")){
            if (s.endsWith("jar")){
                jars.add(s);
            } else
                dirs.add(s);
        }
        
        // for(String s: jars){
        //     System.out.println(s);
        // }
        // System.out.println("_______________");

        Set<JarFile> jfs= new HashSet<JarFile>();
        JarFile jf= null;
        for(String s: jars){
            try{
                jf= new JarFile(s);
                jfs.add(jf);
            } catch(IOException e){
            //    e.printStackTrace();
            //!!! this is an exception "/Home/jre/lib/sunrsasign.jar (No such file or directory)".
            }
        }
        return jfs;
        /*
            // for(String s: jars){
            //      System.out.println(s);
            // }
            // System.out.println("\t----------------");

            // for(String s: dirs){
            //      System.out.println(s);
            // }
        */
    }

}



class ClassFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname){
        if(pathname.getName().endsWith(".class"))
            return true;
        else
            return  false;
    }
}
