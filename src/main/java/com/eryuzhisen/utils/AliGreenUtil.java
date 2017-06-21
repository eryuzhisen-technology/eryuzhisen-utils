package com.eryuzhisen.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20161216.TextAntispamDetectionRequest;
import com.aliyuncs.green.model.v20161216.TextAntispamDetectionResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.eryuzhisen.utils.log.ErYuLogger;

public class AliGreenUtil {
    
    private static String accessKeyId = "LTAI0v5we18Wjvom";
    private static String accessKeySecret = "BxTJ58SAJ8hYHcIq8HhusFQTVrY9mP"; 
    
    public static boolean detection(String userId,String content) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        TextAntispamDetectionRequest textAntispamDetectionRequest = new TextAntispamDetectionRequest();
        //为防中文乱码，务必设置编码类型为utf-8
        textAntispamDetectionRequest.setEncoding("UTF-8");
        textAntispamDetectionRequest.setConnectTimeout(3000);
        textAntispamDetectionRequest.setReadTimeout(4000);
        //设置待检测文本
        List<Map<String, Object>> dataItems = new ArrayList<Map<String, Object>>();
        
        Map<String, Object> dataItem = new HashMap<String, Object>();
        dataItem.put("dataId", UUID.randomUUID().toString()); //数据id
        dataItem.put("content", content); //要检测的文本内容
        dataItem.put("postId", userId);  //发贴人的id, 可选, postId与postTime需同时填或者不填
        dataItem.put("postTime", new Date().getTime());//发帖时间, 可选, postId与postTime需同时填后者不填
        dataItems.add(dataItem);
        
        textAntispamDetectionRequest.setDataItems(JsonUtil.toJson(dataItems));
        
        /**
         * 设置自定义关键词的词库ID, 词库和关键词的定义前往绿网控制台定义和添加(https://yundun.console.aliyun.com/?p=cts#/greenWeb/config2),
         * 词库ID在添加词库时会自动生成,显示在控制台
         * 默认新增的词库自动在本接口生效, 如果想部分词库有用,请将以下参数指定改词库的ID
         */
        //textAntispamDetectionRequest.setCustomDicts(Arrays.asList("11001", "11002"));
        
        //超时设置
        textAntispamDetectionRequest.setConnectTimeout(4000);
        textAntispamDetectionRequest.setReadTimeout(4000);
        
        try {
            TextAntispamDetectionResponse textAntispamDetectionResponse = client.getAcsResponse(textAntispamDetectionRequest);
//            System.out.println(JsonUtil.toJson(textAntispamDetectionResponse));
            if ("Success".equals(textAntispamDetectionResponse.getCode())) {
                List<TextAntispamDetectionResponse.TextAntispamResult> textAntispamResults = textAntispamDetectionResponse.getTextAntispamResults();
                for (TextAntispamDetectionResponse.TextAntispamResult textAntispamResult : textAntispamResults) {
                    if(textAntispamResult.getIsSpam() != null && textAntispamResult.getIsSpam()){
                        return true;
                    }
                }
            }
        } catch (ServerException e) {
            ErYuLogger.error("AliGreenUtil detection:",e);
        } catch (ClientException e) {
            ErYuLogger.error("AliGreenUtil detection:",e);
        } catch (Exception e) {
            ErYuLogger.error("AliGreenUtil detection:",e);
        }
        return false;
    }
}
