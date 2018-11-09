package com.sh.djpk.econsole.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.DefaultSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
public class DefaultCtl {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultCtl.class);

	@Autowired
	DefaultSvc defaultSvc;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public RestResponse getDeploy() {
		LOGGER.info("Start Deploy");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		return response;
	}

	@RequestMapping(value = "/test_connection_db", method = RequestMethod.GET)
	public RestResponse testConnectionDb() {
		LOGGER.info("Test connection Db");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		response.setContents(defaultSvc.getCurrentTImestamp());
		return response;
	}
	
	@RequestMapping(value = "/update_search_path", method = RequestMethod.GET)
	public RestResponse updateSearchPath() {
		LOGGER.info("update_search_path");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		defaultSvc.updateSearchPath();
		response.setContents("search_path updated");
		return response;
	}

	final String DATA_COUNT = "count";

	final String DATA_PAGING = "data";

	@RequestMapping(value = ("/errorPage"), method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
	public ResponseEntity<RestResponse> errorPage(HttpServletRequest request) {
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		int statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		String message = (String) request
				.getAttribute("javax.servlet.error.message");
		String requestUri = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		// String pathUri = (String) request
		// .getAttribute("javax.servlet.forward.servlet_path");

		// Enumeration enume = request.getAttributeNames();
		// while (enume.hasMoreElements()) {
		// String atribute = (String) enume.nextElement();
		// System.out.println("attribute : " + atribute);
		// System.out.println("value : " + request.getAttribute(atribute));
		// }
		if (throwable != null) {
			message = throwable.getMessage();
			LOGGER.error("Error", throwable);
		} else {
			if (statusCode == 404) {
				message = "Page Not Found";
			}
		}

		String restMessage = "Error pada url=" + requestUri + ", Status code="
				+ statusCode + ", Message error=" + message;
		LOGGER.error(restMessage);
		RestResponse response = new RestResponse(
				RestResponse.ERROR_REST_STATUS, restMessage, null);
		return new ResponseEntity<RestResponse>(response, HttpStatus.OK);
	}
}
