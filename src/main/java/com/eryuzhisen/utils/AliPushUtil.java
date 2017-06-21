package com.eryuzhisen.utils;

import java.util.Date;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidResponse;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSResponse;
import com.aliyuncs.push.model.v20150827.PushNoticeToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushNoticeToAndroidResponse;
import com.aliyuncs.push.model.v20150827.PushNoticeToiOSRequest;
import com.aliyuncs.push.model.v20150827.PushNoticeToiOSResponse;
import com.aliyuncs.push.model.v20150827.PushRequest;
import com.aliyuncs.push.model.v20150827.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
/**
 * 阿里云推送消息
 * 
 * @author huangmiao
 * @version $Id: AliPushUtil.java, v 0.1 2017年3月22日 下午4:12:52 huangmiao Exp $
 */
public class AliPushUtil {
    private static long appKey = 23715216;
    private static DefaultAcsClient client;
    
    private static void initPara() {
        String accessKeyId = "LTAIlarV7up8VkEV";
        String accessKeySecret = "Z67pFC0Y8ki8N8ENpkkJG5gM8c76wr";
        String region = "cn-hangzhou";
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }
    
    public static String pushNoticeForAndroid_toAlias(String alias,String title,String message) throws Exception {
        initPara();
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        //推送内容需要保护，请使用HTTPS协议
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("ALIAS");
        androidRequest.setTargetValue(alias);
        androidRequest.setTitle(title);
        androidRequest.setSummary(message);

        PushNoticeToAndroidResponse pushNoticeToAndroidResponse = client.getAcsResponse(androidRequest);
        return pushNoticeToAndroidResponse.getRequestId();
    }

    public static String pushNoticeForAndroid_toAll(String title,String message) throws Exception {
        initPara();
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        //推送内容需要保护，请使用HTTPS协议
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("all");
        androidRequest.setTargetValue("all");
        androidRequest.setTitle(title);
        androidRequest.setSummary(message);
        //androidRequest.setAndroidExtParameters("{\"key1\":\"value1\",\"api_name\":\"PushNoticeToAndroidRequest\"}");

        PushNoticeToAndroidResponse pushNoticeToAndroidResponse = client
            .getAcsResponse(androidRequest);
        
        return pushNoticeToAndroidResponse.getRequestId();
    }

    public static String pushMessageForAndroid_toAll(String message) throws Exception {
        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        //推送内容需要保护，请使用HTTPS协议
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("all");
        androidRequest.setTargetValue("all");
        androidRequest.setMessage(message);

        PushMessageToAndroidResponse pushMessageToAndroidResponse = client
            .getAcsResponse(androidRequest);
        return pushMessageToAndroidResponse.getRequestId();
    }

    public static String pushMessageForAndroid_toAlias(String alias,String message) throws Exception {
        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        //推送内容需要保护，请使用HTTPS协议
        androidRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        androidRequest.setMethod(MethodType.POST);
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("ALIAS");
        androidRequest.setTargetValue(alias);
        androidRequest.setMessage(message);

        PushMessageToAndroidResponse pushMessageToAndroidResponse = client
            .getAcsResponse(androidRequest);
        
        return pushMessageToAndroidResponse.getRequestId();
    }

    public static String pushNoticeForIOS_toAlias(String alias,String message) throws Exception {
        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        //推送内容需要保护，请使用HTTPS协议
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        // iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV:表示开发环境, PRODUCT: 表示生产环境
        iOSRequest.setEnv("DEV");
        iOSRequest.setTarget("ALIAS");
        iOSRequest.setTargetValue(alias);
        iOSRequest.setSummary(message);
        //iOSRequest.setiOSExtParameters("{\"k1\":\"v1\",\"k2\":\"v2\"}");
        iOSRequest.setExt("{\"sound\":\"default\", \"badge\":\"42\"}");

        PushNoticeToiOSResponse pushNoticeToiOSResponse = client.getAcsResponse(iOSRequest);
        return pushNoticeToiOSResponse.getRequestId();
    }

    public static String pushNoticeForIOS_toAll(String message) throws Exception {
        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        //推送内容需要保护，请使用HTTPS协议
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        // iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV:表示开发环境, PRODUCT: 表示生产环境
        iOSRequest.setEnv("DEV");
        iOSRequest.setTarget("all");
        iOSRequest.setTargetValue("all");
        iOSRequest.setSummary(message);
        //iOSRequest.setiOSExtParameters("{\"k1\":\"v1\",\"k2\":\"v2\"}");
        iOSRequest.setExt("{\"sound\":\"default\", \"badge\":\"42\"}");

        PushNoticeToiOSResponse pushNoticeToiOSResponse = client.getAcsResponse(iOSRequest);
        return pushNoticeToiOSResponse.getRequestId();
    }

