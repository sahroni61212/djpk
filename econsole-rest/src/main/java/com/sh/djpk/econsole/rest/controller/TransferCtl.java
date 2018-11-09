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

import com.sh.djpk.econsole.rest.service.TransferSvc;
import com.sh.djpk.econsole.rest.service.TransferSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/ref_transfer")
public class TransferCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(TransferCtl.class);
	
	@Autowired TransferSvc transferSvc;
	
	@RequestMapping(value = "/listTransfer", method = RequestMethod.GET)
	public RestResponse getTransfer() {
		LOGGER.info("GET ALL TRANSFER");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = transferSvc.getAllPemda();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("INSERT");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = transferSvc.insert(params);
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
	public RestResponse update(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("update");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = transferSvc.update(params);
		if (i>0) {
		response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Data Berhasil di update!", null);
		response.setContents(i);
		} else {
			response = new RestResponse(RestResponse.ERROR_REST_STATUS,
					"Data GAGAL di update!", null);
			response.setContents(i);	
		}
		return response;
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public RestResponse delete(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("delete");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = transferSvc.delete(params);
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
