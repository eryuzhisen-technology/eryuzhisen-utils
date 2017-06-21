package com.eryuzhisen.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author albert
 */
public class Slf4jLoggerDelegator implements LoggerDelegator {
	
	private final Logger eryuLogger = LoggerFactory.getLogger("eryu.logger");
	private final Logger errorLogger = LoggerFactory.getLogger("eryu.error");

	@Override
	public void error(String msg, Throwable t) {
		errorLogger.error(msg, t);
	}
	
	@Override
	public void error(String msg) {
		//只打印错误信息不打印堆栈记录到upluslogger中。
	    eryuLogger.error(msg);
	}

	@Override
	public void info(String msg) {
	    eryuLogger.info(msg);
	}

	@Override
	public void debug(String msg) {
	    eryuLogger.debug(msg);
	}

}