    public static String pushMessageForIOS_toAll(String summary,String message) throws Exception {
        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        //推送内容需要保护，请使用HTTPS协议
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        iOSRequest.setTarget("all");
        iOSRequest.setTargetValue("all");
        iOSRequest.setMessage(message);
        iOSRequest.setSummary(summary);

        PushMessageToiOSResponse pushMessageToiOSResponse = client.getAcsResponse(iOSRequest);
        return pushMessageToiOSResponse.getRequestId();
    }

    public static String pushMessageForIOS_toAlias(String alias,String summary,String message) throws Exception {
        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        //推送内容需要保护，请使用HTTPS协议
        iOSRequest.setProtocol(ProtocolType.HTTPS);
        //推送内容较长，请使用POST请求
        iOSRequest.setMethod(MethodType.POST);
        iOSRequest.setAppKey(appKey);
        iOSRequest.setTarget("ALIAS");
        iOSRequest.setTargetValue(alias);
        iOSRequest.setMessage(message);
        iOSRequest.setSummary(summary);

        PushMessageToiOSResponse pushMessageToiOSResponse = client.getAcsResponse(iOSRequest);
        return pushMessageToiOSResponse.getRequestId();
    }
    
    public static String push_toAll(String title,String summary,String message,Date pushDate,Date expireDate) throws Exception {
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget("all"); //推送目标: device:推送给设备; ALIAS:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
        pushRequest.setTargetValue("all"); //根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setDeviceType(3); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0; Android设备: 1; 全部: 3, 这是默认值.
        // 推送配置
        pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(message); // 消息的内容
        pushRequest.setSummary(summary); // 通知的摘要
        // 推送配置: iOS
        pushRequest.setiOSBadge("5"); // iOS应用图标右上角角标
        pushRequest.setiOSMusic("default"); // iOS通知声音
        //pushRequest.setiOSExtParameters("{\"k1\":\"ios\",\"k2\":\"v2\"}"); //自定义的kv结构,开发者扩展用 针对iOS设备
        pushRequest.setApnsEnv("DEV");
        // pushRequest.setRemind(true); // 当APP不在线时候，是否通过通知提醒
        // 推送配置: Android
        //pushRequest.setAndroidOpenType("3"); // 点击通知后动作,1:打开应用 2: 打开应用Activity 3:打开 url
        //pushRequest.setAndroidOpenUrl("http://www.baidu.com"); // Android收到推送后打开对应的url,仅仅当androidOpenType=3有效
        //pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}"); // 设定android类型设备通知的扩展属性
        // 推送控制
        // 推送控制
        if(pushDate != null) {
            final String pushTime = ParameterHelper.getISO8601Time(pushDate);
            pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        }
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        if(expireDate != null) {
            final String expireTime = ParameterHelper.getISO8601Time(expireDate); // 12小时后消息失效, 不会再发送
            pushRequest.setExpireTime(expireTime);
        }
        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        
        return pushResponse.getRequestId();
    }
    
    public static String push_toAlias(String alias,String title,String summary,String message,Date pushDate,Date expireDate) throws Exception {
        PushRequest pushRequest = new PushRequest();
        // 推送目标
        pushRequest.setAppKey(appKey);
        pushRequest.setTarget("all"); //推送目标: device:推送给设备; ALIAS:推送给指定帐号,tag:推送给自定义标签; all: 推送给全部
        pushRequest.setTargetValue("all"); //根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
        pushRequest.setDeviceType(3); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0; Android设备: 1; 全部: 3, 这是默认值.
        // 推送配置
        pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
        pushRequest.setTitle(title); // 消息的标题
        pushRequest.setBody(message); // 消息的内容
        pushRequest.setSummary(summary); // 通知的摘要
        // 推送配置: iOS
        pushRequest.setiOSBadge("5"); // iOS应用图标右上角角标
        pushRequest.setiOSMusic("default"); // iOS通知声音
        //pushRequest.setiOSExtParameters("{\"k1\":\"ios\",\"k2\":\"v2\"}"); //自定义的kv结构,开发者扩展用 针对iOS设备
        pushRequest.setApnsEnv("DEV");
        // pushRequest.setRemind(true); // 当APP不在线时候，是否通过通知提醒
        // 推送配置: Android
        //pushRequest.setAndroidOpenType("3"); // 点击通知后动作,1:打开应用 2: 打开应用Activity 3:打开 url
        //pushRequest.setAndroidOpenUrl("http://www.baidu.com"); // Android收到推送后打开对应的url,仅仅当androidOpenType=3有效
        //pushRequest.setAndroidExtParameters("{\"k1\":\"android\",\"k2\":\"v2\"}"); // 设定android类型设备通知的扩展属性
        // 推送控制
        if(pushDate != null) {
            final String pushTime = ParameterHelper.getISO8601Time(pushDate);
            pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
        }
        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        if(expireDate != null) {
            final String expireTime = ParameterHelper.getISO8601Time(expireDate); // 12小时后消息失效, 不会再发送
            pushRequest.setExpireTime(expireTime);
        }
        PushResponse pushResponse = client.getAcsResponse(pushRequest);
        
        return pushResponse.getRequestId();
    }

}
