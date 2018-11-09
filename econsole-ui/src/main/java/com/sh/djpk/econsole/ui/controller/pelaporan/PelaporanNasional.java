package com.sh.djpk.econsole.ui.controller.pelaporan;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PelaporanNasional extends Pelaporan {

	private static final long serialVersionUID = 1L;

	public PelaporanNasional() {
		super();
	}

	@Override
	public void cari() {
		LOGGER.info("CARI DATA LAPORAN NASIONAL");
		LOGGER.info("periode laporan ={}", selectedPeriodeLaporan);
		ambilData(1);
	}
	
	@Override
	public String getTitle() {
		String title = "";
		if("1".equals(selectedJenisLaporan)){
			title = "LAPORAN REALISASI ANGGARAN KONSOLIDASIAN NASIONAL";
		}else if("2".equals(selectedJenisLaporan)){
			title = "NERACA KONSOLIDASIAN NASIONAL";
		}else if("3".equals(selectedJenisLaporan)){
			title = "LAPORAN OPERASIONAL KONSOLIDASIAN NASIONAL";
		}else if("4".equals(selectedJenisLaporan)){
			title = "LAPORAN ARUS KAS KONSOLIDASIAN NASIONAL";
		}else if("5".equals(selectedJenisLaporan)){
			title = "LAPORAN PERUBAHAN EKUITAS KONSOLIDASIAN NASIONAL";
		}else if("6".equals(selectedJenisLaporan)){
			title = "LAPORAN SALDO ANGGARAN LEBIH KONSOLIDASIAN NASIONAL";
		}
		return title;
	}

	// @Override
	// public void cari() {
	// LOGGER.info("CARI DATA LAPORAN NASIONAL");
	// LOGGER.info("jenis laporan = {}, periode laporan ={}, others={}",
	// selectedJenisLaporan, selectedPeriodeLaporan, selectedOthers);
	// int kodeLaporan = NumberUtils.toInteger(selectedJenisLaporan);
	// if (kodeLaporan == 1 || kodeLaporan == 2 || kodeLaporan == 3
	// || kodeLaporan == 4) {
	// dataPencarian = ambilData(1);
	// // dataPencarian = ambilData() == null ? new ArrayList<Map<String,
	// // Object>>()
	// // :
	// // (ArrayList<Map<String,Object>>)ambilData().get("list_tingkat1");
	// }
	// }

}
