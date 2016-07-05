package com.qunar.effectivelines;

/**
 * Created by Charmy on 2016/6/30.
 */
public class Main {
    public static void main(String args[]){
        EffectiveLines elines=new EffectiveLines();
        String filename ="C:\\Users\\Charmy\\Desktop\\sr_lda\\src\\core\\algorithm\\lda\\LDA.java";
        int linesNumber=elines.countlines(filename);
        System.out.println( linesNumber);
    }
}
