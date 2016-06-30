
package me.helang.countMostImport;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



//输入为一个 String filename 或者 为一个File f
//输出为两个Map  classMap  和 pkgMap


public class DealWithSingleFile{

    public static void main(String[] args){

        Map<String, Integer> classMap= new HashMap<String, Integer>();
        Map<String,Integer> pkgMap= new HashMap<String, Integer>();

        DealWithSingleFile test= new DealWithSingleFile(classMap, pkgMap);
        test.showFunction();
    }


    void showFunction(){

        String filename="/Users/lactic_h/Documents/Test/TestImportSentence";
        dealWithSigleFile(new File(filename));

        System.out.println("show the classMap");
        for(String s: classMap.keySet()){
            System.out.println("\t"+s+"\t"+classMap.get(s));
        }
        System.out.println("show the pkgMap");
        for(String s: pkgMap.keySet()){
            System.out.println("\t"+s+"\t"+pkgMap.get(s));
        }
    }


    Map<String, Integer> classMap= null;
    Map<String,Integer> pkgMap= null;


    public DealWithSingleFile(Map<String, Integer> classMap, Map<String, Integer> pkgMap){
        this.classMap= classMap;
        this.pkgMap= pkgMap;
    }

    public DealWithSingleFile(){
        classMap= new HashMap<String, Integer>();
        pkgMap= new HashMap<String, Integer>();
    }


//    public void dealWithSigleFile(String filename) {
    public void dealWithSigleFile(File filename) {

        BufferedReader br = null;
        Set<String> classSet= new HashSet<String>();

        try {
            br = new BufferedReader(new FileReader(filename));
            String aLine= null;
        
            while (br.ready()) {
                aLine=br.readLine();
                if(isImportLine(aLine)){
                    String result=dealWithImportSentence(aLine);
                    if(result.endsWith(".*")){
                        String pkgName= result.substring(0,result.length()-2);
                        if(pkgMap.containsKey(pkgName)){
                            pkgMap.replace(pkgName,pkgMap.get(pkgName)+1);
                        }else{
                            pkgMap.put(pkgName,1);
                        }
                    }else{
                        classSet.add(result);
                    }
                }
            }

            for(String s: classSet){
                if(classMap.containsKey(s)){
                    classMap.replace(s, classMap.get(s)+1);
                }else{
                    classMap.put(s, 1);
                }
            }
  
        } catch (FileNotFoundException fnfe) {
            System.out.println("the file doesn't exist!");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException fce) {
                    fce.printStackTrace();
                }
            }
        }
    }
 

    //判断一行代码是否为一个import语句
    boolean isImportLine(String aLine){
        Pattern importPattern= Pattern.compile("\\s*import");
        Matcher importMatcher= importPattern.matcher(aLine);

        if(importMatcher.lookingAt())
            return true;
        else
            return false;
    }


    //返回处理后的import遇见，返回String的形式为"java.io.*;" 或者 "java.io.File"这两种形式。
    //因为static import 也只是import一个类中的部分，相当于只import一个类，处理为"java.io.File"这种形式。
    String dealWithImportSentence(String aLine){
        String[] segments= aLine.split("\\s+");
        if(!segments[1].equals("static")){
            return segments[1].substring(0,segments[1].length()-1);
        }else{
            String[] parts=segments[2].split("\\.");
            StringBuffer result= new StringBuffer(parts[0]);
            for(int i=1; i< parts.length-1; i++){
                result.append(".").append((parts[i]));
            }
            return result.toString();
        }
    }


}     




//-----------------------------

/*
    String dealWithImportSentence(String aLine, Set<String> classSet){
        String[] segments= aLine.split("\\s+");
        if(!segments[1].equals("static")){
            if(segments[1].endsWith("*;")){
                //return segments[1].substring(0,segments[1].length()-1);
                pkgName= segments[1].substring(0,segments[1].length()-1);
                if(pkgMap.containsKey(pkgName)){
                    pkgMap.put(pkgName,pkgMap.get(pkgName)+1);
                }else{
                    pkgMap.put(pkgName,1);
                }
                return pkgName;
            }
            else
                //return segments[1].substring(0,segments[1].length()-1);
                className= segments[1].substring(0,segments[1].length()-1);
        }else{
            String[] parts=segments[2].split("\\.");
            StringBuffer result= new StringBuffer(parts[0]);
            for(int i=1; i< parts.length-1; i++){
                result.append(".").append((parts[i]));
            }
            //return result.toString();
            className= result.toString();
        }

        classSet.add(className);
        return className;
    }
*/