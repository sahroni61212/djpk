package com.sh.djpk.share.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/***
 * Ini digunakan untuk memanipulasi tanggal sesuai dengan yang diinginkan
 * 
 * @author Sahroni
 * <br>sahroni61212@gmail.com
 * 
 */
public class DateUtils {

	public final static String FORMAT_DATE = "dd/MM/yyyy";
	public final static String FORMAT_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
	public final static String DATE_NOT_NULL = "Tanggal tidak boleh null";
	
	public static final String FORMAT_DATE_TIME_REST = "yyyyMMddHHmmss";
	
	public static final String FORMAT_DATE_REST = "yyyyMMdd";

	/***
	 * Menambahkan jumlah tahun, jika ingin mengurangi beri tanda '-' pada
	 * amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addYears(Date date, int amount) {
		// Calendar.YEAR = 1
		return add(date, 1, amount);
	}

	/***
	 * Menambahkan jumlah bulan, jika ingin mengurangi beri tanda '-' pada
	 * amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMonths(Date date, int amount) {
		// Calendar.MONTH = 2
		return add(date, 2, amount);
	}

	/***
	 * Menambahkan jumlah tanggal, jika ingin mengurangi beri tanda '-' pada
	 * amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		// Calendar.DATE = 5
		return add(date, 5, amount);
	}

	/***
	 * Menambahkan jumlah jam, jika ingin mengurangi beri tanda '-' pada amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		// Calendar.HOUR = 10
		// Calendar.HOUR_OF_DAY = 11
		return add(date, 11, amount);
	}

	/***
	 * Menambahkan jumlah menit, jika ingin mengurangi beri tanda '-' pada
	 * amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		// Calendar.HOUR = 12
		return add(date, 12, amount);
	}

	/***
	 * Menambahkan jumlah detik, jika ingin mengurangi beri tanda '-' pada
	 * amount
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addSeconds(Date date, int amount) {
		// Calendar.HOUR = 13
		return add(date, 13, amount);
	}
	
	public static Date addMilliseconds(Date date, int amount) {
		//Calendar.MILLISECOND = 14
		return add(date, 14, amount);
	}

	/***
	 * Tanggal ditambah sesuai calendarFieldnya, jika ingin mengurangi beri
	 * tanda '-' pada amount
	 * 
	 * @param date
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, int calendarField, int amount) {
		Calendar c = toCalendar(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/***
	 * Mengambil tahun dari date
	 * @param date
	 * @return
	 */
	public static int getYears(Date date) {
		return toCalendar(date).get(1);
	}

	/***
	 * Mengambil bulan dari date
	 * @param date
	 * @return
	 */
	public static int getMonths(Date date) {
		return (toCalendar(date).get(2)) + 1;
	}
	
	/***
	 * Mengambil jumlah maximum tanggal dalam 1 bulan
	 * @param date
	 * @return
	 */
	public static int getDaysMaximumInMonth(Date date){
        return toCalendar(date).getActualMaximum(Calendar.DAY_OF_MONTH);
    }

	/***
	 * Mengambil tanggal dari date
	 * @param date
	 * @return
	 */
	public static int getDays(Date date) {
		return toCalendar(date).get(5);
	}

	/***
	 * Mengambil jam dari date
	 * @param date
	 * @return
	 */
	public static int getHours(Date date) {
		return toCalendar(date).get(11);
	}

	/***
	 * Mengambil menit dari date
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		return toCalendar(date).get(12);
	}

	/***
	 * Mengambil detik dari date
	 * @param date
	 * @return
	 */
	public static int getSeconds(Date date) {
		return toCalendar(date).get(13);
	}

	/***
	 * Menghitung selisih tahun (second - first)
	 * @param first
	 * @param second
	 * @return
	 */
	public static int getYearsBetween(Date first, Date second) {
		return getYears(second) - getYears(first);
	}

