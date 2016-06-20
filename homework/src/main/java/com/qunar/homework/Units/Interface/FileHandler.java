package com.qunar.homework.Units.Interface;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public interface FileHandler {
    public Object getWorkDone(List<File> fileList,WorkHandler workHandler) throws IOException;
}
