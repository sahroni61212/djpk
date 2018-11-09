package com.sh.djpk.share.util;

import java.awt.Color;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class SystemUtils {

	public static final String PATH_SEPARATOR = System
			.getProperty("file.separator");

	public static final String OS_NAME = System.getProperty("os.name");

	public static String getIdRandom() {
		Random random = new Random();
		int i = random.nextInt(Integer.MAX_VALUE) + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		StringBuilder result = new StringBuilder();
		result.append(sdf.format(new Date()));
		result.append(i);
		return result.toString();
	}

	public static boolean isConnectInternet(String uri) {
		boolean result = true;
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.setConnectTimeout(5000); // berarti 5 detik.
			if (conn.getResponseCode() != 200) {
				result = false;
			}
		} catch (Exception e) {
			result = false;
			LoggerUtils.error(e);
			e.printStackTrace();
		}
		return result;
	}

	public static boolean isConnectInternet() {
		return isConnectInternet("http://google.com");
	}

	public static Color getMyColor() {
		return new Color(120, 155, 198);
	}

	public static Color getMyBackgroundColor() {
		return new Color(204, 204, 204, 255);
	}

	public static Border getMyBorder() {
		Border border = BorderFactory.createLineBorder(getMyColor());
		return border;
	}

	public static void main(String[] args) {
		System.out.println("Mulai");
		System.out.println("Test Koneksi " + isConnectInternet());
	}

}
