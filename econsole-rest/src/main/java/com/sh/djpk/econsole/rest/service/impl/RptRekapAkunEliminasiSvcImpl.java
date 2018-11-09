package com.sh.djpk.econsole.rest.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.dao.RptRekapAkunEliminasiDao;
import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RptRekapAkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.User;

@Service("rptRekapAkunEliminasiSvc")
@Transactional(readOnly = false)
public class RptRekapAkunEliminasiSvcImpl implements RptRekapAkunEliminasiSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(RptRekapAkunEliminasiSvcImpl.class);
	
	@Autowired RptRekapAkunEliminasiDao rekapDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getOnPemda(String kodePemda, String tahunAnggaran, int period) {
		LOGGER.info(
				"try get data per Pemda");
		List<HashMap> lst = rekapDao.getRekapEliminasi(kodePemda,tahunAnggaran,period);
		
		LOGGER.trace("data from db={}", lst);
//		BigDecimal tot = new BigDecimal(0);
		
//		for (HashMap hmp : lst){
//			BigDecimal isi1 = new BigDecimal(0);
////			System.out.println(hmp);
////			System.out.println(hmp.get("level3"));
//			HashMap isi = rekapDao.getSaldoAkun("2016", period, hmp.get("level3").toString(), hmp.get("kd_pemda").toString());
//			hmp.put("realisasi",isi.get("realisasi"));	
////			isi1 = new BigDecimal( (int) isi.get("realisasi"));
////			tot =tot + isi1;
//		}
		
		
		return lst;
	}
	
	
	

}
