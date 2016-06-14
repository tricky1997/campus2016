package test.com.cmi.dao;

import com.cmi.dao.impl.ICMICodeFileOptImpl;
import org.junit.Test;

import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public class ICMICodeFileOptImplTest {


    @Test
    public void genImportPackage() {

        String relativelyPath = System.getProperty("user.dir");
        String path = relativelyPath + "\\src\\com\\cmi\\dao\\impl\\ICMICodeFileOptImpl.java";

        ICMICodeFileOptImpl codeFileOpt = new ICMICodeFileOptImpl();
        List<String> strArr = codeFileOpt.genImportPackage(path);

        for(int i = 0; i < strArr.size(); i++)
        {
            System.out.println(strArr.get(i));
        }

    }

}
