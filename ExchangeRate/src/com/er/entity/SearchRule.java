package com.er.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2016/6/7.
 */
public class SearchRule {

    private String mUrl;

    private int mTimeOut;

    private Map<String,String>  mParams;

    private int mMethod;

    private String mUserAgent;

    public final static int METHOD_POST = 0;

    public final static int METHOD_GET = 1;

    public SearchRule(){
        mUrl = null;
        mTimeOut = 3000;
        mParams = new HashMap<String,String>();
        mMethod = METHOD_POST;
        mUserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)";
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getmTimeOut() {
        return mTimeOut;
    }

    public void setmTimeOut(int mTimeOut) {
        this.mTimeOut = mTimeOut;
    }

    public Map<String, String> getmParams() {
        return mParams;
    }

    public void setmParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

    public int getmMethod() {
        return mMethod;
    }

    public void setmMethod(int mMethod) {
        this.mMethod = mMethod;
    }

    public String getmUserAgent() {
        return mUserAgent;
    }

    public void setmUserAgent(String mUserAgent) {
        this.mUserAgent = mUserAgent;
    }
}
