package com.eryuzhisen.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	static String key = "gfA21deDRxBDFaT3NEvw5VYT1kMEIETDuIsiPq1LxjED";
	public static String decStringCode(String str) {
		String decStr = new String(Base64.decodeBase64(str));
		String mKey = Md5Util.getInstance().getMD5String(key).substring(7,18);
		int strLen = decStr.length();
		int keyLen = mKey.length();
		String code = "";
		for(int i=0;i<strLen;i++) {
			int k = i % keyLen;
			code +=(char)((int)decStr.toCharArray()[i] ^ (int)mKey.toCharArray()[k]);
		}
		return code;
	}
	
	public static String encStringCode(String str) {
		String mKey = Md5Util.getInstance().getMD5String(key).substring(7,18);
		int strLen = str.length();
		int keyLen = mKey.length();
		String code = "";
		for(int i=0;i<strLen;i++) {
			int k = i % keyLen;
			code +=(char)((int)str.toCharArray()[i] ^ (int)mKey.toCharArray()[k]);
		}
		return new String(Base64.encodeBase64(code.getBytes(), false, true));
	}
	
	//test
	public static void main(String[] args) {
		System.out.println(Base64Util.encStringCode("92"));
//		System.out.println(Base64Util.decStringCode("WgEE"));
	}
}
