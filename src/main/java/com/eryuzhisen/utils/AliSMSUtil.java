package com.eryuzhisen.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.Header;

import com.aliyuncs.utils.ParameterHelper;
import com.eryuzhisen.utils.log.ErYuLogger;
import com.google.gson.reflect.TypeToken;

/**
 * 阿里云短信发送
 * 
 * @author huangmiao
 * @version $Id: AliSMSUtil.java, v 0.1 2017年3月22日 下午4:13:04 huangmiao Exp $
 */
public class AliSMSUtil {
    
    private static String SEND_PHONE_URL = "http://sms.aliyuncs.com/";
    
    private static String format = "JSON";
    private static String version= "2016-09-27";
    private static String accessKeyId = "LTAIBt7gJYzIVjoB";
    private static String accessKeySecret = "qBwW0MGHjB4tceYjUE66RKoAkXqpsP"; 
    private static String signatureMethod = "HMAC-SHA1";
    private static String signatureVersion = "1.0";
    private static String action = "SingleSendSms";
    private static String signName = "耳语之森";
    private static String templateCode = "SMS_57100095";
    private static String connector = "&";
    
    private static String jointParam(String code,String phoneNums) throws UnsupportedEncodingException {
        String timestamp = ParameterHelper.getISO8601Time(new Date());
        String nonce = UUID.randomUUID().toString();
        String paramString = "{\"no\":\""+code+"\"}";
        StringBuffer strSb = new StringBuffer("AccessKeyId=").append(accessKeyId)
        .append(connector)
        .append("Action=").append(action)
        .append(connector)
        .append("Format=").append(format)
        .append(connector)
        .append("ParamString=").append(URLEncoder.encode(paramString, "UTF-8"))
        .append(connector)
        .append("RecNum=").append(phoneNums)
        .append(connector)
        .append("SignName=").append(URLEncoder.encode(signName, "UTF-8"))
        .append(connector)
        .append("SignatureMethod=").append(signatureMethod)
        .append(connector)
        .append("SignatureNonce=").append(nonce)
        .append(connector)
        .append("SignatureVersion=").append(signatureVersion)
        .append(connector)
        .append("TemplateCode=").append(templateCode)
        .append(connector)
        .append("Timestamp=").append(URLEncoder.encode(timestamp,"UTF-8"))
        .append(connector)
        .append("Version=").append(version);
        
        return strSb.toString();
    }
    
    private static String stringToSign(String param) throws UnsupportedEncodingException {
        return "POST"+connector+URLEncoder.encode("/","UTF-8")+connector+URLEncoder.encode(param,"UTF-8").replaceAll("\\+", "%20")
                .replaceAll("\\*", "%2A").replaceAll("%7E", "~");
    }
    
    private static String getSign(String strSign) throws Exception {
        return HMACSHA1.HmacSHA1Encrypt(strSign, accessKeySecret+connector);
    }
    
    @SuppressWarnings("unchecked")
    public static boolean sendPhoneCode(String code,String phoneNums) {
        try {
            String param = jointParam(code, phoneNums);
            String strSign = stringToSign(param);
            String signature = URLEncoder.encode(getSign(strSign), "UTF-8");
            String body = "Signature="+signature+connector+param;
            Header[] headers = {new Header("Content-Type","application/x-www-form-urlencoded")};
            String result = HttpClientUtil.getPostResponseForHeader(SEND_PHONE_URL, "UTF-8", body,headers);
            Map<String,String> resultMap = (Map<String, String>) JsonUtil.fromJson(result,new TypeToken<Map<String,String>>() {  
            }.getType());
            
            if(resultMap.containsKey("Code") && resultMap.get("Code").length() > 0) {//失败
                ErYuLogger.error("[send phonecode] phonenumber =" + phoneNums +",code="+ code+" result:"+result);
            } else {
                ErYuLogger.info("[send phonecode] phonenumber =" + phoneNums +",code="+ code);
                return true;
            }
        } catch (Exception e) {
            ErYuLogger.error("[send phonecode] phonenumber =" + phoneNums +",code="+ code+" error:",e);
        }
        return false;
    }
    
    public static void main(String[] args) {
        /*String ss = "POST&%2F&AccessKeyId%3Dtestid%26Action%3DSingleSendSms%26Format%3DXML%26ParamString%3D%257B%2522name%2522%253A%2522d%2522%252C%2522name1%2522%253A%2522d%2522%257D%26RecNum%3D13098765432%26RegionId%3Dcn-hangzhou%26SignName%3D%25E6%25A0%2587%25E7%25AD%25BE%25E6%25B5%258B%25E8%25AF%2595%26SignatureMethod%3DHMAC-SHA1%26SignatureNonce%3D9e030f6b-03a2-40f0-a6ba-157d44532fd0%26SignatureVersion%3D1.0%26TemplateCode%3DSMS_1650053%26Timestamp%3D2016-10-20T05%253A37%253A52Z%26Version%3D2016-09-27";
        try {
            System.out.println(URLEncoder.encode(HMACSHA1.HmacSHA1Encrypt(ss, "testsecret&"),"UTF-8"));
        } catch (Exception e) {
            
        }*/
        AliSMSUtil.sendPhoneCode("757665", "17721454470");
    }
}
