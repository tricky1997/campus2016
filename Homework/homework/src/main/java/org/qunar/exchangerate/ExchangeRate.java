package org.qunar.exchangerate;

import java.io.DataOutput;

/**
 * Created by zhang ruixiong on 2016/6/29 0003.
 */
public class ExchangeRate {
    public static void main(String[] args){
        //与网络交互爬取所需数据
        NetConversation nc = new NetConversation();
        String result = nc.getResult();
        //将爬取得html数据中提取所需的数据
        DataHandler dataHandler = DataHandler.getInstance(result);
        //将所需数据以excel的形式输出
        dataHandler.output();
    }
}
