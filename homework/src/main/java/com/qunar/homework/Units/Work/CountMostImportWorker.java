package com.qunar.homework.Units.Work;

import com.qunar.homework.Units.Interface.WorkHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CountMostImportWorker implements WorkHandler{
    Map<String,Integer> classMap = new HashMap<String, Integer>();

    public boolean getWorkDone(String line) {
        line.trim();
        if(line.startsWith("import")){
            String className = line.substring(6,line.length()-1);
            className.trim();
            if(classMap.containsKey(className)){
                int num = classMap.get(className);
                num += 1;
                classMap.put(className,num);
            }
            else{
                classMap.put(className,1);
            }
        }
        return !line.startsWith("public");
    }

    public Object getData() {
        return classMap;
    }
}
