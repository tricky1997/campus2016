package work2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.URI;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils; 

import org.jsoup.*;  
import org.jsoup.helper.Validate;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class my_work {
	public static void main(String[] args)throws IOException
	{
//htmlunit+jsoup
		
        final WebClient wc = new WebClient();  
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
        wc.getOptions().setCssEnabled(false); //禁用css支持  
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常  
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待  
        final HtmlPage pages = wc.getPage("http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html");  
        String pageXmls = pages.asXml(); //以xml的形式获取响应文本  
        //System.out.println(pageXmls);
        
        //jsoup解析文档  
        Document docs = Jsoup.parse(pageXmls);
        Elements links = docs.select("[height = 22]").select("[align = left]").select("a[href]");
        //System.out.println(links.size());
        //System.out.println(" " + links2.size());
       
        String[] out_href = new String[30];
        int i = 0;
        for(Element link : links)
        {
        	if(i > 29)
        	    break;
        	
        	//System.out.println(link.toString());
        	String linkhref = link.attr("href");
        	//System.out.println(linkhref);
        	String new_link = "http://www.pbc.gov.cn"+linkhref;
        	//System.out.println(new_link);
        	out_href[i] = new_link;
        	i++;
        }
        
        if(i != 29)
        {
        	Elements link = docs.select("a[tagname]").select("[style=cursor:pointer]");
        	//System.out.println(link.size());
        	Element m_link = link.first();
        	String m_href = m_link.attr("tagname");
        	String new_m_href = "http://www.pbc.gov.cn" + m_href;
        	final HtmlPage page = wc.getPage(new_m_href);
        	String pageXml = page.asXml();
    	    Document doc = Jsoup.parse(pageXml);
    	    Elements all_links = doc.select("[height = 22]").select("[align = left]").select("a[href]");
    	    for(Element tmp : all_links)
    	    {
            	if(i > 29)
            	    break;
            	String linkhref = tmp.attr("href");
            	String new_link = "http://www.pbc.gov.cn"+linkhref;
            	out_href[i] = new_link;
            	i++;
    	    }
        }
        
        String[] output = new String[30];
        int out_put_i = 0;
		
        for(String new_link : out_href)
        {
    	    final HtmlPage page = wc.getPage(new_link);
    	    String pageXml = page.asXml();
    	    Document doc = Jsoup.parse(pageXml);
    	//System.out.println(doc.toString());
    	//System.out.println(doc.title());
    	    Elements contents = doc.select("[class = content]");
    	//System.out.println(contents.size());
    	    //System.out.println(contents.text());
    	    output[out_put_i] = contents.text();
    	    out_put_i++;
        }
        
        /*
        for(String tmp:output)
        {
        	System.out.println(tmp);
        }
        */
        
        regex_analyse_excel_out(output);
		
	
        
        
		
/* httpclient+jsoup test1
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        
        HeaderIterator it = response.headerIterator("Set-Cookie");
        while (it.hasNext()) {
        System.out.println(it.next());
        }
        
        httpGet.addHeader("Set-Cookie","wzwsconfirm=798c50164cd669a4c1ccc40c1c759944; path=/");
        response = client.execute(httpGet);
        
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("执行状态码 : " + statusCode);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity,"UTF-8"));
        httpGet.releaseConnection();
    
        Connection conn = Jsoup.connect(url); // 建立与url中页面的连接
        Document doc = conn.get(); // 解析页面
        System.out.println(doc.toString());
        Elements links = doc.select("a[href]"); // 获取页面中所有的超链接
        System.out.println(links.size());
*/
		
    

	}
	
	private static void regex_analyse_excel_out(String[] ss)
	{
		try{
			OutputStream instream = new FileOutputStream(new File("out.xls"));
			WritableWorkbook workbook = Workbook.createWorkbook(instream);
		    WritableSheet sheet = workbook.createSheet("First Sheet",0);
		    Label date = new Label(0,0,"日期");
		    sheet.addCell(date);
		    Label meiyuan = new Label(0,1,"美元");
		    sheet.addCell(meiyuan);
		    Label ouyuan = new Label(0,2,"欧元");
		    sheet.addCell(ouyuan);
		    Label gangbi = new Label(0,3,"港币");
		    sheet.addCell(gangbi);
		    
		    int i = 1;
		    
		    
		for(String s:ss)
		{
		   
			
		   String regex1 = "(\\d+?.{1,4}元)对人民币(\\d+(\\.\\d+))元";
		   Matcher matcher1 = Pattern.compile(regex1).matcher(s);
		   String regex2 = "(\\d{4}年\\d{1,2}月\\d{1,2}日)";
		   Matcher matcher2 = Pattern.compile(regex2).matcher(s);
		   matcher2.find();
		   Label new_date = new Label(i,0,matcher2.group(0));
		   sheet.addCell(new_date);
		   
	       
	       int count = 0;
	       int j = 1;
	       while(matcher1.find())
	       {
	    	   if(count == 4)
	    		   break;
	    	   if(count == 2)
	    	   {
	    		   count++;
	    		   continue;
	    	   }
	           //System.out.println(matcher.group(1)+":"+matcher.group(2));
	    	   Label renmingbi = new Label(i,j,matcher1.group(2));
	    	   sheet.addCell(renmingbi);
	    	   count++;
	    	   j++;
	       }
	       
	       i++;
	       
	      
	       
	       
		}
	    workbook.write();
	    workbook.close();
		}catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}

}
