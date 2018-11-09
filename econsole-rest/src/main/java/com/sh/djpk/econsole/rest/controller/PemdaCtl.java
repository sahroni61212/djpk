package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.PemdaSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/ref_pemda")
public class PemdaCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(PemdaCtl.class);
	
	@Autowired PemdaSvc pemdaSvc;
	
	@RequestMapping(value = "/listPemda", method = RequestMethod.GET)
	public RestResponse getPemda() {
		LOGGER.info("GET ALL PEMDA");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = pemdaSvc.getAllPemda();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/pemdalengkap", method = RequestMethod.GET)
	public RestResponse getPemdaLkp() {
		LOGGER.info("GET ALL PEMDA LENGKAP");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = pemdaSvc.getAllPemdaLkp();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
}
