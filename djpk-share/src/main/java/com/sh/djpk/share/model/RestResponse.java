package com.sh.djpk.share.model;

import java.io.Serializable;

public class RestResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private int status; // status flag based on CommonConstants
	private String message; // message description
	private long totalRecords; // total records data
	private Object contents; // real data content

	public static final int OK_REST_STATUS = 0;
	public static final int ERROR_REST_STATUS = 1;

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final String PARAM = "param";

	public static final String PAGE = "page";
	public static final String SIZE = "size";
	public static final String TOKEN = "token";
	public static final String SEARCH = "search";
	public static final String DIRECTION = "direction";
	public static final String ORDER_BY = "order_by";

	public RestResponse() {
	}

	public RestResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public RestResponse(int status, String message, Object contents) {
		this.status = status;
		this.message = message;
		this.contents = contents;
	}

	public RestResponse(int status, String message, Object contents,
			long totalRecords) {
		this.status = status;
		this.message = message;
		this.contents = contents;
		this.totalRecords = totalRecords;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public static enum HttpMethod {
		GET, POST, PUT;
	}
}
