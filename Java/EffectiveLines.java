package com.qunar.dan;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dan on 2016/6/21.
 */
public class EffectiveLines {
    /*
    *   called every loop
    */
    int isNonsenceLine(String line,ArrayList<Pattern> pattern) {

        for(int i = 0;i < pattern.size();++i) {
            Matcher m = pattern.get(i).matcher(line);
            if(m.find()) return (1 + i);
        }
        return 0;
    }

    /*
    *   {@author:wudan}
    *   just call once
    */
    ArrayList<Pattern> getPattern(ArrayList<String> pattern){
        ArrayList<Pattern> res = new ArrayList<>();

        for (String t:pattern){
            res.add(Pattern.compile(t));
        }
        return res;
    }

    public static void main(String args[]){
        // String to be scanned to find the pattern.
        String line;

        // String pattern = "^\\s*/[*](.*)[*]/\\s*$";
        // String test = "T.{0,5}s";//match a word

        String emptyLine = "^\\s*$";//match space line
        String singleLineComment = "(\\s*/[*](.*)[*]/\\s*$)|(^\\s*//)";//match a comment line
        //match "/* bla bla"
        String blockCommentStart = "^\\s*/[*][^([*]/)]*\\s*$";
        //match "bla bla */"
        String blockCommentEnd = "^\\s*[^(/[*])]*[*]/\\s*$";

        //step 1: define all patterns
        EffectiveLines dan = new EffectiveLines();
        ArrayList<String> nonSenceLine = new ArrayList<>();
        nonSenceLine.add(emptyLine);
        nonSenceLine.add(singleLineComment);
        nonSenceLine.add(blockCommentStart);
        nonSenceLine.add(blockCommentEnd);

        //step 2: get Patterns
        ArrayList<Pattern> patterns = dan.getPattern(nonSenceLine);

        //step 3: init variables
        int validLineCount = 0;
        boolean blockCommentStartFlag = false;

        //step 4: read files
        File file = new File("./MainModule/src/com/qunar/dan/EffectiveLines.java");
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            while((line = br.readLine()) != null) {
                switch (dan.isNonsenceLine(line,patterns)) {
                    case 0: {
                        if(blockCommentStartFlag == false) {
                            //if(blockCommentStartFlag == false) {
                                validLineCount++;
                                //System.out.println(line);
                            //}
                        }
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        break;
                    }
                    case 3: {
                        //for block comment (currently not used)
                        blockCommentStartFlag = true;
                        break;
                    }
                    case 4: {
                        //for block comment (currently not used)
                        blockCommentStartFlag = false;
                        break;
                    }
                    default :{}
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Valid Line Number is: " + validLineCount);

        //        Pattern t = Pattern.compile(blockCommentStart);
        //
        //        Matcher mt = t.matcher("/**");
        //
        //        System.out.println(mt.find());

        //        // Create a Pattern object
        //        Pattern r = Pattern.compile(blockComment);

        //        // Now create matcher object.
        //        Matcher m = r.matcher(line);
        //        if (m.find()) {
        //            System.out.println("Found value: " + m.group() );
        //            //System.out.println("Found value: " + m.group(1) );
        //            //System.out.println("Found value: " + m.group(2) );
        //        } else {
        ////            System.out.println("NO MATCH");
        //        }
    }
}
