/**
 * 
 */
package com.eryuzhisen.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.eryuzhisen.utils.log.ErYuLogger;

/**
 * <p>Description: 读取 PoolConfig.properties 配置文件</p>
 */

public class ConfigUtils {

	private static final String config = "config";

	private static Properties properties = new Properties();

	public ConfigUtils() {
	}

	public static Properties getAppProperties() {
		return properties;

	}

	/**
	 * s为KEY ，如果没有对应KEY值，返回默认值s1
	 * @param s String
	 * @param s1 String
	 * @return String
	 */
	public static String getString(String s, String s1) {
		String s2 = properties.getProperty(s);
		if (s2 != null)
			return s2;
		else
			return s1;
	}

	/**
	 * 返回KEY为S的值
	 * @param s String
	 * @return String
	 */
	public static String getProperty(String s) {
		String s1 = properties.getProperty(s);
		return s1;
	}

	/**
	 * 返回KEY为s的整数值，如果没有对应的KEY值，返回默认值i
	 * @param s String
	 * @param i int
	 * @return int
	 */
	public static int getInt(String s, int i) {
		String s1 = properties.getProperty(s);
		if (s1 != null)
			return Integer.valueOf(s1).intValue();
		else
			return i;
	}

	/**
	 * 返回KEY为s的 BOOLEAN 值
	 * @param s String
	 * @return boolean
	 */
	public static boolean getBoolean(String s) {
		boolean f = false;
		String s1 = properties.getProperty(s);
		if (s1 != null)
			return Boolean.valueOf(s1).booleanValue();
		else
			return f;
	}

	private static boolean loadAppProperties(String s) {
		try {
			InputStream fileinputstream = ConfigUtils.class.getResourceAsStream(s);
			properties.load(fileinputstream);
			fileinputstream.close();
			return true;
		} catch (Exception e) {
			ErYuLogger.error("Unable to read configuration file! " + s);
			return false;
		}
	}

	/**
	 * 获得系统当前时间 ，格式为parr
	 * @param parr String
	 * @return String
	 */
	public static String getNowtime(String parr) {
		String time = null;
		SimpleDateFormat dfr = new SimpleDateFormat(parr);
		Date d = new Date();
		time = dfr.format(d);
		return time;
	}

	static {
		if (System.getProperty(ConfigUtils.config) != null) {
			loadAppProperties(System.getProperty(ConfigUtils.config));
		} else {
			loadAppProperties("/config.properties");
		}

	}
	
	public static void main(String args[]){
		System.out.println("java.home: " + getProperty("fileHVGAPrefix"));
	}

}
