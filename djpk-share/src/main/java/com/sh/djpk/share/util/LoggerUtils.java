package com.sh.djpk.share.util;

import java.util.logging.Logger;

public class LoggerUtils {

	public static Logger log = Logger.getLogger("LoggerUtils");

	public static void info(Exception e) {
		log.info(e.getMessage());
	}

	public static void info(String msg) {
		log.info(msg);
	}

	public static void error(Exception e) {
		log.severe(e.getMessage());
	}

	public static void error(String msg) {
		log.severe(msg);
	}

	public static void warn(Exception e) {
		log.warning(e.getMessage());
	}

	public static void warn(String msg) {
		log.warning(msg);
	}
}
