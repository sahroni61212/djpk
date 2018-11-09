package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;

import com.sh.djpk.share.model.User;

public interface RptRekapAkunEliminasiSvc {

	List<HashMap> getOnPemda(String kodePemda, String tahunAnggaran, int period);
	
}
