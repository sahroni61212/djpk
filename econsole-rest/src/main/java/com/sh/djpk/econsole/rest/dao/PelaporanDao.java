package com.sh.djpk.econsole.rest.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PelaporanDao {

	@SuppressWarnings("rawtypes")
	List<HashMap> getCompilationRecord5(@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId,
			@Param("kodeLaporan") int kodeLaporan,@Param("jenisLaporan") int jenisLaporan, @Param("othersParam") HashMap othersParam);
	
	
	@SuppressWarnings("rawtypes")
	List<HashMap> getCompilationLak(@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId,
			@Param("kodeLaporan") int kodeLaporan, @Param("othersParam") HashMap othersParam,@Param("tahunAnggaranSebelum") String tahunAnggaranSebelum);
	
	
	@SuppressWarnings("rawtypes")
	List<HashMap> getCompilationLpe(@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId,
			@Param("kodeLaporan") int kodeLaporan, @Param("othersParam") HashMap othersParam);
	
	@SuppressWarnings("rawtypes")
	List<HashMap> getCompilationLpsal(@Param("tahunAnggaran") String tahunAnggaran,
			@Param("periodeId") int periodeId,
			@Param("kodeLaporan") int kodeLaporan, @Param("othersParam") HashMap othersParam);
}
