package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.JenisTransferSvc;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/jenistrf")
public class JenisTransferCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(JenisTransferCtl.class);

	@Autowired JenisTransferSvc jenisSvc;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public RestResponse getAll() {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = jenisSvc.getAll();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
}
