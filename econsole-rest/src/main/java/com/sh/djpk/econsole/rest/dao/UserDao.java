package com.sh.djpk.econsole.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserDao {

	List<HashMap> getAllPaging(@Param("pageSize") int pageSize,
			@Param("pageSequence") int pageSequence,
			@Param("direction") String direction,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	public int insertUser(@Param("params") Map<String, Object> params);

	public int updateUser(@Param("params") Map<String, Object> params);

	public int deleteUser(@Param("params") Map<String, Object> params);
	
	public int insertAuthAssignment(@Param("params") Map<String, Object> params);
	
	public int insertUserPemda(@Param("params") Map<String, Object> params);
	
	public int deleteAuthAssignment(@Param("params") Map<String, Object> params);
	
	public int deleteUserPemda(@Param("params") Map<String, Object> params);
	
	public int updateAuthAssignment(@Param("params") Map<String, Object> params);
	
	public int updateUserPemda(@Param("params") Map<String, Object> params);

	public List<HashMap> getAllAuthItem();
	
	List<HashMap> login(
			@Param("params") Map<String, Object> params);
	
	List<HashMap> getMenuAkses(
			@Param("params") Map<String, Object> params);
}
