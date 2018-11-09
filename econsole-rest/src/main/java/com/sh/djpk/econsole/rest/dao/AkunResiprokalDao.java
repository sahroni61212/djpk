package com.sh.djpk.econsole.rest.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface AkunResiprokalDao {
	
	
	List<HashMap> getResiprokal(
			@Param("tahun") String tahunAnggaran
			);
	
	HashMap getNama3(
			@Param("akun") String akun
			);
	
	HashMap getNama4(
			@Param("akun") String akun
			);
	
	HashMap getNama5(
			@Param("akun") String akun
			);
	List<HashMap> getAkr1();
	
	List<HashMap> getAkr1a();
	List<HashMap> getAkr1b();
	List<HashMap> getAkr1c();
	
	List<HashMap> getAkr2(
			@Param("akr1") String akr1
			);
	
	List<HashMap> getAkr3(
			@Param("akr1") String akr1,
			@Param("akr2") String akr2
			);
	
	List<HashMap> getAkr4(
			@Param("akr1") String akr1,
			@Param("akr2") String akr2,
			@Param("akr3") String akr3
			);
	
	List<HashMap> getAkr5(
			@Param("akr1") String akr1,
			@Param("akr2") String akr2,
			@Param("akr3") String akr3,
			@Param("akr4") String akr4
			);
	
	
//	List<HashMap> getEliminasiPemda(
//			@Param("kodePemda") String kdpemda,
//			@Param("tahunAnggaran") String tahunAnggaran
//			);
	
//	List<HashMap> getHead(
//			);
//	
//	List<HashMap> getResiprokal(
//			@Param("lapId") int lapId
//			);
//	
//	List<HashMap> getAkun(
//			@Param("resId") int resId
//			);
//	
//	List<HashMap> getCompilation43(
//			@Param("kd1") String kd1,
//			@Param("kd2") String kd2,
//			@Param("kd3") String kd3,
//			@Param("kd4") String kd4,
//			@Param("headId") String headId,
//			@Param("resId") int resId
//			);
//	
//	List<HashMap> getCompilation42(
//			@Param("kd1") String kd1,
//			@Param("kd2") String kd2,
//			@Param("kd3") String kd3,
//			@Param("kd4") String kd4,
//			@Param("headId") String headId,
//			@Param("resId") int resId
//			);
//	
	public int insertresiprokal(
			@Param("params") Map<String, Object> params
			);
//	HashMap getSaldoAkun(
//			@Param("params") Map<String, Object> params,
//			@Param("headerId") String header_id
//			
//			);
	
	

}
