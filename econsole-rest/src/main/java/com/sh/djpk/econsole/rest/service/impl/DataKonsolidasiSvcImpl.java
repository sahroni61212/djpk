package com.sh.djpk.econsole.rest.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.DataKonsolidasiDao;
import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.service.DataKonsolidasiSvc;


@Service("konsolSvc")
@Transactional(readOnly = false)
public class DataKonsolidasiSvcImpl implements DataKonsolidasiSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(DataKonsolidasiSvcImpl.class);
	
	@Autowired DataKonsolidasiDao dataKonsolDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAll() {
		LOGGER.info(
				"try get data konsolidasi header");
		List<HashMap> l = dataKonsolDao.getAll();
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	public int insert(Map<String, Object> params) {
		
	
	LOGGER.info("try update params={}", params);
	List<Map<String, Object>> listcomp5 = new ArrayList<Map<String,Object>>();
	Map<String, Object> header = new HashMap<String, Object>();
	
	String header_id = (String) params.get("id");
	int user_id = (int) params.get("user_id");
	
	header.put("id", header_id);
	header.put("tahun", params.get("tahun"));
	header.put("bulan", params.get("period_id"));
	header.put("laporan_id", params.get("laporan_id"));
	header.put("lvl_data", params.get("level"));
	header.put("tgl_kirim_baru", params.get("tgl_kirim"));
	header.put("aktif","Y");
	header.put("status", 2);
	header.put("user_id", user_id);

	listcomp5 = (List<Map<String, Object>>) params.get("content");
	
	
	
	
	LOGGER.info("listcomp5={}", listcomp5);
	int jmlpemda = 0;
	String pmdawal = "";
	for (int s = 0; s<listcomp5.size(); s++){
		Map<String, Object> isi = new HashMap<String, Object>();
		String tahun = (String) listcomp5.get(s).get("tahun").toString();
		String kd_provinsi = (String) listcomp5.get(s).get("kd_provinsi").toString();
		String kd_pemda = (String) listcomp5.get(s).get("kd_pemda").toString();
		String kd_satker = (String) listcomp5.get(s).get("kd_satker").toString();
		String periode_id = (String) listcomp5.get(s).get("periode_id").toString();
		String perubahan_id ="0";
		if (listcomp5.get(s).get("perubahan_id") != null){
			perubahan_id = (String) listcomp5.get(s).get("perubahan_id").toString();
		}

		String kd_rek_1 = (String) listcomp5.get(s).get("kd_rek_1").toString();
		String kd_rek_2 = (String) listcomp5.get(s).get("kd_rek_2").toString();
		String kd_rek_3 = (String) listcomp5.get(s).get("kd_rek_3").toString();
		String kd_rek_4 = (String) listcomp5.get(s).get("kd_rek_4").toString();
		String kd_rek_5 = (String) listcomp5.get(s).get("kd_rek_5").toString();
		String nm_rek_1 = (String) listcomp5.get(s).get("nm_rek_1").toString();
		String nm_rek_2 = (String) listcomp5.get(s).get("nm_rek_2").toString();
		String nm_rek_3 = (String) listcomp5.get(s).get("nm_rek_3").toString();
		String nm_rek_4 = (String) listcomp5.get(s).get("nm_rek_4").toString();
		String nm_rek_5 = (String) listcomp5.get(s).get("nm_rek_5").toString();
		
		String anggaran ="0";
		if (listcomp5.get(s).get("anggaran") != null){
			anggaran = (String) listcomp5.get(s).get("anggaran").toString();
		}
		
		String realisasi ="0";
		if (listcomp5.get(s).get("realisasi") != null){
			realisasi = (String) listcomp5.get(s).get("realisasi").toString();
		}
		
		String d_k = (String) listcomp5.get(s).get("d_k");
		
		isi.put("tahun", tahun.trim());
		isi.put("kd_provinsi", Integer.valueOf(kd_provinsi.trim()));
		isi.put("kd_pemda", kd_pemda.trim());
		isi.put("kd_satker", kd_satker.trim());
		isi.put("periode_id", Integer.valueOf(periode_id.trim()));
		isi.put("perubahan_id", Integer.valueOf(perubahan_id.trim()));
		isi.put("kd_rek_1", kd_rek_1.trim());
		isi.put("kd_rek_2", kd_rek_2.trim());
		isi.put("kd_rek_3", kd_rek_3.trim());
		isi.put("kd_rek_4", kd_rek_4.trim());
		isi.put("kd_rek_5", kd_rek_5.trim());
		isi.put("nm_rek_1", nm_rek_1.trim());
		isi.put("nm_rek_2", nm_rek_2.trim());
		isi.put("nm_rek_3", nm_rek_3.trim());
		isi.put("nm_rek_4", nm_rek_4.trim());
		isi.put("nm_rek_5", nm_rek_5.trim());
		isi.put("anggaran", new BigDecimal(anggaran.trim()));
		isi.put("realisasi", new BigDecimal(realisasi.trim()));
		isi.put("d_k", d_k.trim());
		
		kd_pemda = kd_pemda.trim();
		if (s==0){
			pmdawal= kd_pemda;
			jmlpemda = jmlpemda+1;
		}else{
			if (!(pmdawal.equals(kd_pemda))){
				pmdawal=kd_pemda;
				jmlpemda = jmlpemda+1;
			}
		}
		
		LOGGER.info("isi={}", isi);
		int i1 = dataKonsolDao.insertcomp5(isi, header_id, Integer.valueOf(user_id));
		
	}
	
	header.put("jmlPemda", jmlpemda);
	int i = dataKonsolDao.insertcompheader(header);
	
	LOGGER.info("result update user>>data from db={}", i);
	LOGGER.info("params={}", params);
	
	return i;
	}
	
	@Override
	public int insert2(Map<String, Object> params) {
		
	
	LOGGER.info("try update params={}", params);
	List<Map<String, Object>> listcomp5 = new ArrayList<Map<String,Object>>();
	Map<String, Object> header = new HashMap<String, Object>();
	
	String header_id = (String) params.get("id");
	int user_id = (int) params.get("user_id");
	
	header.put("id", header_id);
	header.put("tahun", params.get("tahun"));
	header.put("bulan", Integer.valueOf((String) params.get("period_id").toString()));
	header.put("laporan_id", params.get("laporan_id"));
	header.put("lvl_data", params.get("level"));
	header.put("tgl_kirim_baru", params.get("tgl_kirim"));
	header.put("aktif","Y");
	header.put("status", 2);
	header.put("user_id", user_id);

	listcomp5 = (List<Map<String, Object>>) params.get("content");
	
	
	
	
	LOGGER.info("listcomp5={}", listcomp5);
	int jmlpemda = 0;
	String pmdawal = "";
	for (int s = 0; s<listcomp5.size(); s++){
		Map<String, Object> isi = new HashMap<String, Object>();
		String tahun = (String) listcomp5.get(s).get("tahun").toString();
		String kd_provinsi = (String) listcomp5.get(s).get("kd_provinsi").toString();
		String kd_pemda = (String) listcomp5.get(s).get("kd_pemda").toString();
		String kd_satker = (String) listcomp5.get(s).get("kd_satker").toString();
		String periode_id = (String) listcomp5.get(s).get("periode_id").toString();
		String perubahan_id ="0";
		if (listcomp5.get(s).get("perubahan_id") != null){
			perubahan_id = (String) listcomp5.get(s).get("perubahan_id").toString();
		}

		String kd_rek_1 = (String) listcomp5.get(s).get("kd_rek_1").toString();
		String kd_rek_2 = (String) listcomp5.get(s).get("kd_rek_2").toString();
		String kd_rek_3 = (String) listcomp5.get(s).get("kd_rek_3").toString();
		String kd_rek_4 = (String) listcomp5.get(s).get("kd_rek_4").toString();
		String kd_rek_5 = "0";
		String nm_rek_1 = (String) listcomp5.get(s).get("nm_rek_1").toString();
		String nm_rek_2 = (String) listcomp5.get(s).get("nm_rek_2").toString();
		String nm_rek_3 = (String) listcomp5.get(s).get("nm_rek_3").toString();
		String nm_rek_4 = (String) listcomp5.get(s).get("nm_rek_4").toString();
		String nm_rek_5 = "";
		
		String anggaran ="0";
		if (listcomp5.get(s).get("anggaran") != null){
			anggaran = (String) listcomp5.get(s).get("anggaran").toString();
		}
		
		String realisasi ="0";
		if (listcomp5.get(s).get("realisasi") != null){
			realisasi = (String) listcomp5.get(s).get("realisasi").toString();
		}
		
		String d_k = (String) listcomp5.get(s).get("d_k");
		
		isi.put("tahun", tahun.trim());
		isi.put("kd_provinsi", Integer.valueOf(kd_provinsi.trim()));
		isi.put("kd_pemda", kd_pemda.trim());
		isi.put("kd_satker", kd_satker.trim());
		isi.put("periode_id", Integer.valueOf(periode_id.trim()));
		isi.put("perubahan_id", Integer.valueOf(perubahan_id.trim()));
		isi.put("kd_rek_1", kd_rek_1.trim());
		isi.put("kd_rek_2", kd_rek_2.trim());
		isi.put("kd_rek_3", kd_rek_3.trim());
		isi.put("kd_rek_4", kd_rek_4.trim());
		isi.put("kd_rek_5", kd_rek_5.trim());
		isi.put("nm_rek_1", nm_rek_1.trim());
		isi.put("nm_rek_2", nm_rek_2.trim());
		isi.put("nm_rek_3", nm_rek_3.trim());
		isi.put("nm_rek_4", nm_rek_4.trim());
		isi.put("nm_rek_5", nm_rek_5.trim());
		isi.put("anggaran", new BigDecimal(anggaran.trim()));
		isi.put("realisasi", new BigDecimal(realisasi.trim()));
		isi.put("d_k", d_k.trim());
		
		kd_pemda = kd_pemda.trim();
		if (s==0){
			pmdawal= kd_pemda;
			jmlpemda = jmlpemda+1;
		}else{
			if (!(pmdawal.equals(kd_pemda))){
				pmdawal=kd_pemda;
				jmlpemda = jmlpemda+1;
			}
		}
		
		LOGGER.info("isi={}", isi);
		int i1 = dataKonsolDao.insertcomp5(isi, header_id, Integer.valueOf(user_id));
		
	}
	
	header.put("jmlPemda", jmlpemda);
	int i = dataKonsolDao.insertcompheader(header);
	
	LOGGER.info("result update user>>data from db={}", i);
	LOGGER.info("params={}", params);
	
	return i;
	}
	
	
	@Override
	public int insert3(Map<String, Object> params) {
		
	
	LOGGER.info("try update params={}", params);
	List<Map<String, Object>> listcomp5 = new ArrayList<Map<String,Object>>();
	Map<String, Object> header = new HashMap<String, Object>();
	
	String header_id = (String) params.get("id");
	int user_id = (int) params.get("user_id");
	
	header.put("id", header_id);
	header.put("tahun", params.get("tahun"));
	header.put("bulan", Integer.valueOf((String) params.get("period_id").toString()));
	header.put("laporan_id", params.get("laporan_id"));
	header.put("lvl_data", params.get("level"));
	header.put("tgl_kirim_baru", params.get("tgl_kirim"));
	header.put("aktif","Y");
	header.put("status", 2);
	header.put("user_id", user_id);

	listcomp5 = (List<Map<String, Object>>) params.get("content");
	
	
	
	
	LOGGER.info("listcomp5={}", listcomp5);
	int jmlpemda = 0;
	String pmdawal = "";
	for (int s = 0; s<listcomp5.size(); s++){
		Map<String, Object> isi = new HashMap<String, Object>();
		String tahun = (String) listcomp5.get(s).get("tahun").toString();
		String kd_provinsi = (String) listcomp5.get(s).get("kd_provinsi").toString();
		String kd_pemda = (String) listcomp5.get(s).get("kd_pemda").toString();
		String kd_satker = (String) listcomp5.get(s).get("kd_satker").toString();
		String periode_id = (String) listcomp5.get(s).get("periode_id").toString();
		String perubahan_id ="0";
		if (listcomp5.get(s).get("perubahan_id") != null){
			perubahan_id = (String) listcomp5.get(s).get("perubahan_id").toString();
		}

		String kd_rek_1 = (String) listcomp5.get(s).get("kd_rek_1").toString();
		String kd_rek_2 = (String) listcomp5.get(s).get("kd_rek_2").toString();
		String kd_rek_3 = (String) listcomp5.get(s).get("kd_rek_3").toString();
		String kd_rek_4 = (String) listcomp5.get(s).get("kd_rek_4").toString();
		String kd_rek_5 = "0";
		String nm_rek_1 = (String) listcomp5.get(s).get("nm_rek_1").toString();
		String nm_rek_2 = (String) listcomp5.get(s).get("nm_rek_2").toString();
		String nm_rek_3 = (String) listcomp5.get(s).get("nm_rek_3").toString();
		String nm_rek_4 = (String) listcomp5.get(s).get("nm_rek_4").toString();
		String nm_rek_5 = "";
		
		String anggaran ="0";
		if (listcomp5.get(s).get("anggaran") != null){
			anggaran = (String) listcomp5.get(s).get("anggaran").toString();
		}
		
		String realisasi ="0";
		if (listcomp5.get(s).get("realisasi") != null){
			realisasi = (String) listcomp5.get(s).get("realisasi").toString();
		}
		
		String d_k = (String) listcomp5.get(s).get("d_k");
		
		isi.put("tahun", tahun.trim());
		isi.put("kd_provinsi", Integer.valueOf(kd_provinsi.trim()));
		isi.put("kd_pemda", kd_pemda.trim());
		isi.put("kd_satker", kd_satker.trim());
		isi.put("periode_id", Integer.valueOf(periode_id.trim()));
		isi.put("perubahan_id", Integer.valueOf(perubahan_id.trim()));
		isi.put("kd_rek_1", kd_rek_1.trim());
		isi.put("kd_rek_2", kd_rek_2.trim());
		isi.put("kd_rek_3", kd_rek_3.trim());
		isi.put("kd_rek_4", kd_rek_4.trim());
		isi.put("kd_rek_5", kd_rek_5.trim());
		isi.put("nm_rek_1", nm_rek_1.trim());
		isi.put("nm_rek_2", nm_rek_2.trim());
		isi.put("nm_rek_3", nm_rek_3.trim());
		isi.put("nm_rek_4", nm_rek_4.trim());
		isi.put("nm_rek_5", nm_rek_5.trim());
		isi.put("anggaran", new BigDecimal(anggaran.trim()));
		isi.put("realisasi", new BigDecimal(realisasi.trim()));
		isi.put("d_k", d_k.trim());
		
		kd_pemda = kd_pemda.trim();
		if (s==0){
			pmdawal= kd_pemda;
			jmlpemda = jmlpemda+1;
		}else{
			if (!(pmdawal.equals(kd_pemda))){
				pmdawal=kd_pemda;
				jmlpemda = jmlpemda+1;
			}
		}
		
		LOGGER.info("isi={}", isi);
		int i1 = dataKonsolDao.insertcomp5(isi, header_id, Integer.valueOf(user_id));
		
	}
	
	header.put("jmlPemda", jmlpemda);
	int i = dataKonsolDao.insertcompheader(header);
	
	LOGGER.info("result update user>>data from db={}", i);
	LOGGER.info("params={}", params);
	
	return i;
	}
	
//
//	@Override
//	public int update(Map<String, Object> params) {
//		LOGGER.info("try update params={}", params);
//		int i = periodeDao.updateperiode(params);
//		LOGGER.info("result update periode>>data from db={}", i);
//		LOGGER.info("params={}", params);
//		
//		return i;
//	}
//
//	@Override
//	public int delete(Map<String, Object> params) {
//		LOGGER.info("try update params={}", params);
//		int i = periodeDao.deleteperiode(params);
//		LOGGER.info("result delete periode>>data from db={}", i);
//		LOGGER.info("params={}", params);
//		
//		return i;
//	}
	
	

}
