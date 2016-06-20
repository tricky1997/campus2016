package com.qunar.homework.EffectiveLines;

import com.qunar.homework.Units.After.AfterWorkNothing;
import com.qunar.homework.Units.Display.EffectDisplay;
import com.qunar.homework.Units.File.FilesParser;
import com.qunar.homework.Units.FrameWork;
import com.qunar.homework.Units.Input.InputLocalFileHandler;
import com.qunar.homework.Units.Work.EffectiveLinesWork;

/**
 * Created by Administrator on 2016/6/20.
 */
public class EffectiveLines {
    public EffectiveLines(String name) {
        FrameWork frameWork = new FrameWork(new InputLocalFileHandler(),
                new AfterWorkNothing(), new FilesParser(), new EffectiveLinesWork(), new EffectDisplay());
        frameWork.run(name);
    }
}
