package com.eryuzhisen.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.eryuzhisen.utils.log.ErYuLogger;

public class HttpClientUtil {
	// 获得ConnectionManager，设置相关参数
	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
	private static int connectionTimeOut =10000;
	private static int socketTimeOut = 25000;
	private static int maxConnectionPerHost = 20;
	private static int maxTotalConnections = 200;
	private static int connectionManageTimeOut = 5000; 

	// 标志初始化是否完成的flag
	private static boolean initialed = false;

	// 初始化ConnectionManger的方法
	public static void SetPara() {
		manager.getParams().setConnectionTimeout(connectionTimeOut);
		manager.getParams().setSoTimeout(socketTimeOut);
		manager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
		manager.getParams().setMaxTotalConnections(maxTotalConnections);
		initialed = true;
	}

	public static void setConnectionTimeOut(int connectionTimeOut) {
		HttpClientUtil.connectionTimeOut = connectionTimeOut;
		initialed = false;
	}

	public static void setConnectionManageTimeOut(int connectionManageTimeOut) {
		HttpClientUtil.connectionManageTimeOut = connectionManageTimeOut;
		initialed = false;
	}

	public static void setSocketTimeOut(int socketTimeOut) {
		HttpClientUtil.socketTimeOut = socketTimeOut;
		initialed = false;
	}

	public static void setMaxConnectionPerHost(int maxConnectionPerHost) {
		HttpClientUtil.maxConnectionPerHost = maxConnectionPerHost;
		initialed = false;
	}

	public static void setMaxTotalConnections(int maxTotalConnections) {
		HttpClientUtil.maxTotalConnections = maxTotalConnections;
		initialed = false;
	}

