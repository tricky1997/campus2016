package org.qunar.exchangerate;

import java.io.DataOutput;

/**
 * Created by zhang ruixiong on 2016/6/29 0003.
 */
public class ExchangeRate {
    public static void main(String[] args){
        NetConversation nc = new NetConversation();
        String result = nc.getResult();
        DataHandler dataHandler = DataHandler.getInstance(result);
        dataHandler.output();
    }
}
