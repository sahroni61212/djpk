package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TransferSvc {

	List<HashMap> getAllPemda();
	
	public int insert(Map<String, Object> params);
	
	public int update(Map<String, Object> params);
	public int delete(Map<String, Object> params);
}