	// 通过get方法获取网页内容
	@SuppressWarnings("finally")
	public static String getGetResponseWithHttpClient(String url, String encode) {
		HttpClient client = new HttpClient(manager);
		client.getParams().setConnectionManagerTimeout(connectionManageTimeOut); 
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		GetMethod get = new GetMethod(url);
		get.setFollowRedirects(true);

		String result = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			client.executeMethod(get);
			// 在目标页面情况未知的条件下，不推荐使用getResponseBodyAsString()方法
			// String strGetResponseBody = post.getResponseBodyAsString();
			BufferedReader in = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), get.getResponseCharSet()));

			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\n");
			}
			in.close();
			result = resultBuffer.toString();
			// iso-8859-1 is the default reading encode
			result = HttpClientUtil.ConverterStringCode(resultBuffer.toString(), get.getResponseCharSet(), encode);
		} catch (Exception e) {
			result = "";
			System.out.println(e);
			e.printStackTrace();
		} finally {
			get.releaseConnection();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public static String getPostResponseWithHttpClient(String url, String encode) {
		HttpClient client = new HttpClient(manager);
		client.getParams().setConnectionManagerTimeout(connectionManageTimeOut);
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		PostMethod post = new PostMethod(url);
		post.setFollowRedirects(false);
		StringBuffer resultBuffer = new StringBuffer();
		String result = null;
		try {
			client.executeMethod(post);
			BufferedReader in = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\n");
			}
			in.close();
			result = HttpClientUtil.ConverterStringCode(resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			result = "";
		} finally {
			post.releaseConnection();
			return result;
		}
	}

	@SuppressWarnings("finally")
	public static String getPostResponseWithHttpClient(String url,String encode, NameValuePair[] nameValuePair) {
		HttpClient client = new HttpClient(manager);
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		PostMethod post = new PostMethod(url);
		post.setRequestBody(nameValuePair);
		post.setFollowRedirects(false);

		String result = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			client.executeMethod(post);
			BufferedReader in = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\n");
			}
			in.close();
			result = HttpClientUtil.ConverterStringCode(resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			result = "";
		} finally {
			post.releaseConnection();
			return result;
		}
	}
	
	@SuppressWarnings({ "deprecation", "finally" })
	public static String getPostResponseForHeader(String url,String encode, String body,Header[] headers) {
		HttpClient client = new HttpClient(manager);
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		PostMethod post = new PostMethod(url);
		if(headers != null && headers.length > 0) {
			for(int i=0;i<headers.length;i++){
				post.addRequestHeader(headers[i]);
			}
		}
		post.setRequestBody(body);
		post.setFollowRedirects(false);
		
		String result = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			client.executeMethod(post);
			BufferedReader in = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\n");
			}
			in.close();
			result = HttpClientUtil.ConverterStringCode(resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			result = "";
		} finally {
			post.releaseConnection();
			return result;
		}
	}
	
	@SuppressWarnings({ "finally", "deprecation" })
	public static String getPostResponseForBody(String url,String encode, String body) {
		HttpClient client = new HttpClient(manager);
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		PostMethod post = new PostMethod(url);
		post.addRequestHeader("Content-Type","application/json;charset=UTF-8");
		post.setRequestBody(body);
		post.setFollowRedirects(false);

		String result = null;
		StringBuffer resultBuffer = new StringBuffer();
		try {
			client.executeMethod(post);
			BufferedReader in = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), post.getResponseCharSet()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				resultBuffer.append(inputLine);
				resultBuffer.append("\n");
			}
			in.close();
			result = HttpClientUtil.ConverterStringCode(resultBuffer.toString(), post.getResponseCharSet(), encode);
		} catch (Exception e) {
			ErYuLogger.error("http client error:",e);
			result = "";
		} finally {
			post.releaseConnection();
			return result;
		}
	}
	
	

	public static String putHttpResult(String url, String reqBody){ 
		String retStr = null;
		try{
			if (url !=null) {
				HttpClient htpClient = new HttpClient();
				PutMethod putMethod = new PutMethod(url);	       
				putMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8" );
	        
				putMethod.setRequestEntity(new StringRequestEntity(reqBody,"application/json", null));
//	        	postMethod.setRequestBody(reqBody);
				try{
					int statusCode = htpClient.executeMethod( putMethod );
//	            	loggerError.error("statusCode : "+statusCode+"  sendNotice body : "+ reqBody);
					if(statusCode != HttpStatus.SC_OK){
						ErYuLogger.error("Put Http Exception (Error "+statusCode+"): " + url);
					} else {  
						retStr = new String(putMethod.getResponseBody(),"UTF-8");
					}
				}catch(Exception e){
					ErYuLogger.error("Put Http Exception : " + url,e);
	        	}finally{
	        		putMethod.releaseConnection();
	        	}
			}
		}catch(Exception e){
			ErYuLogger.error("Put Http Exception : " + url,e);
        	//System.out.println(e.toString());
		}
		return retStr;
    }
	
	@SuppressWarnings("deprecation")
	public static String getDeleteResponseWithHttpClient(String url, String reqBody) throws Exception{
		String resStr = "";
		HttpClient htpClient = new HttpClient(manager);
		if (!initialed) {
			HttpClientUtil.SetPara();
		}
		
        MyDELETEMethod delMethod = new HttpClientUtil().new MyDELETEMethod(url);
        
        delMethod.addRequestHeader( "Content-Type","application/json" );
        delMethod.setRequestBody( reqBody);
        try{
            int statusCode = htpClient.executeMethod( delMethod );
            if(statusCode != HttpStatus.SC_OK){
            	System.out.println("Method failed: "+delMethod.getStatusLine());
            }  
            resStr = new String(delMethod.getResponseBody(),"UTF-8");
        }catch(Exception e){
        	e.printStackTrace(System.out);
        }finally{
        	delMethod.releaseConnection();
        }
		return resStr;
    }
	
	
	class MyDELETEMethod extends PostMethod{
		@Override
	    public String getName() {
			return "DELETE";
	    }

		public MyDELETEMethod() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MyDELETEMethod(String uri) {
			super(uri);
			// TODO Auto-generated constructor stub
		}
	}
	
	
	
	private static String ConverterStringCode(String source, String srcEncode,String destEncode) {
		if (source != null) {
			try {
				return new String(source.getBytes(srcEncode), destEncode);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static String[] getLonTudeLatTude(String lat,String lon){
		String result = HttpClientUtil.getGetResponseWithHttpClient("http://www.anttna.com/goffset/goffset1.php?lat="+lat+"&lon="+lon, "utf-8");
		if(result.indexOf("\n") > 0)
			result = result.replaceAll("\n", "");
			
		String[] array = new String[2];
		array[0] = result.substring(result.indexOf(",") + 1);
		array[1] = result.substring(0, result.indexOf(","));
		
		return array;
	}

	
	public static void main(String[] args){
		//System.out.println(HttpClientUtil.getGetResponseWithHttpClient("http://www.anttna.com/goffset/goffset1.php?lat=31.171895075000002&lon=121.42982384999999", "utf-8"));
		
//		String array[] = HttpClientUtil.getLonTudeLatTude("31.171895075000002","121.42982384999999");
//		for(int i=0;i<array.length;i++){
//			System.out.println(array[i]);
//		}
	}
}
