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
import com.sh.djpk.econsole.rest.dao.AkunResiprokalDao;
import com.sh.djpk.econsole.rest.service.AkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.AkunResiprokalSvc;


@Service("akunResiprokalSvc")
@Transactional(readOnly = false)
public class AkunResiprokalSvcImpl implements AkunResiprokalSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(AkunResiprokalSvcImpl.class);
	
	@Autowired AkunResiprokalDao akunDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getOnTahun(String tahunAnggaran) {
		LOGGER.info(
				"try get resiprokal per tahun anggaaran");
		List<HashMap> lst = akunDao.getResiprokal(tahunAnggaran);
		
		List<HashMap> lst2 = new ArrayList<HashMap>();
		
		for (int i=0; i<lst.size();i++){
			Map<String, Object> dt1 = new HashMap<String, Object>();
			
			dt1 = lst.get(i);
			String akn =(String) dt1.get("akun");
			int lvl = Integer.valueOf(dt1.get("level_data").toString());
			String nmakn = "NN";
			if (lvl == 3){
				nmakn = (String) akunDao.getNama3(akn).get("nm_akrual_3");
			}else if (lvl == 4){
				nmakn = (String) akunDao.getNama4(akn).get("mm_akrual_4");
			}else if (lvl == 5){
				nmakn = (String) akunDao.getNama5(akn).get("nm_akrual_5");
			}
			
			dt1.put("nama_akun", nmakn);
			
			lst2.add((HashMap) dt1);
			
		}
		
		LOGGER.trace("data from db={}", lst);
		
		return lst2;
	}

	@Override
	public List<HashMap> getAkr1() {
		List<HashMap> laskar1 = akunDao.getAkr1();
		return laskar1;
	}
	
	@Override
	public List<HashMap> getAkr1a(String lap) {
		int lapId = Integer.valueOf(lap);
		List<HashMap> laskar1 = new ArrayList<HashMap>();
		if (lapId == 1){
			laskar1 = akunDao.getAkr1a();	
		}else if (lapId == 2){
			laskar1 = akunDao.getAkr1b();	
		}else if (lapId == 3){
			laskar1 = akunDao.getAkr1c();	
		}
		
		return laskar1;
	}

	@Override
	public List<HashMap> getAkr2(String akr1) {
		List<HashMap> laskar1 = akunDao.getAkr2(akr1);
		return laskar1;
	}

	@Override
	public List<HashMap> getAkr3(String akr1, String akr2) {
		List<HashMap> laskar1 = akunDao.getAkr3(akr1,akr2);
		return laskar1;
	}

	@Override
	public List<HashMap> getAkr4(String akr1, String akr2, String akr3) {
		List<HashMap> laskar1 = akunDao.getAkr4(akr1,akr2,akr3);
		return laskar1;
	}

	@Override
	public List<HashMap> getAkr5(String akr1, String akr2, String akr3,
			String akr4) {
		List<HashMap> laskar1 = akunDao.getAkr5(akr1,akr2,akr3,akr4);
		return laskar1;
	}

	@Override
	public int getProses(Map<String, Object> params) {
		int inres =0;
		inres = akunDao.insertresiprokal(params);
		
		return inres;
	}

	
	
	

}
