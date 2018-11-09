package com.sh.djpk.share.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;


/***
 * Ini digunakan untuk validasi object
 * 
 * @author Sahroni
 * <br>sahroni61212@gmail.com
 * 
 */
public class ValidateUtils {
	
	/***
	 * Akan error jika nilai object sama dengan null
	 * @param object value yang akan divalidasi
	 * @param message pesan error
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/***
	 * Akan error jika nilai object sama dengan null
	 * @param object value yang akan divalidasi
	 */
	public static void notNull(Object object) {
		notNull(object, "Object must be not null");
	}
	
	/***
	 * Akan error jika nilai object tidak sama dengan null
	 * @param object value yang akan divalidasi
	 * @param message pesan error
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/***
	 * Akan error jika nilai object tidak sama dengan null
	 * @param object value yang akan divalidasi
	 */
	public static void isNull(Object object) {
		isNull(object, "Object must be null");
	}
	
	
	/***
	 * Akan error jika element array ada yang null
	 * @param array value yang akan divalidasi
	 * @param message
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}else{
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void noNullElements(Object[] array) {
		noNullElements(array, "Elements Array  must be not null");
	}
	
	public static void notEmpty(Collection collection, String message) {
		if (collection == null || collection.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notEmpty(Collection collection) {
		notEmpty(collection,
				"Collection must be not empty");
	}
	
	public static void notEmpty(Map map, String message) {
		if (map == null || map.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void notEmpty(Map map) {
		notEmpty(map, "Map must be not empty");
	}

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
				LoggerUtils.error(obj.getClass().getName() + " dengan value : " + obj
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
