package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.dao.RefAkrualDao;
import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RefAkrualSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.User;

@Service("refAkrualSvc")
@Transactional(readOnly = false)
public class RefAkrualSvcImpl implements RefAkrualSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(RefAkrualSvcImpl.class);
	
	@Autowired RefAkrualDao akrualDao;
	
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<HashMap> getAll() {
//		LOGGER.info(
//				"try get data Periode");
//		List<HashMap> l = periodeDao.getPeriode();
//		LOGGER.trace("data from db={}", l);
//		return l;
//	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllAkrual1() {
		LOGGER.info(
				"try get data akrual 1");
		List<HashMap> lst = akrualDao.getAkrual1();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllAkrual2() {
		LOGGER.info(
				"try get data akrual 2");
		List<HashMap> lst = akrualDao.getAkrual2();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllAkrual3() {
		LOGGER.info(
				"try get data akrual 3");
		List<HashMap> lst = akrualDao.getAkrual3();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllAkrual4() {
		LOGGER.info(
				"try get data akrual 4");
		List<HashMap> lst = akrualDao.getAkrual5();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllAkrual5() {
		LOGGER.info(
				"try get data akrual 5");
		List<HashMap> lst = akrualDao.getAkrual5();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	
	
	
	

}
