package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserGroupSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.econsole.rest.service.WilayahSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/usrgrp")
public class UserGroupCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(UserGroupCtl.class);

	@Autowired
	UserGroupSvc usrgrpSvc;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public RestResponse getAll() {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap> lst = usrgrpSvc.getAll();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		return usrgrpSvc.insert(params);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse update(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("update");
		LOGGER.info("params={}", params);
		return usrgrpSvc.update(params);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("delete");
		LOGGER.info("params={}", params);
		return usrgrpSvc.delete(params);
	}

	@RequestMapping(value = "/get_all_menu/{kdUser}", method = RequestMethod.GET)
	public RestResponse getAllMenu(@PathVariable("kdUser") int kdUser) {
		LOGGER.info("GET ALL MENU");
		return usrgrpSvc.getAllMenu(kdUser);
	}

	@RequestMapping(value = "/get_ref_user_menu/{kdUser}", method = RequestMethod.GET)
	public RestResponse getRefUserMenu(@PathVariable("kdUser") int kdUser) {
		LOGGER.info("get_ref_user_menu with kdUser={}", kdUser);
		return usrgrpSvc.getRefUserMenu(kdUser);
	}
	
	@RequestMapping(value = "/submit_ref_menu/{action}", method = RequestMethod.POST)
	public RestResponse submitRefMenu(@PathVariable("action") int action, @RequestBody HashMap<String, Object> params) {
		LOGGER.info("submit_ref_menu with action={} and params={}", action, params);
		return usrgrpSvc.submitRefUserMenu(action, params);
	}
}
