package com.pokerface.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class HttpUtil {
	
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	public static void sendGet(String url){
		HttpClient client = new HttpClient();
    	HttpMethod method = new GetMethod(url);
    	method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
        		new DefaultHttpMethodRetryHandler(3, false));
    	try {
	      // Execute the method.
	      int statusCode = client.executeMethod(method);

	      if (statusCode != HttpStatus.SC_OK) {
	        System.err.println("Method failed: " + method.getStatusLine());
	      }

	      // Read the response body.
	      byte[] responseBody = method.getResponseBody();

	      // Deal with the response.
	      // Use caution: ensure correct character encoding and is not binary data
	      System.out.println(new String(responseBody));

	    } catch (HttpException e) {
	      System.err.println("Fatal protocol violation: " + e.getMessage());
	      e.printStackTrace();
	    } catch (IOException e) {
	      System.err.println("Fatal transport error: " + e.getMessage());
	      e.printStackTrace();
	    } finally {
	      // Release the connection.
	      method.releaseConnection();
	    }
	}

	public static JSONObject getJSONObjectFromURL(String url) {
		try {
			String result = HttpUtil.doPost(url, "");
			JSONObject obj = new JSONObject(result);
			return obj;
		} catch (Exception e) {
			logger.error("getJSONObjectFromURL:"+url+" error!", e);
			return null;
		}
	}
	
	public static String doPost(String link, String param)
	{
		StringBuilder sb = new StringBuilder();
		HttpURLConnection conn = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try
		{
			URL url = new URL(link);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10*1000);
			pw = new PrintWriter(conn.getOutputStream());
			pw.write(param);
			pw.flush();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			String data;
			while ((data = br.readLine()) != null)
			{
				sb.append(data);
			}
		} catch (Exception e)
		{
			logger.error( String.format("[doPost] link : %s params : %s", link,param));
			logger.error("[doPost] exception : " + e );
			e.printStackTrace();
		} finally  {
			try{
				if (pw!=null)
					pw.close();
				if (br!=null)
					br.close();
				if (conn!=null)
					conn.disconnect();
			} catch (Exception e) {
				logger.error("[doPost] exception : " + e );
			}
		}
		return sb.toString();
	}
}
