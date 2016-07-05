package com.qunar.dan;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wudan
 * @version 0.1
 * @since 2016-06-21
 */
public class EffectiveLines {
    /**
    *  @param line every line of a file
    *  @param pattern patterns for comment(stored in arraylist)
    *  @return true or false(if the line is comment line)
    */
    int isNonsenceLine(String line,ArrayList<Pattern> pattern) {

        for (int i = 0; i < pattern.size(); ++i) {
            Matcher m = pattern.get(i).matcher(line);
            if (m.find()) return (1 + i);
        }
        return 0;
    }

    /**
    *   @param pattern regular expression of all comment line
    *   @return patterns of all comments
    */
    ArrayList<Pattern> getPattern(ArrayList<String> pattern){
        ArrayList<Pattern> res = new ArrayList<>();

        for (String t:pattern){
            res.add(Pattern.compile(t));
        }
        return res;
    }

    /**
     * main function
     * @param args command line arguments
     */
    public static void main(String args[]){
        // String to be scanned to find the pattern.
        String line;

        //match empty line(all space)
        String emptyLine = "^\\s*$";
        //match single line comment
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
                            validLineCount++;
                            //System.out.println(line);
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
                        //start of block comments
                        blockCommentStartFlag = true;
                        break;
                    }
                    case 4: {
                        //end of block comments
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
