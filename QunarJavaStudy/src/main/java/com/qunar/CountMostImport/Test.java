package com.qunar.CountMostImport;


import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> rst = null;
        try {
            rst = CountMostImport.MostImportedClass("D:\\java-idea\\invoicing_zh\\src");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("存在某些错误，请查实。");
        }
        for (String s : rst) {
            System.out.println(s);
        }
    }
}
