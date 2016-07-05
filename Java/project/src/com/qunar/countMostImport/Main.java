package com.qunar.countMostImport;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String args[]) {

        String filename ="C:\\Users\\Charmy\\Desktop\\sr_lda\\src\\core\\algorithm\\lda";
        CountMostImport countMostImport=new CountMostImport(filename);
        ArrayList<Map.Entry<String,Integer>> list =countMostImport.getTop10MostImportClass();
        for(int i=0;i<list.size()&&i<10;i++)
            System.out.println(list.get(i).getKey()+" : "+list.get(i).getValue());
    }
}
