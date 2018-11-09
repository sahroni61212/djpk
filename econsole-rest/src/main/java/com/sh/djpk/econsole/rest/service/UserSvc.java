package com.sh.djpk.econsole.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.User;

public interface UserSvc {

	List<HashMap> getAllPaging(int pageSize, int pageSequence,String direction, String orderBy,
			Map<String, Object> params);

	public int insert(Map<String, Object> params);

	public int update(Map<String, Object> params);

	public int delete(Map<String, Object> params);
	
	public RestResponse getAllAuthItem();
	
	public RestResponse login(Map<String, Object> params);
	
	public RestResponse getMenuAkses(
			Map<String, Object> params);
}
