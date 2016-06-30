package com.qunar.helang.effectiveLines;

/**
 * Created by lactic_h on 6/15/16.
 */

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class EffectiveLines {

    public static void main(String[] args){
        showFunction();
    }

    public static void showFunction(){

        //String filename = "/Users/lactic_h/Code/src/learnJava/learnIO/LearnFileInputStream.java";
        File f= new File(Class.class.getClass().getResource("/").getPath());
        f= new File(f.getParentFile().getParentFile().getParentFile(),"test/UsedForTest.java");
        String filename=f.getAbsolutePath();

        EffectiveLines cl = new EffectiveLines(filename);
        System.out.println(cl.getCounts());

    }
//Class.class.getClass().getResource("/").getPath()

    File f= null;
    int Count= 0;

    Pattern blankPattern= Pattern.compile("\\s*");
    Matcher blankMatcher;
    Pattern commentPattern= Pattern.compile("\\s*//");
    Matcher commentMatcher;

    public EffectiveLines(String filename){

        f= new File(filename);
        if(!f.exists()){
            System.out.println(filename+" doesn't exist");
            System.exit(0);
        }
        if(!filename.endsWith(".java")){
            System.out.println(filename+"isn't a java file");
            System.exit(0);
        }

    }

    public int getCounts() {
        BufferedReader br= null;
        try {
            br = new BufferedReader(new FileReader(f));

            while(br.ready()){
                if(isValue(br.readLine())){
                    Count++;
                }
            }


        } catch (FileNotFoundException fnfe) {
            System.out.println("the file doesn't exist!");
            fnfe.printStackTrace();
        } catch (IOException ioe){
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
        return Count;
    }

    boolean isValue(String aLine){

        blankMatcher= blankPattern.matcher(aLine);
        if(blankMatcher.matches())
            return false;

        commentMatcher = commentPattern.matcher(aLine);
        if(commentMatcher.lookingAt())
            return false;

        else
            return true;

    }
}


