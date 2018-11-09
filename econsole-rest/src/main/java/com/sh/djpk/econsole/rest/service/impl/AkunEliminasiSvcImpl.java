package com.sh.djpk.econsole.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.AkunEliminasiDao;
import com.sh.djpk.econsole.rest.service.AkunEliminasiSvc;


@Service("akunEliminasiSvc")
@Transactional(readOnly = false)
public class AkunEliminasiSvcImpl implements AkunEliminasiSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(AkunEliminasiSvcImpl.class);
	
	@Autowired AkunEliminasiDao akunDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getOnTahun(String tahunAnggaran) {
		LOGGER.info(
				"try get data per Pemda");
		List<HashMap> lst = akunDao.getEliminasi(tahunAnggaran);
		
		LOGGER.trace("data from db={}", lst);
		
		return lst;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getOnTahunPemda(String kodePemda, String tahunAnggaran) {
		LOGGER.info(
				"try get data per Pemda");
		List<HashMap> lst = akunDao.getEliminasiPemda(kodePemda, tahunAnggaran);
		
		LOGGER.trace("data from db={}", lst);
		
		return lst;
	}

	@Override
	public List<HashMap> getHeader() {
		LOGGER.info(
				"try get header ID");
		List<HashMap> lst = akunDao.getHead();
		
		LOGGER.trace("data from db={}", lst);
		
		return lst;
	}

	@Override
	public int getProses(Map<String, Object> params) {
		LOGGER.info(
				"try get resiprokal");
		int lapId = Integer.valueOf((String) params.get("laporanId").toString());
		String headId = (String) params.get("headerId").toString();
		String thn = (String) params.get("tahun").toString();
		int elim = 0;
		List<HashMap> lst = akunDao.getResiprokal(thn,lapId);
		int resId = 0;
		
		Map<String, Object> elimhd = new HashMap<String, Object>();
		elimhd.put("cheader_id", headId);
		elimhd.put("tahun", thn);
		elimhd.put("laporan_id", lapId);
		
		
		elimhd.put("status_balance", "1");
		
		int perid = 99;
		int jmlakun=0;
		
		if (!(lst.isEmpty())){
			
			
			
			for(int c =0;c<lst.size();c++){
				Map<String, Object> resip = new HashMap<String, Object>();
				resip = lst.get(c);
				resId = (int) resip.get("resiprokal_id");
				List<HashMap> lstakun = akunDao.getAkun(thn,resId);
				jmlakun = jmlakun+lstakun.size();
				System.out.println("============== list akun ==" + lstakun);
				if (!(lstakun.isEmpty())){
					for(int d =0;d<lstakun.size();d++){
						Map<String, Object> akun = new HashMap<String, Object>();
						akun=lstakun.get(d);
						String Kd1 = akun.get("kd_rek1").toString();
						String Kd2 = akun.get("kd_rek2").toString();
						String Kd3 = akun.get("kd_rek3").toString();
						String Kd4 = akun.get("kd_rek4").toString();
						String Kd5 = akun.get("kd_rek5").toString();
						int lvl_data =Integer.valueOf(akun.get("level_data").toString());
						int loc_akun =Integer.valueOf(akun.get("location_account").toString());
						List<HashMap> lstcomp = new ArrayList<HashMap>();
						if((lvl_data == 4) && (loc_akun == 3)){
							
							lstcomp = akunDao.getCompilation43(Kd1,Kd2,Kd3,Kd4,thn, headId, resId);
							System.out.println("======================= list compiltion ==" +lstcomp);
							if (!(lstcomp.isEmpty())){
								System.out.println("============================ insert eliminasi");
								String kdpmd = "";
								for (int el =0; el<lstcomp.size();el++){
									Map<String, Object> comp1 = new HashMap<String, Object>();
									
									Map<String, Object> saldo = new HashMap<String, Object>();
									
									comp1 = lstcomp.get(el);
									
									System.out.println("======= kode pemda sebelum == "+kdpmd+"========kode pemda current == "+comp1.get("kd_pemda").toString() );
									Map<String, Object> isi = new HashMap<String, Object>();
									if (!(kdpmd.equals(comp1.get("kd_pemda").toString())) ){
									isi.put("tahun", comp1.get("tahun").toString());
									isi.put("kd_pemda", comp1.get("kd_pemda").toString());
									isi.put("kd_rek_1", comp1.get("kd_rek_1").toString());
									isi.put("kd_rek_2", comp1.get("kd_rek_2").toString());
									isi.put("kd_rek_3", comp1.get("kd_rek_3").toString());
									isi.put("kd_rek_4", comp1.get("kd_rek_4").toString());
									isi.put("kd_rek_5", "00");
									isi.put("nama_rek", comp1.get("nm_rek_4").toString());
									isi.put("d_k", comp1.get("d_k").toString());
//									isi.put("nilai", comp1.get("realisasi"));
									isi.put("transfer_id", akun.get("transfer_id"));
									isi.put("cheader_id", comp1.get("cheader_id").toString());
									isi.put("periode_id", comp1.get("periode_id"));
									saldo = akunDao.getSaldoAkun(isi, headId);
									isi.put("nilai", saldo.get("sum"));
									elim = akunDao.insertEliminasi(isi, headId);
									perid = Integer.valueOf(comp1.get("periode_id").toString());
									}
									kdpmd= comp1.get("kd_pemda").toString();
									
								}
							}else {
								System.out.println("============================ isinya kosong");
							}
							
						} else if((lvl_data == 4) && (loc_akun == 2)){
							lstcomp = akunDao.getCompilation42(Kd1,Kd2,Kd3,Kd4,thn, headId, resId);
							System.out.println("======================= list compiltion ==" +lstcomp);
							if (!(lstcomp.isEmpty())){
								System.out.println("============================ insert eliminasi");
								String kdpmd = "";
								for (int el =0; el<lstcomp.size();el++){
									Map<String, Object> comp1 = new HashMap<String, Object>();
									
									Map<String, Object> saldo = new HashMap<String, Object>();
									
									comp1 = lstcomp.get(el);
									Map<String, Object> isi = new HashMap<String, Object>();
									if (!(kdpmd.equals(comp1.get("kd_pemda").toString()))){
									isi.put("tahun", comp1.get("tahun").toString());
									isi.put("kd_pemda", comp1.get("kd_pemda").toString());
									isi.put("kd_rek_1", comp1.get("kd_rek_1").toString());
									isi.put("kd_rek_2", comp1.get("kd_rek_2").toString());
									isi.put("kd_rek_3", comp1.get("kd_rek_3").toString());
									isi.put("kd_rek_4", comp1.get("kd_rek_4").toString());
									isi.put("kd_rek_5", "00");
									isi.put("nama_rek", comp1.get("nm_rek_4").toString());
									isi.put("d_k", comp1.get("d_k").toString());
//									isi.put("nilai", comp1.get("realisasi"));
									isi.put("transfer_id", akun.get("transfer_id"));
									isi.put("cheader_id", comp1.get("cheader_id").toString());
									isi.put("periode_id", comp1.get("periode_id"));
									saldo = akunDao.getSaldoAkun(isi, headId);
									isi.put("nilai", saldo.get("sum"));
									elim = akunDao.insertEliminasi(isi, headId);
									perid = Integer.valueOf(comp1.get("periode_id").toString());
									}
									kdpmd= comp1.get("kd_pemda").toString();
									
								}
							}else {
								System.out.println("============================ isinya kosong");
							}
							
						} else if((lvl_data == 5) && (loc_akun == 2)){
							lstcomp = akunDao.getCompilation52(Kd1,Kd2,Kd3,Kd4,Kd5,thn, headId, resId);
							System.out.println("======================= list compiltion ==" +lstcomp);
							if (!(lstcomp.isEmpty())){
								System.out.println("============================ insert eliminasi");
								
								for (int el =0; el<lstcomp.size();el++){
									Map<String, Object> comp1 = new HashMap<String, Object>();
									
									Map<String, Object> isi = new HashMap<String, Object>();
									
									isi.put("tahun", comp1.get("tahun").toString());
									isi.put("kd_pemda", comp1.get("kd_pemda").toString());
									isi.put("kd_rek_1", comp1.get("kd_rek_1").toString());
									isi.put("kd_rek_2", comp1.get("kd_rek_2").toString());
									isi.put("kd_rek_3", comp1.get("kd_rek_3").toString());
									isi.put("kd_rek_4", comp1.get("kd_rek_4").toString());
									isi.put("kd_rek_5", comp1.get("kd_rek_5").toString());
									isi.put("nama_rek", comp1.get("nm_rek_5").toString());
									isi.put("d_k", comp1.get("d_k").toString());
									isi.put("nilai", comp1.get("realisasi"));
									isi.put("transfer_id", akun.get("transfer_id"));
									isi.put("cheader_id", comp1.get("cheader_id").toString());
									isi.put("periode_id", comp1.get("periode_id"));
//									saldo = akunDao.getSaldoAkun(isi, headId);
//									isi.put("nilai", saldo.get("sum"));
									elim = akunDao.insertEliminasi(isi, headId);
									perid = Integer.valueOf(comp1.get("periode_id").toString());
									
									
								}
							}else {
								System.out.println("============================ isinya kosong");
							}
							
						} else if((lvl_data == 5) && (loc_akun == 3)){
							lstcomp = akunDao.getCompilation53(Kd1,Kd2,Kd3,Kd4,Kd5,thn, headId, resId);
							System.out.println("======================= list compiltion ==" +lstcomp);
							if (!(lstcomp.isEmpty())){
								System.out.println("============================ insert eliminasi");
								
								for (int el =0; el<lstcomp.size();el++){
									Map<String, Object> comp1 = new HashMap<String, Object>();
									
									Map<String, Object> saldo = new HashMap<String, Object>();
									
									comp1 = lstcomp.get(el);
									Map<String, Object> isi = new HashMap<String, Object>();
				
									isi.put("tahun", comp1.get("tahun").toString());
									isi.put("kd_pemda", comp1.get("kd_pemda").toString());
									isi.put("kd_rek_1", comp1.get("kd_rek_1").toString());
									isi.put("kd_rek_2", comp1.get("kd_rek_2").toString());
									isi.put("kd_rek_3", comp1.get("kd_rek_3").toString());
									isi.put("kd_rek_4", comp1.get("kd_rek_4").toString());
									isi.put("kd_rek_5", comp1.get("kd_rek_5").toString());
									isi.put("nama_rek", comp1.get("nm_rek_5").toString());
									isi.put("d_k", comp1.get("d_k").toString());
									isi.put("nilai", comp1.get("realisasi"));
									isi.put("transfer_id", akun.get("transfer_id"));
									isi.put("cheader_id", comp1.get("cheader_id").toString());
									isi.put("periode_id", comp1.get("periode_id"));
//									saldo = akunDao.getSaldoAkun(isi, headId);
//									isi.put("nilai", saldo.get("sum"));
									elim = akunDao.insertEliminasi(isi, headId);
									perid = Integer.valueOf(comp1.get("periode_id").toString());
									
									
									
								}
							}else {
								System.out.println("============================ isinya kosong");
							}
							
						}
					}	
				}
			}
		}
		
		int u = akunDao.updateheader(headId);
		elimhd.put("otomatis", jmlakun);
		elimhd.put("manual", 0);
		elimhd.put("periode_id", perid);
		
		int inelimhd = akunDao.insertelimhead(elimhd);
		
		return 1;
	}

	@Override
	public List<HashMap> getElimHead(String thn) {
		LOGGER.info(
				"try get elimination header");
		List<HashMap> lst = akunDao.getelimhead(thn);
		
		LOGGER.trace("data from db={}", lst);
		
		return lst;
	}
	
	
	

}
