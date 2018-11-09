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

import com.sh.djpk.econsole.rest.service.DataKonsolidasiSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/datakonsol")
public class DataKonsolidasiCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DataKonsolidasiCtl.class);

	@Autowired DataKonsolidasiSvc konsolSvc;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public RestResponse getAll() {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = konsolSvc.getAll();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/inkonsol", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = konsolSvc.insert(params);
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
	
	@RequestMapping(value = "/inkonsol2", method = RequestMethod.POST)
	public RestResponse insert2(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = konsolSvc.insert2(params);
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
	
	@RequestMapping(value = "/inkonsol3", method = RequestMethod.POST)
	public RestResponse insert3(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = konsolSvc.insert3(params);
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
//	
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public RestResponse updatet(@RequestBody HashMap<String, Object> params) {
//		LOGGER.info("update");
//		LOGGER.info("params={}", params);
//		RestResponse response =  new RestResponse();
//		int i = periodeSvc.update(params);
//		if (i>0) {
//		response = new RestResponse(RestResponse.OK_REST_STATUS,
//				"Data Berhasil di perbaharui!", null);
//		response.setContents(i);
//		} else {
//			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
//					"Data GAGAL disimpan!", null);
//			response.setContents(i);	
//		}
//		return response;
//	}
//	
//	@RequestMapping(value = "/del", method = RequestMethod.POST)
//	public RestResponse delete(@RequestBody HashMap<String, Object> params) {
//		LOGGER.info("delete");
//		LOGGER.info("params={}", params);
//		RestResponse response =  new RestResponse();
//		int i = periodeSvc.delete(params);
//		if (i>0) {
//		response = new RestResponse(RestResponse.OK_REST_STATUS,
//				"Data Berhasil dihapus!", null);
//		response.setContents(i);
//		} else {
//			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
//					"Data GAGAL disimpan!", null);
//			response.setContents(i);	
//		}
//		return response;
//	}
}
