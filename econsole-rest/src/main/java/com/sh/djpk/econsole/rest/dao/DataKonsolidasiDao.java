package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DataKonsolidasiDao {
	
	
	List<HashMap> getAll();
	
	public int insertcompheader(
			@Param("params") Map<String, Object> params
			);
	
	public int insertcomp5(
			@Param("params") Map<String, Object> params,
			@Param("cheader_id") String header_id,
			@Param("user_id") int user_id
			);
//	public int deleteperiode(
//			@Param("params") Map<String, Object> params
//			);
	

}
