package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sh.djpk.share.model.User;

public interface AkunEliminasiSvc {

	List<HashMap> getOnTahun(String tahunAnggaran);
	
	List<HashMap> getOnTahunPemda(String kodePemda, String tahunAnggaran);
	
	List<HashMap> getHeader();
	
	public int getProses(Map<String, Object> params);
	
	List<HashMap> getElimHead(String tahun);
	
}
