package com.qunar.homework.CountMostImport;

import com.qunar.homework.Units.After.AfterWorkSortMap;
import com.qunar.homework.Units.Display.CountDisplay;
import com.qunar.homework.Units.File.FilesParser;
import com.qunar.homework.Units.FrameWork;
import com.qunar.homework.Units.Input.InputLocalFileHandler;
import com.qunar.homework.Units.Work.CountMostImportWorker;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CountMostImport {
    public CountMostImport(String name){
        FrameWork frameWork = new FrameWork(new InputLocalFileHandler(),
                new AfterWorkSortMap(),new FilesParser(),new CountMostImportWorker(),new CountDisplay());
        frameWork.run(name);
    }
}
