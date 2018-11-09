package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface WilayahDao {
	
	
	List<HashMap> getWilayah();
	
	public int insertwil(
			@Param("params") Map<String, Object> params
			);
	
	public int updatewil(
			@Param("params") Map<String, Object> params
			);
	public int deletewil(
			@Param("params") Map<String, Object> params
			);
	
	

}
