package com.eryuzhisen.utils.log;

public interface LoggerDelegator {
	
	/**
	 * 记录业务相关的正常日志信息.
	 * 
	 * @param msg
	 */
	public void info(String msg);

	/**
	 * 程序调试,代替System.out系列.
	 * 
	 * @param msg
	 */
	public void debug(String msg);

	/**
	 * 捕获异常，并输出堆栈.
	 */
	public void error(String msg, Throwable t);

	/**
	 * 捕获异常，输出业务信息.
	 */
	public void error(String msg);

}
