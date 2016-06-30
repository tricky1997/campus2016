package com.qunar.test.effectivelines;
import com.qunar.java.effectivelines.EffectiveLines;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 16-6-27
 * Time: 下午4:25
 * 功能：判断java文件的有效行数的单元测试类
 */
public class TestEffectiveLines {

    @Test
    public void testStatisticsFileLines(){
           EffectiveLines el = new EffectiveLines();
           int count = el.statisticsFileLines("TestFile.java");
           assertEquals("判断有效行数是否正确",count,93);
    }

}
