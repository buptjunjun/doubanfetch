package org.junjun.douban.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HtmlUtls {
	
	public static String getHtml(String url) throws Exception 
	{
			String html="";
			 CloseableHttpClient httpclient = HttpClients.createDefault();
             HttpGet httpget = new HttpGet(url);
            
             httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;"); 
             httpget.setHeader("Accept-Language", "zh-cn"); 
             httpget.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36"); 
             httpget.setHeader("Accept-Charset", "gbk"); 
             httpget.setHeader("Keep-Alive", "300"); 
             httpget.setHeader("Connection", "Keep-Alive"); 
             httpget.setHeader("Cache-Control", "no-cache"); 
             HttpResponse response = httpclient.execute(httpget);
             HttpEntity entity = response.getEntity();
             html = EntityUtils.toString(entity); 
             httpclient.close();
	         return html;
	}
	
	public static void main(String [] args) throws Exception
	{
		String html = getHtml("https://api.douban.com/v2/movie/subject/1298744");
		System.out.println(html);
	}

}
