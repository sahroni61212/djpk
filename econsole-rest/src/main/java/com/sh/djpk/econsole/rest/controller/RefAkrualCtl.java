package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RefAkrualSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/akrual")
public class RefAkrualCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(RefAkrualCtl.class);

	@Autowired RefAkrualSvc refAkrualSvc;
	
	@RequestMapping(value = "/akr1", method = RequestMethod.GET)
	public RestResponse getAkr1() {
		LOGGER.info("GET ALL Akrual 1");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = refAkrualSvc.getAllAkrual1();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr2", method = RequestMethod.GET)
	public RestResponse getAkr2() {
		LOGGER.info("GET ALL Akrual 2");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = refAkrualSvc.getAllAkrual2();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr3", method = RequestMethod.GET)
	public RestResponse getAkr3() {
		LOGGER.info("GET ALL Akrual 3");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = refAkrualSvc.getAllAkrual3();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr4", method = RequestMethod.GET)
	public RestResponse getAkr4() {
		LOGGER.info("GET ALL Akrual 4");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = refAkrualSvc.getAllAkrual4();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr5", method = RequestMethod.GET)
	public RestResponse getAll() {
		LOGGER.info("GET ALL Akrual 5");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = refAkrualSvc.getAllAkrual5();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
}
