package com.cmi.dao.impl;

import com.cmi.dao.ICMICodeFileOpt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/6/5.
 */
public class ICMICodeFileOptImpl implements ICMICodeFileOpt {

    @Override
    public List<String> genImportPackage(String filePath) {
        List<String> packPathArr = new ArrayList<String>();

        File file = new File(filePath);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.trim().replaceAll(" +"," ");
                String[] strArr = tempString.split(" ");
                if(strArr.length == 2 && strArr[0].equals("import"))
                {
                    String packPath = strArr[1].substring(0, strArr[1].length()-1);
                    packPathArr.add(packPath);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return packPathArr;
    }
}
