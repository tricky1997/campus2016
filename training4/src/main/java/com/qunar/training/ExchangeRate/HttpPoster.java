package com.qunar.training.ExchangeRate;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 任意一天的汇率可以从中国银行官网上查询，传入货币代号，开始日期，结束日期即可获得改天汇率的页面
 * <p/>
 * Created by shining.cui on 2016/6/5.
 */
public class HttpPoster {
    /**
     * 由于返回值是全部代码，因此建议日期只查询一天，这样比较容易取得结果，因为每天的汇率会有很多信息，并且有分页功能
     *
     * @param currencyCode 货币代码 港币：1315，欧元：1326 美元：1316
     * @param beginDataStr 开始日期，格式为：2016-05-01
     * @param endDataStr   结束如期，格式为:2016-06-01
     * @return 返回包含查询日期汇率内容的页面全部信息，即html代码
     * @throws IOException
     */
    public static BufferedReader getResponseContent(String currencyCode, String beginDataStr, String endDataStr) throws IOException {
        String basicUrl = "http://srh.bankofchina.com/search/whpj/search.jsp";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(basicUrl);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("pjname", currencyCode));
        formParams.add(new BasicNameValuePair("erectDate", beginDataStr));
        formParams.add(new BasicNameValuePair("nothing", endDataStr));
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        httpPost.setEntity(uefEntity);
        System.out.println("executing request " + httpPost.getURI());
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        return bufferedReader;
    }

}

