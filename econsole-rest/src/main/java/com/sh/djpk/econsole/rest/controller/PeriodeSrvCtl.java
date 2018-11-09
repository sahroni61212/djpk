package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/periode")
public class PeriodeSrvCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(PeriodeSrvCtl.class);

	@Autowired PeriodeSvc periodeSvc;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public RestResponse getAll() {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = periodeSvc.getAll();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = periodeSvc.insert(params);
		if (i>0) {
		response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Data Berhasil disimpan!", null);
		response.setContents(i);
		} else {
			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
					"Data GAGAL disimpan!", null);
			response.setContents(i);	
		}
		return response;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public RestResponse updatet(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("update");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = periodeSvc.update(params);
		if (i>0) {
		response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Data Berhasil di perbaharui!", null);
		response.setContents(i);
		} else {
			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
					"Data GAGAL disimpan!", null);
			response.setContents(i);	
		}
		return response;
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("delete");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = periodeSvc.delete(params);
		if (i>0) {
		response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Data Berhasil dihapus!", null);
		response.setContents(i);
		} else {
			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
					"Data GAGAL disimpan!", null);
			response.setContents(i);	
		}
		return response;
	}
}
