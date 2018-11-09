package com.sh.djpk.share.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class InetUtils {

	public static final String GOOGLE_COM = "http://www.google.com";

	public static boolean isConnectInet(String uri) {
		try {
			URL url = new URL(uri == null || uri.isEmpty() ? GOOGLE_COM : uri);
			LoggerUtils.info("Get Host : " + url.getHost());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			if (con.getResponseCode() == 200) {
				LoggerUtils.info("Dapat Koneksi internet! ");
				return true;
			}
		} catch (Exception e) {
			LoggerUtils.error("Tidak dapat Koneksi internet!, dengan error : "
					+ e.getMessage());
		}

		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("Result " + InetUtils.isConnectInet(GOOGLE_COM));
	}

}
