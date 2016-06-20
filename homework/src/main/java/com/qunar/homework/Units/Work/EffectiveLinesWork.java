package com.qunar.homework.Units.Work;

import com.qunar.homework.Units.Interface.WorkHandler;

/**
 * Created by Administrator on 2016/6/20.
 */
public class EffectiveLinesWork implements WorkHandler{
    Integer effectiveNums;
    boolean mulLines;

    //双引号中间的多层无效
    public EffectiveLinesWork(){
        effectiveNums = new Integer(0);
        mulLines = false;

    }
    public boolean getWorkDone(String line) {
        line.trim();
        //单纯的以双斜杠开头，空行，只有｝的行，多行注释
        //对于一行多次使用多行注释的情况真真的没招了
        //跨越多行的字符串也没考虑
        if(line.length()>2){
            boolean dong = false;
            boolean cite = false;
            for(int i=1;i<line.length();i++){
                if(line.charAt(i-1)=='"' ){
                    cite = !cite;
                }
                if(!cite && line.charAt(i-1) == '/' && line.charAt(i) == '*' && !mulLines){
                    if(i-2>0) justIfOneLineEffect(line.substring(0, i - 1));
                    mulLines = !mulLines;
                    dong = true;
                }
                if(!cite && line.charAt(i-1) == '*' && line.charAt(i) == '/' && mulLines){
                    if(i+1<line.length()) justIfOneLineEffect(line.substring(i+1));
                    mulLines = !mulLines;
                    dong = true;
                }
            }
            if(dong) return true;

        }
        else {
            if(line.startsWith("*/") && mulLines){
                mulLines = false;
                return true;
            }
            else{
                if(line.startsWith("/*") && !mulLines){
                    mulLines = true;
                    return true;
                }
            }
        }
        if(mulLines) return true;

        justIfOneLineEffect(line);

        return true;
    }

    private void justIfOneLineEffect(String line){
        if(line.startsWith("\\") || line.equals("") || (line.startsWith("}") && line.length()==1)){
        }
        else{
            effectiveNums += 1;
        }
    }

    public Object getData() {
        return effectiveNums;
    }
}
