package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sh.djpk.share.model.User;

public interface AkunResiprokalSvc {

	List<HashMap> getOnTahun(String tahun);
	
	List<HashMap> getAkr1();
	
	List<HashMap> getAkr1a(String lap);
	
	List<HashMap> getAkr2(String akr1);
	
	List<HashMap> getAkr3(String akr1, String akr2);

	List<HashMap> getAkr4(String akr1,String akr2, String akr3);
	
	List<HashMap> getAkr5(String akr1,String akr2, String akr3, String akr4);
	
	public int getProses(Map<String, Object> params);
	
	
}
