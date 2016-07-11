/**
 * Created by zhaoxin on 16-6-30.
 */
import java.io.File;
import java.io.FilenameFilter;
import java.lang.String;

public class JavaFileFilter implements FilenameFilter{
    public boolean accept(File dir,String name)
    {
        if(name.endsWith(".java"))
        {
            return true;
        }
        else
            return false;
    }
}
