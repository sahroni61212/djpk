package com.sh.djpk.econsole.rest.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.djpk.econsole.rest.dao.PelaporanDao;
import com.sh.djpk.econsole.rest.service.PelaporanSvc;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.util.NumberUtils;

@Service("pelaporanSvc")
@Transactional(readOnly = false)
public class PelaporanSvcImpl implements PelaporanSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(PelaporanSvcImpl.class);

	@Autowired
	PelaporanDao pelaporanDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public RestResponse getAllReport(int tahunAnggaran, int periodeId,
			int kodeLaporan, int jenisLaporan,
			HashMap<String, Object> othersParam) {
		LOGGER.info(
				"try get data all report tahunAnggaran={}, periodeId={}, kodeLaporan={}, jenisLaporan={}, othersParam={}",
				tahunAnggaran, periodeId, kodeLaporan, jenisLaporan,
				othersParam);
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK");
		/***
		if (jenisLaporan == 5 || jenisLaporan == 6) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				HashMap hashMap = null;
				if (jenisLaporan == 5) {
					hashMap = objectMapper.readValue(new File(
							"C:\\file\\sahroni\\imam\\DJKP\\lpe_data.json"),
							HashMap.class);
				} else if (jenisLaporan == 6) {
					hashMap = objectMapper.readValue(new File(
							"C:\\file\\sahroni\\imam\\DJKP\\lpsal_data.json"),
							HashMap.class);
				}

				response.setContents(hashMap);
				return response;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		***/
		String thnAngNow = String.valueOf(tahunAnggaran);
		String thnAngBefore = String.valueOf((tahunAnggaran - 1));
		if (1 == jenisLaporan || 2 == jenisLaporan || 3 == jenisLaporan) {
			// List<HashMap> l = getLraReportData(tahunAnggaran, periodeId,
			// kodeLaporan, othersParam);
			List<HashMap> l = pelaporanDao.getCompilationRecord5(thnAngNow,
					periodeId, kodeLaporan, jenisLaporan, othersParam);
			List<Map<String, Object>> dataMappingListToTree = mappingListToTree(l);
			response.setContents(dataMappingListToTree);
		} else if (4 == jenisLaporan) {
			List<HashMap> l = pelaporanDao.getCompilationLak(thnAngNow, periodeId, kodeLaporan, othersParam, thnAngBefore);
			List<Map<String, Object>> dataMappingListToTree = mappingListToTree(l);
			response.setContents(dataMappingListToTree);
		} else if (5 == jenisLaporan) {
			HashMap result = new HashMap();
			List<HashMap> lBefore = pelaporanDao.getCompilationLpe(
					thnAngBefore, periodeId, kodeLaporan, othersParam);
			if (lBefore != null && !lBefore.isEmpty()) {
				result.putAll(lBefore.get(0));
				result.put("ekuitasAwal", lBefore.get(0).get("ekuitasawal"));
				result.put("surplusDefisit", lBefore.get(0)
						.get("surplusdefisit"));
				result.put("koreksiPersediaan",
						lBefore.get(0).get("koreksipersediaan"));
				result.put("selisihRevaluasiAset",
						lBefore.get(0).get("selisihrevaluasiaset"));
				result.put("lainlain", lBefore.get(0).get("lainlain"));
				result.put("tahun", thnAngBefore);
				result.put("ekuitasAkhir", getEkuitasAkhir(lBefore.get(0)));
				
			}
			List<HashMap> lNow = pelaporanDao.getCompilationLpe(thnAngNow,
					periodeId, kodeLaporan, othersParam);
			if (lNow != null && !lNow.isEmpty()) {
				result.put("ekuitasAwal_1", lNow.get(0).get("ekuitasawal"));
				result.put("surplusDefisit_1", lNow.get(0)
						.get("surplusdefisit"));
				result.put("koreksiPersediaan_1",
						lNow.get(0).get("koreksipersediaan"));
				result.put("selisihRevaluasiAset_1",
						lNow.get(0).get("selisihrevaluasiaset"));
				result.put("lainlain_1", lNow.get(0).get("lainlain"));
				result.put("tahun_1", thnAngNow);
				result.put("ekuitasAkhir_1", getEkuitasAkhir(lNow.get(0)));
			}
			response.setContents(result);
		} else if (6 == jenisLaporan) {
			HashMap result = new HashMap();
			List<HashMap> lBefore = pelaporanDao.getCompilationLpsal(
					thnAngBefore, periodeId, kodeLaporan, othersParam);
			if (lBefore != null && !lBefore.isEmpty()) {
				result.put("salAwal", lBefore.get(0).get("salawal"));
				result.put("penggunaanSal", lBefore.get(0).get("penggunaansal"));
				result.put("silpa", lBefore.get(0).get("silpa"));
				result.put("koreksi", lBefore.get(0).get("koreksi"));
				result.put("lainnya", lBefore.get(0).get("lainnya"));
				result.put("tahun", thnAngBefore);
				result.put("salAkhir", getSalAkhir(lBefore.get(0)));
			}
			List<HashMap> lNow = pelaporanDao.getCompilationLpsal(thnAngNow,
					periodeId, kodeLaporan, othersParam);
			if (lNow != null && !lNow.isEmpty()) {
				result.put("salAwal_1", lNow.get(0).get("salawal"));
				result.put("penggunaanSal_1", lNow.get(0).get("penggunaansal"));
				result.put("silpa_1", lNow.get(0).get("silpa"));
				result.put("koreksi_1", lNow.get(0).get("koreksi"));
				result.put("lainnya_1", lNow.get(0).get("lainnya"));
				result.put("tahun_1", thnAngNow);
				result.put("salAkhir_1", getSalAkhir(lNow.get(0)));
			}
			response.setContents(result);
		} else {
			response.setStatus(RestResponse.ERROR_REST_STATUS);
			response.setMessage("Jenis Laporan tidak teridentifakasi, jenis laporan="
					+ jenisLaporan);
		}

		return response;
	}
	
	public BigDecimal getEkuitasAkhir(HashMap hashMap){
		BigDecimal bd = new BigDecimal(0);
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("ekuitasawal")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("surplusdefisit")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("koreksipersediaan")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("selisihrevaluasiaset")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("lainlain")));		
		return bd;
	}
	
	public BigDecimal getSalAkhir(HashMap hashMap){
		BigDecimal bd = new BigDecimal(0);
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("salawal")));
		bd = bd.subtract(NumberUtils.toBigdecimal(hashMap.get("penggunaansal")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("silpa")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("koreksi")));
		bd = bd.add(NumberUtils.toBigdecimal(hashMap.get("lainnya")));	
				
		return bd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	List<Map<String, Object>> mappingListToTree(List<HashMap> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (HashMap m : list) {
			Map<String, Object> tingkat1 = findDataFromList(result, "kd_rek_1",
					(String) m.get("kd_rek_1"));
			// String kd_rek_1_m = (String) m.get("kd_rek_1");
			if (tingkat1 == null) {
				tingkat1 = new HashMap<String, Object>();
				tingkat1.put("kd_rek_1", m.get("kd_rek_1"));
				tingkat1.put("nm_rek_1", m.get("nm_rek_1"));
				tingkat1.put("sum_sebelum_kd_rek_1", m.get("sebelum_eliminasi"));
				tingkat1.put("sum_setelah_kd_rek_1", m.get("setelah_eliminasi"));
				tingkat1.put("list_tingkat2",
						new ArrayList<Map<String, Object>>());
				result.add(tingkat1);
			} else {
				tingkat1.put(
						"sum_sebelum_kd_rek_1",
						NumberUtils.toBigdecimal(
								tingkat1.get("sum_sebelum_kd_rek_1")).add(
								NumberUtils.toBigdecimal(m
										.get("sebelum_eliminasi"))));
				tingkat1.put(
						"sum_setelah_kd_rek_1",
						NumberUtils.toBigdecimal(
								tingkat1.get("sum_setelah_kd_rek_1")).add(
								NumberUtils.toBigdecimal(m
										.get("setelah_eliminasi"))));
			}

			List<Map<String, Object>> listTingkat2 = (List<Map<String, Object>>) tingkat1
					.get("list_tingkat2");
			Map<String, Object> tingkat2 = findDataFromList(listTingkat2,
					"kd_rek_2", (String) m.get("kd_rek_2"));
			if (tingkat2 == null) {
				tingkat2 = new HashMap<String, Object>();
				tingkat2.put("kd_rek_2", m.get("kd_rek_2"));
				tingkat2.put("nm_rek_2", m.get("nm_rek_2"));
				tingkat2.put("sum_sebelum_kd_rek_2", m.get("sebelum_eliminasi"));
				tingkat2.put("sum_setelah_kd_rek_2", m.get("setelah_eliminasi"));
				tingkat2.put("list_tingkat3",
						new ArrayList<Map<String, Object>>());
				listTingkat2.add(tingkat2);
			} else {
				tingkat2.put(
						"sum_sebelum_kd_rek_2",
						NumberUtils.toBigdecimal(
								tingkat2.get("sum_sebelum_kd_rek_2")).add(
								NumberUtils.toBigdecimal(m
										.get("sebelum_eliminasi"))));
				tingkat2.put(
						"sum_setelah_kd_rek_2",
						NumberUtils.toBigdecimal(
								tingkat2.get("sum_setelah_kd_rek_2")).add(
								NumberUtils.toBigdecimal(m
										.get("setelah_eliminasi"))));

			}

			List<Map<String, Object>> listTingkat3 = (List<Map<String, Object>>) tingkat2
					.get("list_tingkat3");
			listTingkat3.add(m);

		}
		return result;

	}

	Map<String, Object> findDataFromList(List<Map<String, Object>> list,
			String key, String val) {
		for (Map<String, Object> m : list) {
			if (m.get(key).equals(val)) {
				return m;
			}
		}
		return null;
	}

}
