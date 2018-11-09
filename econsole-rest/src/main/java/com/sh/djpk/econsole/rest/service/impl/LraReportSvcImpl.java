package com.sh.djpk.econsole.rest.service.impl;

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

import com.sh.djpk.econsole.rest.dao.LraReportDao;
import com.sh.djpk.econsole.rest.service.LraReportSvc;
import com.sh.djpk.share.util.NumberUtils;

@Service("lraReportSvc")
@Transactional(readOnly = false)
public class LraReportSvcImpl implements LraReportSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(LraReportSvcImpl.class);

	@Autowired
	LraReportDao lraReportDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getLraReportData(int tahunAnggaran, int periodeId,
			int kodeLaporan, HashMap othersParam) {
		LOGGER.info(
				"try get data lra report tahunAnggaran={}, periodeId={}, kodeLaporan={}, othersParam={}",
				tahunAnggaran, periodeId, kodeLaporan, othersParam);
		String thnAng = String.valueOf(tahunAnggaran);
		List<HashMap> l = lraReportDao.getLraReportData(thnAng, periodeId,
				kodeLaporan, othersParam);
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getRefPemdaDua(int tahunAnggaran, int kodeLaporan,
			HashMap othersParam) {
		LOGGER.info(
				"try get data ref_pemda2 tahunAnggaran={}, kodeLaporan={}, othersParam={}",
				tahunAnggaran, kodeLaporan, othersParam);
		String thnAng = String.valueOf(tahunAnggaran);
		List<HashMap> l = lraReportDao.getRefPemdaDua(thnAng, kodeLaporan,
				othersParam);
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getPeriodeCb() {
		LOGGER.info("try get data periode to combobox");
		List<HashMap> l = lraReportDao.getPeriodeCb();
		return l;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getLraReportDataTree(
			int tahunAnggaran, int periodeId, int kodeLaporan,
			HashMap othersParam) {
		LOGGER.info(
				"try get data lra report tree tahunAnggaran={}, periodeId={}, kodeLaporan={}, othersParam={}",
				tahunAnggaran, periodeId, kodeLaporan, othersParam);
		List<HashMap> l = getLraReportData(tahunAnggaran, periodeId,
				kodeLaporan, othersParam);
		return mappingListToTree(l);
	}

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
			}else{
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
