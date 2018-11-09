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

import com.sh.djpk.econsole.rest.service.PelaporanSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/pelaporan")
public class PelaporanCtl {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(LraReportCtl.class);

	@Autowired
	PelaporanSvc pelaporanSvc;

	/***
	 * 
	 * @param tahunAnggaran
	 * @param periodeId
	 * @param kodeLaporan
	 *            1=Nas,2=Wil,3=Porv,4=Pemda
	 * @param jenisLaporan
	 * @param othersParam
	 * @return
	 */
	@RequestMapping(value = "/get_all_report", method = RequestMethod.POST)
	public RestResponse getAllReport(
			@RequestParam(value = "tahun_anggaran", required = true) int tahunAnggaran,
			@RequestParam(value = "periode_id", required = true) int periodeId,
			@RequestParam(value = "kode_laporan", required = true) int kodeLaporan,
			@RequestParam(value = "jenis_laporan", required = true) int jenisLaporan,
			@RequestBody HashMap<String, Object> othersParam) {
		LOGGER.info("get_all_report");
		LOGGER.info(
				"try get data all report tahunAnggaran={}, periodeId={}, kodeLaporan={}, jenisLaporan={}, othersParam={}",
				tahunAnggaran, periodeId, kodeLaporan, jenisLaporan,
				othersParam);
		RestResponse restResponse = pelaporanSvc.getAllReport(tahunAnggaran,
				periodeId, kodeLaporan, jenisLaporan, othersParam);
		return restResponse;
	}
}
