package org.qunar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zhang ruixiong on 2016/6/29 0003.
 */
public class CountMostImport {
    private static List<String> imports = new ArrayList<String>();
    private static String[] result = new String[10];
    private static HashMap<String,Integer> importCounts = new HashMap<String,Integer>();
    public static void main(String[] args){
        traversal();
        insertHashMap();
        setResult();
        for (int i=0;i<10;i++){
            System.out.print(result[i]+"  ");
            System.out.println(importCounts.get(result[i]));
        }
    }
    private static void traversal(){
        File dir = new File("./homework/src/main/java/org/qunar");
        List<File> files = new ArrayList<File>();
        files = traversalFile(dir);
        imports = traversalImports(files);
    }
    private static List<File> traversalFile(File dir){
        List<File> files = new ArrayList<File>();
        if (dir.isFile() && dir.getName().endsWith(".java")) {
            files.add(dir);
            return files;
        }
        else if (dir.isFile() && !dir.getName().endsWith(".java")){
            return files;
        }
        File[] fs = dir.listFiles();
        for (int i=0;i<fs.length;i++){
            files.addAll(traversalFile(fs[i]));
        }
        return files;
    }
    private static List<String> traversalImports(List<File> files){
        List<String> imports = new ArrayList<String>();
        for (int i=0;i<files.size();i++){
            Scanner scanner = null;
            try {
                scanner = new Scanner(files.get(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //只考虑import出现在行首
            while (scanner.hasNext()){
                String codeLine = scanner.nextLine();
                if (codeLine.startsWith("import ")){
                    int startIndex = codeLine.indexOf("import")+6;
                    int endIndex = codeLine.indexOf(";",startIndex);
                    String className = codeLine.substring(startIndex,endIndex).trim();
                    imports.add(className);
                }
            }
        }
        return imports;
    }
    private static void insertHashMap(){
        for (int i=0;i<imports.size();i++){
            if (!importCounts.containsKey(imports.get(i)))
                importCounts.put(imports.get(i),1);
            else importCounts.put(imports.get(i),importCounts.get(imports.get(i))+1);
        }
        importCounts.put("",0);
    }
    private static void setResult(){
        for (int i=0;i<10;i++){
            result[i] = "";
        }
        Iterator<String> iter = importCounts.keySet().iterator();
        while (iter.hasNext()){
            int minIndex = 0;
            for (int i=0;i<10;i++){
                if (importCounts.get(result[i]) < importCounts.get(result[minIndex]))
                    minIndex = i;
            }
            String key = iter.next();
            if (importCounts.get(key) > importCounts.get(result[minIndex]))
                result[minIndex] = key;
        }
    }
}
