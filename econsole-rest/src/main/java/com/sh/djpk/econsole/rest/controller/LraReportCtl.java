package com.sh.djpk.econsole.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.djpk.econsole.rest.service.LraReportSvc;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.util.JsonUtil;

@RestController
@RequestMapping("/lra_report")
public class LraReportCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(LraReportCtl.class);

	@Autowired
	LraReportSvc lraReportSvc;

	@RequestMapping(value = "/get_lra_report_data", method = RequestMethod.POST)
	public RestResponse getLraReportData(
			@RequestParam(value = "tahun_anggaran", required = true) int tahunAnggaran,
			@RequestParam(value = "periode_id", required = true) int periodeId,
			@RequestParam(value = "kode_laporan", required = true) int kodeLaporan,
			@RequestBody HashMap othersParam) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("get_lra_report_data");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		response.setContents(lraReportSvc.getLraReportDataTree(tahunAnggaran,
				periodeId, kodeLaporan, othersParam));
//		ObjectMapper objectMapper = new ObjectMapper();
//		HashMap hashMap = objectMapper.readValue(new File("C:\\file\\sahroni\\imam\\DJKP\\lra_data_2.json"), HashMap.class);
//		response.setContents(hashMap);
		LOGGER.trace("response={}", JsonUtil.getJson(response));
		return response;
	}
	
	@RequestMapping(value = "/get_ref_pemda2", method = RequestMethod.POST)
	public RestResponse getRefPemdaDua(
			@RequestParam(value = "tahun_anggaran", required = true) int tahunAnggaran,
			@RequestParam(value = "kode_laporan", required = true) int kodeLaporan,
			@RequestBody HashMap othersParam) {
		LOGGER.info("get_get_ref_pemda2");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		response.setContents(lraReportSvc.getRefPemdaDua(tahunAnggaran, kodeLaporan, othersParam));
		LOGGER.trace("response={}", JsonUtil.getJson(response));
		return response;
	}
	
	@RequestMapping(value = "/get_periode", method = RequestMethod.GET)
	public RestResponse getPeriode() {
		LOGGER.info("get_periode");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		response.setContents(lraReportSvc.getPeriodeCb());
		LOGGER.trace("response={}", JsonUtil.getJson(response));
		return response;
	}
}
