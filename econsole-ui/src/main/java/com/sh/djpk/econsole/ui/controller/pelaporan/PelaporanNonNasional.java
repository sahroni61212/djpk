package com.sh.djpk.econsole.ui.controller.pelaporan;

public class PelaporanNonNasional  extends Pelaporan{

	private static final long serialVersionUID = 1L;

	
	
	public PelaporanNonNasional() {
		super();
		selectOneOthers = getDataSelectOthers(getKodeLaporan());
	}

	

	@Override
	public void cari() {
		LOGGER.info("CARI DATA LAPORAN NON NASIONAL");
		LOGGER.info("periode laporan ={}, others={}",
				selectedPeriodeLaporan, selectedOthers);
		ambilData(getKodeLaporan());

	}
	
	public int getKodeLaporan(){
		return 99999;
	}
}
