package com.sh.djpk.econsole.rest.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface GlobalParamDao {

	List<HashMap> getAllData(@Param("globalParamParent") String globalParamParent,
			@Param("globalParamValue") String globalParamValue);
}
