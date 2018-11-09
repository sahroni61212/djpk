package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.GlobalParamSvc;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.util.JsonUtil;

@RestController
@RequestMapping("/global_param")
public class GlobalParamCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalParamCtl.class);
	
	@Autowired GlobalParamSvc globalParamSvc;
	
	@RequestMapping(value = "/get_all_data", method = RequestMethod.GET)
	public RestResponse getAllData(
			@RequestParam(value = "global_param_parent", required = false) String globalParamParent,
			@RequestParam(value = "global_param_value", required = false) String globalParamValue) {
		LOGGER.info("get_all_data");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		response.setContents(globalParamSvc.getAllData(globalParamParent, globalParamValue));
		LOGGER.trace("response={}", JsonUtil.getJson(response));
		return response;
	}
}
