package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sh.djpk.share.model.User;

public interface PeriodeSvc {

	List<HashMap> getAll();
	
	public int insert(Map<String, Object> params);
	
	public int update(Map<String, Object> params);
	public int delete(Map<String, Object> params);
	
}
