package com.qunar.dan;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dan on 2016/6/22.
 */
public class CountMostImport {
    private Pattern importClassPatten;
    private Pattern classDeclarePatten;
    private Pattern javaFileName;
    private String classNameTemp;
    private Map<String,Integer> results;

    /*
        Constructor
     */
    CountMostImport(String arg0,String arg1){
        importClassPatten = Pattern.compile(arg0);
        classDeclarePatten = Pattern.compile(arg1);
        javaFileName = Pattern.compile(".*\\.java$");
        results = new HashMap<>();
    }
    /*
        Check string is java file name ?
     */
    public boolean isJavaFileName(String name){
        Matcher m = javaFileName.matcher(name);
        return m.find();
    }

    /*
        Check is match?
     */
    public int isMatch(String line) {
        Matcher matcher1 = importClassPatten.matcher(line);
        Matcher matcher2 = classDeclarePatten.matcher(line);

        if(matcher1.find()) {
            //System.out.println(matcher.find());
            String temp = matcher1.group();

            int commaIndex = temp.indexOf(';');

            this.classNameTemp = temp.substring(6,commaIndex);

            return 0;
        } else if(matcher2.find()){
            return 1;
        }

        return 2;
    }
    /*
        Parse a single file
     */
    public void parseFile(String fileName) {
        File file = new File(fileName);
        FileInputStream is = null;
        String line;

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = br.readLine()) != null) {
                int t = isMatch(line);
                if(t == 1) {
                    break;
                } else if(t == 0) {
                    if(results.containsKey(classNameTemp)) {
                        results.put(classNameTemp,results.get(classNameTemp)+1);
                    } else {
                        results.put(classNameTemp,1);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*
        Parse a folder
     */
    public void parseFolder(String foldName,String PathOverHead){
        String newPath = PathOverHead + foldName + '/';
        File fold = new File(newPath);
        File[] files = fold.listFiles();
        //System.out.println(newPath);

        // DFS Traverse the Folder
        for(int i = 0;i < files.length;++i) {
            //System.out.println(files[i].getName());
            if(files[i].isDirectory()) {
                parseFolder(files[i].getName(),newPath);
            } else if(files[i].isFile() && isJavaFileName(files[i].getName())){
                parseFile(newPath + files[i].getName());
            }
        }
    }

    public Map<String,Integer> getResults(){
        return sortByValue(this.results);
    }
    /*
        Sort Map by Value
        Reference : http://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
     */
    public Map<String,Integer> sortByValue(Map<String,Integer> map){
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public static void main(String args[]){
        String importStr = "^(import)\\s[a-zA-Z0-9.]+;\\s*$";
        String classStr = "(class|interface|abstract)";

        CountMostImport dan = new CountMostImport(importStr,classStr);

        dan.parseFolder("java","C:\\Users\\dan\\AndroidStudioProjects\\MyActionBar\\app\\src\\main\\");

        int count = 0;
        for(Map.Entry<String,Integer> entry : dan.getResults().entrySet()){
            if(count >= 10)break;//Only print top 10 class
            System.out.print(entry.getKey() + ": ");
            System.out.println(entry.getValue());
            ++count;
        }
    }
}