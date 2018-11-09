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
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RptRekapAkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/akuneliminasi")
public class AkunEliminasiCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(AkunEliminasiCtl.class);

	@Autowired AkunEliminasiSvc akunSvc;
	
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
	
	@RequestMapping(value = "/tahun/{pemda}/{tahunAnggaran}", method = RequestMethod.GET)
	public RestResponse getAll(
			@PathVariable(value = "pemda") String pemda,
			@PathVariable(value = "tahunAnggaran") String tahunAnggaran
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getOnTahunPemda(pemda, tahunAnggaran);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/head", method = RequestMethod.GET)
	public RestResponse getHead(
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getHeader();
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/elimhead/{tahun}", method = RequestMethod.GET)
	public RestResponse getElimHead(
			@PathVariable(value = "tahun") String tahun
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = akunSvc.getElimHead(tahun);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
	
	@RequestMapping(value = "/proses", method = RequestMethod.POST)
	public RestResponse insert(@RequestBody HashMap<String, Object> params) {
		LOGGER.info("Proses Eliminasi");
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
