package com.qunar.homework.Units;

import com.qunar.homework.Units.Interface.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class FrameWork {
    InputHandler inputHandler;
    AfterHandler afterHandler;
    FileHandler fileHandler;
    WorkHandler workHandler;
    DisplayHandler displayHandler;
    public FrameWork(InputHandler inputHandler,AfterHandler afterHandler,FileHandler fileHandler,WorkHandler workHandler,DisplayHandler displayHandler){
        this.inputHandler = inputHandler;
        this.afterHandler = afterHandler;
        this.fileHandler = fileHandler;
        this.workHandler = workHandler;
        this.displayHandler = displayHandler;
    }
    public void run(String name) {
        List<File> fileList = inputHandler.getInputFileList(name);
        try {
            Object midRes = fileHandler.getWorkDone(fileList,workHandler);
            Object res = afterHandler.getWorkDone(midRes);
            displayHandler.display(res);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
