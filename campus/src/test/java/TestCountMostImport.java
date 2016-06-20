import org.junit.Test;

import java.io.File;

/**
 * Created by andrew on 2016/6/20.
 */
public class TestCountMostImport {
    @Test
    public void testListAllJavaFile(){
        CountMostImport countMostImport = new CountMostImport("C:\\Users\\andrew\\IdeaProjects\\campus2016\\campus\\src\\main\\java");
        System.out.println(countMostImport.listAllJavaFile(new File("C:\\Users\\andrew\\IdeaProjects\\campus2016\\campus\\src\\main\\java")));
    }
}
