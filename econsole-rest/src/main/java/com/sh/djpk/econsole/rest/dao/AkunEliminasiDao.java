package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface AkunEliminasiDao {
	
	
	List<HashMap> getEliminasi(
			@Param("tahunAnggaran") String tahunAnggaran
			);
	
	List<HashMap> getEliminasiPemda(
			@Param("kodePemda") String kdpemda,
			@Param("tahunAnggaran") String tahunAnggaran
			);
	
	List<HashMap> getHead(
			);
	
	List<HashMap> getResiprokal(
			@Param("thn") String thn,
			@Param("lapId") int lapId
			);
	
	List<HashMap> getAkun(
			@Param("thn") String thn,
			@Param("resId") int resId
			);
	List<HashMap> getCompilation53(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("kd4") String kd4,
			@Param("kd5") String kd5,
			@Param("thn") String thn,
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	List<HashMap> getCompilation43(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("kd4") String kd4,
			@Param("thn") String thn,
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	List<HashMap> getCompilation33(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("thn") String thn,			
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	
	List<HashMap> getCompilation52(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("kd4") String kd4,
			@Param("kd5") String kd5,
			@Param("thn") String thn,
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	
	List<HashMap> getCompilation42(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("kd4") String kd4,
			@Param("thn") String thn,
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	
	List<HashMap> getCompilation32(
			@Param("kd1") String kd1,
			@Param("kd2") String kd2,
			@Param("kd3") String kd3,
			@Param("thn") String thn,
			@Param("headId") String headId,
			@Param("resId") int resId
			);
	
	public int insertEliminasi(
			@Param("params") Map<String, Object> params,
			@Param("headerId") String header_id
			);
	
	public int updateheader(
			@Param("headId") String header_id
			);
	HashMap getSaldoAkun(
			@Param("params") Map<String, Object> params,
			@Param("headerId") String header_id
			
			);
	
	public int insertresiprokal(
			@Param("params") Map<String, Object> params
			);
	
	List<HashMap> getelimhead(
			@Param("thn") String thn
			);
	
	public int insertelimhead(
			@Param("params") Map<String, Object> params
			);

}
