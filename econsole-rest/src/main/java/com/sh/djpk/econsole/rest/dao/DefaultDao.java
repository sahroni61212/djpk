package com.sh.djpk.econsole.rest.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Qualifier;

public interface DefaultDao {

	@Select("select CURRENT_TIMESTAMP")
	public List<HashMap> getCurrentTImestamp();

	@Update("SET search_path TO econsole,public")
	public void updateSearchPath();
}
