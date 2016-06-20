package com.qunar.homework.ExchangeRate;

import com.qunar.homework.Units.After.AfterWorkNothing;
import com.qunar.homework.Units.Display.NothingDisplay;
import com.qunar.homework.Units.File.XLSParser;
import com.qunar.homework.Units.FrameWork;
import com.qunar.homework.Units.Input.InputWebXLSHandler;
import com.qunar.homework.Units.Work.NothingWork;

/**
 * Created by Administrator on 2016/6/20.
 */
public class ExchangeRate {
    public ExchangeRate(){
        FrameWork frameWork = new FrameWork(new InputWebXLSHandler(),
                new AfterWorkNothing(),new XLSParser(),new NothingWork(),new NothingDisplay());
        frameWork.run("");
    }
}
