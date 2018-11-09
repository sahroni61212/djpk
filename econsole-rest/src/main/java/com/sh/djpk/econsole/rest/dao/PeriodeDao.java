package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface PeriodeDao {
	
	
	List<HashMap> getPeriode();
	
	public int insertperiode(
			@Param("params") Map<String, Object> params
			);
	
	public int updateperiode(
			@Param("params") Map<String, Object> params
			);
	public int deleteperiode(
			@Param("params") Map<String, Object> params
			);
	

}
