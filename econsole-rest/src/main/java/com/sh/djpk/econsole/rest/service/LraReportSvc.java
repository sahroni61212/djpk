package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LraReportSvc {

	List<HashMap> getLraReportData(int tahunAnggaran, int periodeId,
			int kodeLaporan, HashMap othersParam);

	List<HashMap> getRefPemdaDua(int tahunAnggaran, int kodeLaporan,
			HashMap othersParam);

	List<HashMap> getPeriodeCb();

	List<Map<String, Object>> getLraReportDataTree(int tahunAnggaran,
			int periodeId, int kodeLaporan, HashMap othersParam);
}
