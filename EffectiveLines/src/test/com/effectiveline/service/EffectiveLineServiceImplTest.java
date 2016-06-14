package test.com.effectiveline.service;

import com.effectiveline.service.impl.EffectiveLineServiceImpl;
import org.junit.Test;

/**
 * Created by lenovo on 2016/6/7.
 */
public class EffectiveLineServiceImplTest {

    private EffectiveLineServiceImpl mEffectiveLineService = new EffectiveLineServiceImpl();

    @Test
    public void isEffectiveLine(){
        assert(mEffectiveLineService.isEffectiveLine("") == false);
        assert(mEffectiveLineService.isEffectiveLine("//") ==false);
        assert(mEffectiveLineService.isEffectiveLine("   ") ==false);
        assert(mEffectiveLineService.isEffectiveLine("/** abdfsd **/") ==false);
        assert(mEffectiveLineService.isEffectiveLine("int a = 0") ==true);
    }

    @Test
    public void countEffectiveLine(){
        String relativelyPath = System.getProperty("user.dir");
        String path = relativelyPath + "\\src\\test\\com\\effectiveline\\service\\EffectiveLineServiceImplTest.java";

        System.out.println(mEffectiveLineService.countEffectiveLine(path));
        assert(mEffectiveLineService.countEffectiveLine(path)==23);
        // absdef

    }

}