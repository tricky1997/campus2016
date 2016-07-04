package org.qunar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EffectiveLines {
    private static String line = null;
    private static int notePrefix = 0;
    public static void main(String[] args) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("./homework/src/main/java/org/qunar/EffectiveLines.java")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int number = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (isEmptyOrSingleNote()) continue;
                if (isOneOfNotes()) continue;
                number++;
            }
            System.out.println(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean isEmptyOrSingleNote(){
        if (line.equals("")) return true;
        if (line.charAt(0) == '/' && line.charAt(1) == '/') return true;
        if (line.charAt(0) == '/' && line.charAt(1) == '*' &&
                line.charAt(line.length()-2) == '*' && line.charAt(line.length()-1) == '/') return true;
        return false;
    }
    private static boolean isOneOfNotes(){
        int prefixs = countNote();
        int currentPres = prefixs+notePrefix;
        if (currentPres == 0 && prefixs == 0){
            notePrefix = currentPres;
            return false;
        }
        notePrefix = currentPres;
        return true;
    }
    private static int countNote(){
        int n=0;
        for (int i=0;i<line.length()-1;i++){
            if (line.charAt(i)=='/' && line.charAt(i+1)=='*') {
                n++;
                i++;
            }
            else if (line.charAt(i) == '*' && line.charAt(i+1) == '/') {
                n--;
                i++;
            }
        }
        return n;
    }
}
