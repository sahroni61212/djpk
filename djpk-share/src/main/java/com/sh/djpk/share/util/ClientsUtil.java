package com.sh.djpk.share.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;



public class ClientsUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(ClientsUtil.class);

	protected final static String WS_URL = "http://localhost:6060/econsole-rest";
//	protected final static String WS_URL = "http://103.82.243.62:6060/econsole-rest";
//	protected final static String WS_URL = "http://djpk.repomyapps.com/econsole-rest";
//	protected final static String WS_URL = "http://repomyapps.com:9977/econsole-rest";

	public static RestResponse callWs(String uri, Object sentObject,
			HttpMethod httpMethod, String... params) throws Exception {

		return callWs(WS_URL, uri, sentObject, httpMethod, params);
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> callWsListResponse(String uri,
			Object sentObject, HttpMethod httpMethod, String... params) {
		List<Map<String, Object>> return_ = new ArrayList<Map<String, Object>>();
		try {
			RestResponse restResponse = callWs(uri, sentObject, httpMethod,
					params);
			if (RestResponse.OK_REST_STATUS == restResponse.getStatus()) {
				if (restResponse.getContents() != null) {
					return_ = (List<Map<String, Object>>) restResponse
							.getContents();
				}
			} else {
				LOGGER.error("<<<<<<RESPONSE TIDAK OK>>>>>",
						restResponse.getMessage());
				throw new RuntimeException(restResponse.getMessage());
				
			}

		} catch (Exception e) {			
			LOGGER.error("<<<<<<ERRRRRRRORRRR>>>>>", e);
			throw new RuntimeException(e.getMessage());			
		}
		return return_;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> callWsMapResponse(String uri,
			Object sentObject, HttpMethod httpMethod, String... params) {
		Map<String, Object> return_ = new HashMap<String, Object>();
		try {
			RestResponse restResponse = callWs(uri, sentObject, httpMethod,
					params);
			if (RestResponse.OK_REST_STATUS == restResponse.getStatus()) {
				if (restResponse.getContents() != null) {
					return_ = (Map<String, Object>) restResponse.getContents();
				}
			} else {
				
				LOGGER.error("<<<<<<RESPONSE TIDAK OK>>>>>",
						restResponse.getMessage());
				throw new RuntimeException(restResponse.getMessage());	
			}

		} catch (Exception e) {
			
			LOGGER.error("<<<<<<ERRRRRRRORRRR>>>>>", e);
			throw new RuntimeException(e.getMessage());	
		}
		return return_;
	}
	
	public static Object callWsContentResponse(String uri,
			Object sentObject, HttpMethod httpMethod, String... params) {
		Object content = null;
		try {
			RestResponse restResponse = callWs(uri, sentObject, httpMethod,
					params);
			if (RestResponse.OK_REST_STATUS == restResponse.getStatus()) {
				if (restResponse.getContents() != null) {
					content  = restResponse.getContents();
				}
			} else {
				
				LOGGER.error("<<<<<<RESPONSE TIDAK OK>>>>>",
						restResponse.getMessage());
				throw new RuntimeException(restResponse.getMessage());	
			}

		} catch (Exception e) {
			
			LOGGER.error("<<<<<<ERRRRRRRORRRR>>>>>", e);
			throw new RuntimeException(e.getMessage());	
		}
		return content;
	}

	public static RestResponse callWs(String host, String uri,
			Object sentObject, HttpMethod httpMethod, String... params)
			throws Exception {

		String param = "";
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				param += "&" + params[i];
			}
		}
		// could be closed later in production for faster performance
		// java WS
		String url = host + uri + "?" + RestResponse.TOKEN + "="
				+ ClientsUtil.getSessionToken() + param;

		return executeWebService(url, sentObject, httpMethod);

	}

	public static String getSessionToken() {
		return "initokenkasaran";
	}

	public static RestResponse executeWebService(String strUrl,
			Object sentObject, RestResponse.HttpMethod httpMethod)
			throws Exception {
		strUrl = strUrl.replace("\\", "");
		LOGGER.info("JSON Object : {} ", JsonUtil.getJson(sentObject));
		LOGGER.info("Invoke web service with URL : {}", strUrl);
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(httpMethod.name());
		conn.setRequestProperty("Content-Type",
				"application/json; charset=UTF-8");
		if ((RestResponse.HttpMethod.POST.equals(httpMethod) || RestResponse.HttpMethod.PUT
				.equals(httpMethod)) && sentObject != null) {
			// Send post request
			// conn.setDoOutput(true);
			// DataOutputStream wr = new
			// DataOutputStream(conn.getOutputStream());
			// wr.writeBytes(JsonUtil.getJson(sentObject));
			// wr.flush();
			// wr.close();
			// conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(JsonUtil.getJson(sentObject).getBytes("UTF-8"));
			os.close();
		}
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		RestResponse restResponse = JsonUtil.mapJsonToRestResponse(conn
				.getInputStream());
		LOGGER.trace("response from ws={}", JsonUtil.getJson(restResponse));
		return restResponse;
	}

	public static RestResponse callCustomWs(String uri, String reqParm,
			Object sentObject, HttpMethod httpMethod) throws Exception {
		String url = WS_URL + uri + "?" + reqParm;
		return executeWebService(url, sentObject, httpMethod);
	}

}
