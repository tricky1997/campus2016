package com.qunar.homework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 6/26/2016.
 */
public class JavaFiles {
    private List<String> fileLists;


    public JavaFiles() {
        fileLists = new ArrayList<String>();
    }

    public void getFileLists(File file)
    {
        if(file==null) return;
        File[] files = file.listFiles();
        if(files==null)
            return;
        for (File f:files) {
            if(f.isDirectory()){
                getFileLists(f);
            }
            else
            {
                if(isJavaFile(f))
                {
                    fileLists.add(f.getPath());


                }
            }
        }
    }

    public List<String> getFileLists() {
        return fileLists;
    }

    public boolean isJavaFile(File file)
    {
        boolean result = false;
        String str = file.getPath();
        if(str.endsWith(".java"))
            result = true;

        return result;
    }
}
