package com.sh.djpk.share.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/***
 * Ini digunakan untuk memanipulasi file sesuai dengan yang diinginkan
 * 
 * @author Sahroni
 * <br>sahroni61212@gmail.com
 * 
 */
public class FileUtils {
	
	public static final int BUFFER_SIZE = 1024;

	/***
	 * Membuat file, jika path dan file belum dibuat maka akan dibuatkan.
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File getFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}	
	
	public static int write(File in, File out) throws IOException {
		ValidateUtils.notNull(in, "File tidak boleh null");
		ValidateUtils.notNull(out, "File tidak boleh null");
		return write(new BufferedInputStream(new FileInputStream(in)),
		    new BufferedOutputStream(new FileOutputStream(out)));
	}
	
	public static void write(byte[] in, File out) throws IOException {
		ValidateUtils.notNull(in, "byte array tidak boleh null");
		ValidateUtils.notNull(out, "File tidak boleh null");
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);
		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		write(inStream, outStream);
	}
	
		
	public static int write(InputStream in, OutputStream out) throws IOException{
		ValidateUtils.notNull(in, "InputStream tidak boleh null");
		ValidateUtils.notNull(out, "OutputStream tidak boleh null");
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
			}
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}
	
	public static void write(byte[] in, String fileName) throws IOException {
	    OutputStream out = new FileOutputStream(getFile(fileName));
	    write(in, out);
	  }
	
	public static void write(byte[] in, OutputStream out) throws IOException {
		ValidateUtils.notNull(in, "byte array tidak boleh null");
		ValidateUtils.notNull(out, "OutputStream tidak boleh null");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}
	
	public static int write(Reader in, Writer out) throws IOException {
		ValidateUtils.notNull(in, "Reader tidak boleh null");
		ValidateUtils.notNull(out, "Writer tidak boleh null");
		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
			}
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}
	
	public static void write(String in, Writer out) throws IOException {
		ValidateUtils.notNull(in, "String tidak boleh null");
		ValidateUtils.notNull(out, "Writer tidak boleh null");
		try {
			out.write(in);
		}
		finally {
			try {
				out.close();
			}
			catch (IOException ex) {
			}
		}
	}
	
	public static String toString(Reader in) throws IOException {
		StringWriter out = new StringWriter();
		write(in, out);
		return out.toString();
	}

	
	public static byte[] toByteArray(File in) throws IOException {
		ValidateUtils.notNull(in, "File tidak boleh null");
		return toByteArray(new BufferedInputStream(new FileInputStream(in)));
	}
	
	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		write(in, out);
		return out.toByteArray();
	}
}
