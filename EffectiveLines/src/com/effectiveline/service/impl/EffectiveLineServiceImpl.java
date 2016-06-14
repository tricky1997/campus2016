package com.effectiveline.service.impl;

import com.effectiveline.cfg.Configuration;
import com.effectiveline.service.IEffectiveLineService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lenovo on 2016/6/7.
 */
public class EffectiveLineServiceImpl implements IEffectiveLineService {

    @Override
    public int countEffectiveLine(String path) {

        int count = 0;

        File file = new File(path);
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));

            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if(isEffectiveLine(tempString)){
                    count++;
                }else{
                    //It's a invalid line. Do nothing
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

        return count;
    }

    public boolean isEffectiveLine(String path)
    {
        path = path.trim();
        if(path.length() == 0) {
            return false;
        }
        else if(path.length() >= 2) {
            for(String invalidLine: Configuration.INVALID_LINE) {
                if(path.substring(0, 2).equals(invalidLine)) {
                    return false;
                }
                else{
                    // do nothing
                }
            }
        }
        else{
            // do nothing
        }
        return true;

    }

}
