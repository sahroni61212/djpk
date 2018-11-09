package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface RptRekapAkunEliminasiDao {
	
	
	List<HashMap> getRekapEliminasi(
			@Param("kodePemda") String kdpemda,
			@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId
			);
	
	HashMap getSaldoAkun(
			@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId,
			@Param("level3") String level3, 
			@Param("kodePemda") String kdpemda 
			);
	
	

}
