package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;

import com.sh.djpk.share.model.RestResponse;

public interface PelaporanSvc {

	/***
	 * 
	 * @param tahunAnggaran
	 * @param periodeId
	 * @param kodeLaporan  1=Nas,2=Wil,3=Porv,4=Pemda
	 * @param jenisLaporan 1=LRA,2=Neraca,3=LO,4=LAK, 5=LPE,6=LPSAL
	 * @param othersParam
	 * @return
	 */
	public RestResponse getAllReport(int tahunAnggaran, int periodeId,
			int kodeLaporan, int jenisLaporan,
			HashMap<String, Object> othersParam);

}
