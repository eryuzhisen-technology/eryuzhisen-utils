package com.eryuzhisen.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eryuzhisen.utils.log.ErYuLogger;

/**
 * 
 * 
 * @author huangmiao
 * @version $Id: JpushUtil.java, v 0.1 2017年2月28日 上午10:27:31 huangmiao Exp $
 */
public class JpushUtil {
	private static String masterSecret = "ec27f87231d7e5b44048e0b2";
	private static String appKey = "d8ed0757732b7d5a1093a139";
	private static String pushUrl = "https://api.jpush.cn/v3/push";
	private static boolean apns_production = true;
	private static int time_to_live = 86400;
	private static final String ALERT = "推送信息";

	/**
	 * 极光推送
	 */
	public static  void jPush(List<String> alias) {
		try {
			String result = push(pushUrl, alias, ALERT, appKey, masterSecret,
					apns_production, time_to_live);
			JSONObject resData = new JSONObject(result);
			if (resData.has("error")) {
				ErYuLogger.info("针对别名为" + alias + "的信息推送失败！");
				JSONObject error = new JSONObject(resData.get("error"));
				ErYuLogger.info("错误信息为：" + error.get("message").toString());
			}
			ErYuLogger.info("针对别名为" + alias + "的信息推送成功！");
		} catch (Exception e) {
			ErYuLogger.error("针对别名为" + alias + "的信息推送失败！", e);
		}
	}

	/**
	 * 组装极光推送专用json串
	 * 
	 * @param alias
	 * @param alert
	 * @return json
	 */
	public static JSONObject generateJson(List<String> alias, String alert,
			boolean apns_production, int time_to_live) {
		JSONObject json = new JSONObject();
		JSONArray platform = new JSONArray();// 平台
		platform.put("android");
		platform.put("ios");

		JSONObject audience = new JSONObject();// 推送目标
		JSONArray alias1 = new JSONArray();
		alias1.put(alias);
		audience.put("alias", alias1);

		JSONObject notification = new JSONObject();// 通知内容
		JSONObject android = new JSONObject();// android通知内容
		android.put("alert", alert);
		android.put("builder_id", 1);
		JSONObject android_extras = new JSONObject();// android额外参数
		android_extras.put("type", "infomation");
		android.put("extras", android_extras);

		JSONObject ios = new JSONObject();// ios通知内容
		ios.put("alert", alert);
		ios.put("sound", "default");
		ios.put("badge", "+1");
		JSONObject ios_extras = new JSONObject();// ios额外参数
		ios_extras.put("type", "infomation");
		ios.put("extras", ios_extras);
		notification.put("android", android);
		notification.put("ios", ios);

		JSONObject options = new JSONObject();// 设置参数
		options.put("time_to_live", Integer.valueOf(time_to_live));
		options.put("apns_production", apns_production);

		json.put("platform", platform);
		json.put("audience", audience);
		json.put("notification", notification);
		json.put("options", options);
		return json;
	}

	/**
	 * 推送方法-调用极光API
	 * 
	 * @param reqUrl
	 * @param alias
	 * @param alert
	 * @return result
	 */
	public static String push(String reqUrl, List<String> alias, String alert,
			String appKey, String masterSecret, boolean apns_production,
			int time_to_live) {
		String base64_auth_string = encryptBASE64(appKey + ":"
				+ masterSecret);
		String authorization = "Basic " + base64_auth_string;
		
		return HttpClientUtil.getPostResponseForHeader(reqUrl, "UTF-8",
				generateJson(alias, alert, apns_production, time_to_live)
						.toString(), new Header[] { new Header("Authorization", authorization),new Header("Content-Type", "application/json;charset=UTF-8") });
	}
	
	/**
	 * 
	 */
	public static String encryptBASE64(String str) {
		byte[] key = str.getBytes();
		String strs = new String(Base64.encode(key));
		return strs;
	}
	
	public static void main(String[] args) {
		List<String> alias = new ArrayList<String>();
		alias.add("10467");
		JpushUtil.jPush(alias);
	}
}
