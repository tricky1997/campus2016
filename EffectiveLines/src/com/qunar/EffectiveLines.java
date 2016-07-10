package com.qunar;

import java.io.*;
import java.util.Scanner;

public class EffectiveLines {

    /**
     * @param args
     */
    public static void main(String[] args) {
        testCountEffectiveLine();
    }

    /**
     * test for function countEffectiveLine
     */
    public static void testCountEffectiveLine() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a java file name: ");
        String filename = in.nextLine().trim();
        int ret = countEffectiveLine(filename);
        if (ret < 0) {
            System.out.println(filename + " is not a java file !");
        } else {
            System.out.println(filename + " has " + ret + " effective lines !");
        }
    }

    /**
     * @param filename a java file
     * @return -1 if filename is not a java file,
     * otherwise, return the number of effective line of the java file
     */
    public static int countEffectiveLine(String filename) {
        int ret = 0;

        //not a java file
        if (!filename.endsWith(".java")) {
            ret = -1;
        } else {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(new File(filename)));

                //read the file line by line
                while (true) {
                    String line = reader.readLine();
                    //read the end of the file
                    if (line == null) {
                        break;
                    }
                    line = line.trim();
                    //blank line or comment line
                    if (line.length() == 0 || line.startsWith("//")
                            || (line.startsWith("/*") && line.endsWith("*/"))) {

                    } else {
                        ret++;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}
