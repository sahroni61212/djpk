package com.sh.djpk.econsole.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TransferDao {

	List<HashMap> getAllTransfer();
	
	public int inserttrf(
			@Param("params") Map<String, Object> params
			);
	
	public int updatetrf(
			@Param("params") Map<String, Object> params
			);
	public int deletetrf(
			@Param("params") Map<String, Object> params
			);
	
}
