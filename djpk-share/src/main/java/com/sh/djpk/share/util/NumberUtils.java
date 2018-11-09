package com.sh.djpk.share.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Logger;

public class NumberUtils {
	
	public static final Logger logger = Logger.getLogger("ParseUtil");
	
	public static BigDecimal toBigdecimal(Object obj) {
		BigDecimal bd = new BigDecimal(0);
		if(obj != null){
			if (obj instanceof Long) {
				bd = new BigDecimal((Long) obj);
			} else if (obj instanceof Integer) {
				bd = new BigDecimal((Integer) obj);
			} else if (obj instanceof String) {
				bd = new BigDecimal((String) obj);
			} else if (obj instanceof BigDecimal) {
				bd = (BigDecimal) obj;
			} else if (obj instanceof Double) {
				bd = new BigDecimal((Double) obj);
			} else if (obj instanceof Float) {
				bd = new BigDecimal((Float) obj);
			} else if (obj instanceof BigInteger) {
				bd = new BigDecimal((BigInteger) obj);
			} else {
				logger.warning(obj.getClass().getName() + " dengan value : " + obj
						+ " tidak teridentifikasi!");
			}
		}
		
		return bd;
	}

	public static int toInteger(Object obj) {
		return toBigdecimal(obj).intValue();
	}

	public static long toLong(Object obj) {
		return toBigdecimal(obj).longValue();
	}

	public static double toDouble(Object obj) {
		return toBigdecimal(obj).doubleValue();
	}

	public static float toFloat(Object obj) {
		return toBigdecimal(obj).floatValue();
	}

	public static BigInteger toBigInteger(Object obj) {
		return toBigdecimal(obj).toBigInteger();
	}

}
