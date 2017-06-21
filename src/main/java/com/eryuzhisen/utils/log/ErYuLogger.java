package com.eryuzhisen.utils.log;

/**
 * 
 * <ul>
 * <li>1.info</li>
 * <li>2.error</li>
 * <li>3.debug</li>
 * </ul>
 * 注：所有java类应用代码统一采用此类进行日志记录。
 * 
 * @author albert
 * @version 1.0
 */
public final class ErYuLogger {

	private static final String	SEPARATOR		= "│ß│";
	private static final String	THIS_CLASS_NAME	= ErYuLogger.class.getName();

	private static final LoggerDelegator DELEGATOR = new Slf4jLoggerDelegator();

	/**
	 * 调试信息.
	 * 
	 * @param msg
	 */
	public static void debug(String msg) {
		DELEGATOR.debug(buildSplitableMessage(Thread.currentThread().getStackTrace(), msg));
	}

	/**
	 * 正常输出.
	 * 
	 * @param msg
	 */
	public static void info(String msg) {
		DELEGATOR.info(buildSplitableMessage(Thread.currentThread().getStackTrace(), msg));
	}

	/**
	 * 错误日志，并记录堆栈信息。
	 * 
	 * @param msg
	 * @param t
	 */
	public static void error(String msg, Throwable t) {
		DELEGATOR.error(buildSplitableMessage(Thread.currentThread().getStackTrace(), msg), t);
	}

	/**
	 * 不记录详细错误堆栈。
	 * 
	 * @param msg
	 * @param t
	 */
	public static void error(String msg) {
		DELEGATOR.error(buildSplitableMessage(Thread.currentThread().getStackTrace(), msg));
	}

	/**
	 * 根据堆栈信息得到源代码行信息 取得当前调用者行号信息。
	 * <p>
	 * 原理：本工具类的堆栈下一行即为源代码的最原始堆栈。
	 * 
	 * @param ste
	 *            堆栈信息
	 * @return 调用输出日志的代码所在的类.方法.代码行的相关信息
	 */
	private static String traceFirstLine(StackTraceElement[] ste) {
		StringBuffer sb = new StringBuffer("");
		boolean srcFlag = false;
		for (int i = 0; i < ste.length; i++) {
			StackTraceElement traceElement = ste[i];
			// 如果上一行堆栈代码是本类的堆栈，则该行代码则为源代码的最原始堆栈。
			if (srcFlag) {
				sb.append(traceElement == null ? "" : traceElement.toString());
				break;
			}

			// 定位本类的堆栈
			if (THIS_CLASS_NAME.equals(traceElement.getClassName())) {
				srcFlag = true;
			}
		}
		return sb.toString();
	}

	/**
	 * 组合包装下调试信息.
	 * 
	 * @param ste
	 * @param message
	 * @return
	 */
	private static String buildSplitableMessage(StackTraceElement[] ste, String message) {
		return traceFirstLine(ste) + SEPARATOR + message;
	}
}