	/***
	 * Menghitung selisih bulan (second - first)
	 * <br> Tanpa memperhatikan tanggal(hari)
	 * @param first
	 * @param second
	 * @return
	 */
	public static int getMonthsBetween(Date first, Date second) {
		return (getYearsBetween(first, second) * 12)
				+ (getMonths(second) - getMonths(first));
	}

	/***
	 * menghitung selisih hari (second - first)
	 * <br> Tanpa memperhatikan time
	 * @param first
	 * @param second
	 * @return
	 */
	public static long getDaysBetween(Date first, Date second) {
		return TimeUnit.MILLISECONDS.toDays(noTime(second).getTime()
				- noTime(first).getTime());
	}
	
	/***
	 * menghitung selisih detik (second - first)
	 * @param first
	 * @param second
	 * @return
	 */
	public static long getSecondsBetween(Date first, Date second) {
		return TimeUnit.MILLISECONDS.toSeconds(second.getTime() - first.getTime());
	}

	/***
	 * Mengubah date tanpa time
	 * @param date
	 * @return
	 */
	public static Date noTime(Date date) {
		try {
			return stringToDate(dateToString(date, null), null);
		} catch (ParseException e) {
			throw new IllegalArgumentException(
					"Format tanggal pada DateUtils salah!");
		}
	}
	
	public static Date maxTime(Date date) {
		return addMilliseconds(addDays(noTime(date), 1), -1);
	}

	// public static Date stringToDate(String str) throws ParseException {
	// ValidateUtils.notNull(str, DATE_NOT_NULL);
	// SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
	// return sdf.parse(str);
	// }

	/***
	 * 
	 * @param date
	 * @param format
	 *            jika null atau '' maka default (dd/MM/yyyy)
	 * @return
	 */
	public static Date stringToDate(String str, String format)
			throws ParseException {
		ValidateUtils.notNull(str, DATE_NOT_NULL);
		return sdfDate(format, false).parse(str);

	}

	/***
	 * 
	 * @param date
	 * @param format
	 *            jika null atau '' maka default (dd/MM/yyyy HH:mm:ss)
	 * @return
	 */
	public static Date stringToDatetime(String str, String format)
			throws ParseException {
		ValidateUtils.notNull(str, DATE_NOT_NULL);
		return sdfDate(format, true).parse(str);

	}

	// public static Date stringToDatetime(String str) throws ParseException {
	// ValidateUtils.notNull(str, DATE_NOT_NULL);
	// SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
	// return sdf.parse(str);
	//
	// }

	// public static String dateToString(Date date) {
	// ValidateUtils.notNull(date, DATE_NOT_NULL);
	// SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
	// return sdf.format(date);
	// }

	/***
	 * 
	 * @param date
	 * @param format
	 *            jika null atau '' maka default (dd/MM/yyyy)
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		ValidateUtils.notNull(date, DATE_NOT_NULL);
		return sdfDate(format, false).format(date);

	}

	/***
	 * 
	 * @param date
	 * @param format
	 *            jika null atau '' maka default (dd/MM/yyyy HH:mm:ss)
	 * @return
	 */
	public static String datetimeToString(Date date, String format) {
		ValidateUtils.notNull(date, DATE_NOT_NULL);
		return sdfDate(format, true).format(date);

	}

	// public static String datetimeToString(Date date) {
	// ValidateUtils.notNull(date, DATE_NOT_NULL);
	// SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
	// return sdf.format(date);
	//
	// }

	/***
	 * buat format
	 * @param format
	 * @param isTime
	 * @return
	 */
	public static SimpleDateFormat sdfDate(String format, boolean isTime) {
		if (format != null && !format.isEmpty()) {
			return new SimpleDateFormat(format);
		}
		if (isTime) {
			return new SimpleDateFormat(FORMAT_DATE_TIME);
		}
		return new SimpleDateFormat(FORMAT_DATE);
	}

	/***
	 * buat calendar
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date) {
		ValidateUtils.notNull(date, DATE_NOT_NULL);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

}
