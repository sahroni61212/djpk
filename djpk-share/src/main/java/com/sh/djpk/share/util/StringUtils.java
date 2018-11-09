package com.sh.djpk.share.util;

import java.util.Vector;

/***
 * Ini digunakan untuk memanipulasi String sesuai dengan yang diinginkan
 * 
 * @author Sahroni
 * 
 */
public class StringUtils {

	/***
	 * Akan true jika <code>word</code> terdapat angka
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isAnyNumeric(String word) {
		if (word != null) {
			word = word.replaceAll("[^0-9]+", " ").trim();
			if (!word.isEmpty())
				return true;
			return false;
		}
		return false;
	}

	/***
	 * Akan true jika <code>word</code> terdapat hurup besar
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isAnyCharCapital(String word) {
		if (word != null) {
			word = word.replaceAll("[^A-Z]+", " ").trim();
			if (!word.isEmpty())
				return true;
			return false;
		}
		return false;
	}

	/***
	 * Akan true jika <code>word</code> terdapat hurup kecil
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isAnyCharUncapital(String word) {
		if (word != null) {
			word = word.replaceAll("[^a-z]+", " ").trim();
			if (!word.isEmpty())
				return true;
			return false;
		}
		return false;
	}

	/***
	 * Hanya akan mengambil hurup saja
	 * 
	 * @param word
	 * @return
	 */
	public static String replaceAlphabetOnly(String word) {
		if (word != null) {
			word = word.replaceAll("[^a-zA-Z]+", " ").trim();
		}
		return word;
	}

	/***
	 * Membandingkan 2 buah string
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean convertValue(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 != null && str2 == null)
			return false;
		if (str1 == null && str2 != null)
			return false;
		if (str1.equals(str2))
			return true;
		return false;

	}

	/***
	 * Mengubah angka menjadi string value <br>
	 * ex: 999 => SEMBILAN RATUS SEMBILAN PULUH SEMBILAN
	 * 
	 * @param val
	 * @return
	 */
	public String toWords(int val) {
		String[] huruf = { "", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam",
				"Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas" };
		String hasil = "";
		if (val < 12)
			hasil = hasil + huruf[val];
		else if (val < 20)
			hasil = hasil + toWords(val - 10) + " Belas";
		else if (val < 100)
			hasil = hasil + toWords(val / 10) + " Puluh " + toWords(val % 10);
		else if (val < 200)
			hasil = hasil + "Seratus " + toWords(val - 100);
		else if (val < 1000)
			hasil = hasil + toWords(val / 100) + " Ratus " + toWords(val % 100);
		else if (val < 2000)
			hasil = hasil + "Seribu " + toWords(val - 1000);
		else if (val < 1000000)
			hasil = hasil + toWords(val / 1000) + " Ribu "
					+ toWords(val % 1000);
		else if (val < 1000000000)
			hasil = hasil + toWords(val / 1000000) + " Juta "
					+ toWords(val % 1000000);
		else if (val >= 1000000000)
			hasil = "Angka terlalu besar, harus kurang dari 1 milyar!";
		return hasil;
	}

	/***
	 * mengganti val menjadi defVal jika val null atau empty
	 * 
	 * @param val
	 * @param defVal
	 * @return
	 */
	public static String nevl(String val, String defVal) {
		if (val == null || val.isEmpty())
			return defVal;
		// URLEncoder.encode("sat", "utf-8");
		return val;
	}

	public static String nevl(Object val, String defVal) {
		if (val == null)
			return defVal;
		return String.valueOf(val).isEmpty() ? defVal : String.valueOf(val);
	}

	public static String surroundString(String word, String added) {
		return added + nevl(word, "") + added;
	}

	public static String toCamelCase(String str) {
		String[] data2 = str.toLowerCase().split("_");
		String capitalize = "";
		for (String data : data2) {
			if (data.length() > 0) {
				data = data.replaceFirst(data.substring(0, 1),
						(data.substring(0, 1)).toUpperCase());
			}
			capitalize = capitalize.concat(data);
		}
		return capitalize;
	}

	public static String toCapitalSnakeCase(String camelCase) {
		String regex = "([a-z])([A-Z])";
		String replacement = "$1_$2";
		return camelCase.replaceAll(regex, replacement).toUpperCase();
	}

	public static String toCapitalSnakeCaseWithNumber(String camelCase) {
		String regex = "([a-z0-9])([A-Z0-9])";
		String replacement = "$1_$2";
		return camelCase.replaceAll(regex, replacement).toUpperCase();
	}

	public static String queryEscapeMybatis(String sql, String... param) {
		return queryEscape(sql, "MYBATIS", param);
	}

	private static String queryEscape(String sql, String queryType,
			String... param) {
		StringBuffer stringBuffer = new StringBuffer("");
		if (param != null && param.length > 0) {
			String[] spl = split(sql, "?");
			if ((spl.length - 1) != param.length) {
				throw new RuntimeException("Jumlah Parameter tidak sama!");
			}
			for (int i = 0; i < spl.length; i++) {
				stringBuffer.append(spl[i]);
				if (i < param.length) {
					if ("MYBATIS".equals(queryType)) {
						stringBuffer.append("#{").append((param[i]))
								.append("}");
					} else {
						stringBuffer.append("'").append(escapeSql(param[i]))
								.append("'");
					}

				}
			}
		} else {
			stringBuffer.append(sql);
		}
		return stringBuffer.toString();
	}

	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return replace(str, "'", "''");
	}

	public static String replace(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString,
			String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null
				|| max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static Vector<Vector<String>> splitVector(String original,
			String separatorcol, String separatorrow) {
		Vector<Vector<String>> nodes = new Vector<Vector<String>>();
		int index = original.indexOf(separatorrow);
		while (index >= 0) {
			nodes.addElement(splitVector(original.substring(0, index),
					separatorcol));
			original = original.substring(index + separatorrow.length());
			index = original.indexOf(separatorrow);
		}
		nodes.addElement(splitVector(original, separatorcol));
		return nodes;
	}

	public static Vector<String> splitVector(String original, String separator) {
		Vector<String> nodes = new Vector<String>();
		int index = original.indexOf(separator);
		while (index >= 0) {
			nodes.addElement(original.substring(0, index));
			original = original.substring(index + separator.length());
			index = original.indexOf(separator);
		}
		nodes.addElement(original);
		return nodes;
	}

	public static String[] split(String original, String separator) {
		Vector<String> nodes = splitVector(original, separator);
		String[] result = new String[nodes.size()];
		if (nodes.size() > 0) {
			for (int loop = 0; loop < nodes.size(); loop++) {
				result[loop] = (String) nodes.elementAt(loop);
			}
		}
		nodes.removeAllElements();
		return result;
	}

	public static void main(String[] args) {
		String x = "SAHRONI_PALING_GANTENG_SEDUNIA";
		System.out.println(toCamelCase(x));
	}

}
