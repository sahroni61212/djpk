package com.sh.djpk.econsole.ui.controller.pelaporan;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class PelaporanPemda extends PelaporanNonNasional{

	private static final long serialVersionUID = 1L;

	
	@Override
	public int getKodeLaporan() {
		return 4;
	}
	
	@Override
	public String getTitle() {
		String selectedName = getSelectOthersName();		
		String title = "";
		if("1".equals(selectedJenisLaporan)){
			title = "LAPORAN REALISASI ANGGARAN PEMDA " + selectedName;
		}else if("2".equals(selectedJenisLaporan)){
			title = "NERACA PEMDA " + selectedName;
		}else if("3".equals(selectedJenisLaporan)){
			title = "LAPORAN OPERASIONAL PEMDA " + selectedName;
		}else if("4".equals(selectedJenisLaporan)){
			title = "LAPORAN ARUS KAS PEMDA " + selectedName;
		}else if("5".equals(selectedJenisLaporan)){
			title = "LAPORAN PERUBAHAN EKUITAS PEMDA " + selectedName;
		}else if("6".equals(selectedJenisLaporan)){
			title = "LAPORAN SALDO ANGGARAN LEBIH PEMDA " + selectedName;
		}
		return title;
	}
}
