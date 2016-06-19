import org.junit.Test;

import java.io.IOException;

/**
 * Created by andrew on 2016/6/19.
 */
public class TestEffectiveLines {
    @Test
    public void test(){
        try {
            System.out.println(EffectiveLines.countValidityLines("C:\\Users\\andrew\\IdeaProjects\\campus2016\\campus\\src\\main\\java\\EffectiveLines.java", "utf-8"));
        } catch (IOException e) {
        }
    }
}
