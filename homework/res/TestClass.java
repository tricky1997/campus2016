package com.qunar.homework.EffectiveLines;

/**
 * 测试源代码有效行的源程序文件，包括多行注释、单行注释、空行、特殊情况等
 * 文件总共33行，有效代码行数为：8行
 * Created by zhaocai-luo on 2016/6/15.
 */

// 下一行是一个空行

// 下一行是一个制表符

public class TestClass {
    /**
     /* 这是一个奇怪的多行注释
     *
     /*
     */
    public void test(){/*  多行注释在有效代码行后面  */
        // 这是一个单行注释
        String string = "Hello World!";
        int number = 0;

        /* 这是一个只有一行的多行注释 */
        System.out.println(string); // 单行注释在有效代码行后面
    }
}

// /* 下面几行空行 */

    //

