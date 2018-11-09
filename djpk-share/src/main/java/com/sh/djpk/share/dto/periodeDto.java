package com.sh.djpk.share.dto;

public class periodeDto {
	
	public String kode;
	public String namaPeriode;
	
	public periodeDto (String kode, String nama){
		this.kode = kode;
		this.namaPeriode = nama;
		
		
	}
	
	public String getKode() {
		return kode;
	}
	
	
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getNamaPeriode() {
		return namaPeriode;
	}
	public void setNamaPeriode(String namaPeriode) {
		this.namaPeriode = namaPeriode;
	}
	
	

}
