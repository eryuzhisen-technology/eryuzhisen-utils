package com.eryuzhisen.utils;

import java.io.ByteArrayInputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.Callback.CalbackBodyType;
import com.aliyun.oss.model.PutObjectRequest;
/**
 * 阿里云上传
 * 
 * @author huangmiao
 * @version $Id: AliUploadUtil.java, v 0.1 2017年3月1日 下午11:27:30 huangmiao Exp $
 */
public class AliUploadUtil {
	
	private static String endpoint = "eryu.oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIgTxi7RibaQGl";
    private static String accessKeySecret = "Nc3VZapvIka1spwB9zYtmhKOgUsEeC";
    private static String bucketName = "eryu";
    
    // 您的回调服务器地址，如http://oss-demo.aliyuncs.com:23450或http://0.0.0.0:9090
    private static final String callbackUrl = "<yourCallbackServerUrl>";
	
	public static void upload() {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String content = "Hello OSS";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "key", 
                    new ByteArrayInputStream(content.getBytes())); 
            
            Callback callback = new Callback();
            callback.setCallbackUrl(callbackUrl);
            callback.setCallbackHost("oss-cn-hangzhou.aliyuncs.com");
            callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},"
                    + "\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},"
                    + "\\\"my_var1\\\":${x:var1},\\\"my_var2\\\":${x:var2}}");
            callback.setCalbackBodyType(CalbackBodyType.JSON);
            callback.addCallbackVar("x:var1", "value1");
            callback.addCallbackVar("x:var2", "value2");
            putObjectRequest.setCallback(callback);
            
            ossClient.putObject(putObjectRequest);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
	}
}
