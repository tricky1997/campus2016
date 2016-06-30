package com.qunar.helang.exchangeRate;

/**
 * Created by lactic_h on 6/20/16.
 */



import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CircularRedirectException;

import org.apache.http.client.params.ClientPNames;

import org.apache.http.impl.client.DefaultHttpClient;

//import org.apache.http.cookie.Cookie;

//import org.apache.http.util.EntityUtils;


public class GetHtml{


    public static void main(String[] args)throws ClientProtocolException, IOException
    {
        String url= "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
        System.out.println(GetHtml.getHtml(url));
    }

    public static String getHtml(String url) throws ClientProtocolException, IOException
    {

        String cookie_2 = firstRequest(url);
        System.out.println(cookie_2);

        String cookie_3 = secondRequest(url, cookie_2);
        System.out.println(cookie_3);

        return thirdRequest(url, cookie_3+";"+cookie_2);
    }


    public static String firstRequest(String url)throws ClientProtocolException, IOException
    {
        System.out.println("-------------firstRequest");


        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);

        HttpGet get1 = new HttpGet(url);
        get1.addHeader("Host", "www.pbc.gov.cn");
        get1.addHeader("Connection", "keep-alive");
        // get1.addHeader("Upgrade-Insecure-Requests", "1");
        get1.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
        get1.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get1.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");


        HttpResponse response_1 = client.execute(get1);
        System.out.println("Response Code: " +response_1.getStatusLine());
        Header[] cookieHeaders_1= response_1.getHeaders("Set-Cookie");


        String cookieString="";
        for(Header h: cookieHeaders_1){
            System.out.println(h);
            cookieString+=h.getValue();
        }

        BufferedReader rd_1 = new BufferedReader(new InputStreamReader(response_1.getEntity().getContent()));
        String line_1 = null;

        Pattern pattern= Pattern.compile("\\s*eval\\((.*)\\)");
        Matcher matcher= null;
        String jsCode=null;
        while((line_1 = rd_1.readLine()) != null) {
            matcher= pattern.matcher(line_1);
            if(matcher.find()){
                jsCode=matcher.group(1);
                //	System.out.println(jsCode);
            }
        }

        if(jsCode==null){
            System.out.println("false");
        }

        cookieString+=";";
        cookieString+=runJs(jsCode);

        return cookieString;
    }

    public static String secondRequest(String url, String cookieString)throws ClientProtocolException, IOException
    {
        System.out.println("-------------secondRequest");


        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);

        HttpGet get2 = new HttpGet(url);
        get2.addHeader("Host", "www.pbc.gov.cn");
        get2.addHeader("Connection", "keep-alive");
        get2.addHeader("Upgrade-Insecure-Requests", "1");
        get2.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
        get2.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get2.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");

        get2.addHeader("Cookie", cookieString);

        HttpResponse response_2 = client.execute(get2);
        System.out.println("Response Code: " +response_2.getStatusLine());
        Header[] cookieHeaders_2= response_2.getHeaders("Set-Cookie");


        String cookieString_3="";
        for(Header h: cookieHeaders_2){
            System.out.println(h);
            cookieString_3+=h.getValue();
        }

        return cookieString_3;

    }

    public static String thirdRequest(String url, String cookieString)throws ClientProtocolException, IOException
    {
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);

        System.out.println("-------------");
        System.out.println("-------------");

        HttpGet get3 = new HttpGet(url);
        get3.addHeader("Host", "www.pbc.gov.cn");
        get3.addHeader("Connection", "keep-alive");
        get3.addHeader("Upgrade-Insecure-Requests", "1");
        get3.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36");
        get3.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get3.addHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");

        get3.addHeader("Cookie", cookieString);

        HttpResponse response_3 = client.execute(get3);
        System.out.println("Response Code: " +response_3.getStatusLine());

        BufferedReader rd_3 = new BufferedReader(  new InputStreamReader(response_3.getEntity().getContent()));
        StringBuffer sb= new StringBuffer();
        String line_3 = "";
        while((line_3 = rd_3.readLine()) != null) {
            sb.append(line_3);
        }

        return sb.toString();
    }



    public static String runJs(String jsCode) {
        String cookieString="";
        try{
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("javascript");
            String s= jsCode;
            s="var plainJs="+s;
            engine.eval(s);
            String result= (String) engine.get("plainJs");
            result= result.substring(0,result.length()-16);

            String cookieFunc= "var templateCookie;var confirm;var challengeCookie;function getCookie() {templateCookie= \"wzwstemplate=\"+KTKY2RBD9NHPBCIHV9ZMEQQDARSLVFDU(template.toString()) + \"; path=/\";var confirm = QWERTASDFGXYSF();challengeCookie= \"wzwschallenge=\"+KTKY2RBD9NHPBCIHV9ZMEQQDARSLVFDU(confirm.toString()) + \"; path=/\";}getCookie();";
            String newJs=result+cookieFunc;
            engine.eval(newJs);

            String templateCookie= (String) engine.get("templateCookie");
            //?String confirm= (String) engine.get("confirm");
            String challengeCookie= (String) engine.get("challengeCookie");
            System.out.println(templateCookie);
            //?System.out.println(confirm);
            System.out.println(challengeCookie);

            // cookieString += "wzwstemplate="+templateCookie + "; path=/";
            // cookieString +=";";
            // cookieString += "wzwschallenge="+challengeCookie + "; path=/";

            cookieString += templateCookie.split(";")[0] ;
            cookieString +=";";
            cookieString += challengeCookie.split(";")[0] ;

        }catch (Exception e){
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return cookieString;
    }

}
