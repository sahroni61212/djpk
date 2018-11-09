package com.sh.djpk.econsole.rest.dao;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface UserGroupDao {
	
	
	List<HashMap> getUsrGrp();
	
	public int insert(@Param("params") Map<String, Object> params);

	public int update(@Param("params") Map<String, Object> params);

	public int delete(@Param("params") Map<String, Object> params);
	
	List<HashMap> getAllMenu(@Param("kdUser") int kdUser);
	
	List<HashMap> getRefUserMenuByKdUser(@Param("kdUser") int kdUser); 
	
	List<HashMap> getRefUserMenuByPk(@Param("kdUser") int kdUser, @Param("menu") int menu); 
	
	public int insertRefUserMenu(@Param("params") Map<String, Object> params);

	public int updateRefUserMenu(@Param("params") Map<String, Object> params);
	
	public int deleteRefUserMenu(@Param("params") Map<String, Object> params);

}
