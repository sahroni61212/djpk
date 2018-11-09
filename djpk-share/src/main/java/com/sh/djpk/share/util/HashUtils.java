package com.sh.djpk.share.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
	
	 public static String md5Java(String toMd5) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] hash = md.digest(toMd5.getBytes("UTF-8"));            
	        //converting byte array to Hexadecimal String 
	        StringBuilder sb = new StringBuilder(2 * hash.length);
	        for (byte b : hash) {
	            sb.append(String.format("%02x", b & 0xff));
	        }        
	        return sb.toString();
	    }
	 
	 public static void main(String[] args){
		 //c4ca4238a0b923820dcc509a6f75849b
		 String s = "1";
		 try {
			System.out.println("MD5 : " + md5Java(s));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
