package com.sh.djpk.econsole.ui.controller.pelaporan;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PelaporanWilayah extends PelaporanNonNasional {

	private static final long serialVersionUID = 1L;

	@Override
	public int getKodeLaporan() {
		return 2;
	}
	
	@Override
	public String getTitle() {
		String selectedName = getSelectOthersName();		
		String title = "";
		if("1".equals(selectedJenisLaporan)){
			title = "LAPORAN REALISASI ANGGARAN KONSOLIDASIAN WILAYAH " + selectedName;
		}else if("2".equals(selectedJenisLaporan)){
			title = "NERACA KONSOLIDASIAN WILAYAH " + selectedName;
		}else if("3".equals(selectedJenisLaporan)){
			title = "LAPORAN OPERASIONAL KONSOLIDASIAN WILAYAH " + selectedName;
		}else if("4".equals(selectedJenisLaporan)){
			title = "LAPORAN ARUS KAS KONSOLIDASIAN WILAYAH " + selectedName;
		}else if("5".equals(selectedJenisLaporan)){
			title = "LAPORAN PERUBAHAN EKUITAS KONSOLIDASIAN WILAYAH " + selectedName;
		}else if("6".equals(selectedJenisLaporan)){
			title = "LAPORAN SALDO ANGGARAN LEBIH KONSOLIDASIAN WILAYAH " + selectedName;
		}
		return title;
	}


}
