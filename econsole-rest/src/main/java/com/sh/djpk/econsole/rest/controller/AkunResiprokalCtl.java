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

import com.sh.djpk.econsole.rest.service.AkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.AkunResiprokalSvc;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RptRekapAkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/resiprokal")
public class AkunResiprokalCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(AkunResiprokalCtl.class);

	@Autowired AkunResiprokalSvc akunSvc;
	
	@RequestMapping(value = "/tahun/{tahunAnggaran}", method = RequestMethod.GET)
	public RestResponse getAll(
			@PathVariable(value = "tahunAnggaran") String tahunAnggaran
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getOnTahun(tahunAnggaran);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr1", method = RequestMethod.GET)
	public RestResponse getakr1(
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr1();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr1/{lap}", method = RequestMethod.GET)
	public RestResponse getakr1a(
			@PathVariable(value = "lap") String lap
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr1a(lap);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr2/{akr1}", method = RequestMethod.GET)
	public RestResponse getakr2(
			@PathVariable(value = "akr1") String akr1
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr2(akr1);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr3/{akr1}/{akr2}", method = RequestMethod.GET)
	public RestResponse getakr3(
			@PathVariable(value = "akr1") String akr1,
			@PathVariable(value = "akr2") String akr2
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr3(akr1,akr2);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr4/{akr1}/{akr2}/{akr3}", method = RequestMethod.GET)
	public RestResponse getakr4(
			@PathVariable(value = "akr1") String akr1,
			@PathVariable(value = "akr2") String akr2,
			@PathVariable(value = "akr3") String akr3
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr4(akr1,akr2,akr3);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/akr5/{akr1}/{akr2}/{akr3}/{akr4}", method = RequestMethod.GET)
	public RestResponse getakr5(
			@PathVariable(value = "akr1") String akr1,
			@PathVariable(value = "akr2") String akr2,
			@PathVariable(value = "akr3") String akr3,
			@PathVariable(value = "akr4") String akr4
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getAkr5(akr1,akr2,akr3,akr4);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/inresi", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("Insert akun resiprokal");
		LOGGER.info("params={}", params);
		RestResponse response =  new RestResponse();
		int i = akunSvc.getProses(params);
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
	
}
