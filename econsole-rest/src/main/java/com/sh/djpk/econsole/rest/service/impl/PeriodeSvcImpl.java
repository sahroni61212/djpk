package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.User;

@Service("periodeSvc")
@Transactional(readOnly = false)
public class PeriodeSvcImpl implements PeriodeSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(PeriodeSvcImpl.class);
	
	@Autowired PeriodeDao periodeDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAll() {
		LOGGER.info(
				"try get data Periode");
		List<HashMap> l = periodeDao.getPeriode();
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	public int insert(Map<String, Object> params) {
		
	
	LOGGER.info("try update params={}", params);
	int i = periodeDao.insertperiode(params);
	LOGGER.info("result update user>>data from db={}", i);
	LOGGER.info("params={}", params);
	
	return i;
	}

	@Override
	public int update(Map<String, Object> params) {
		LOGGER.info("try update params={}", params);
		int i = periodeDao.updateperiode(params);
		LOGGER.info("result update periode>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

	@Override
	public int delete(Map<String, Object> params) {
		LOGGER.info("try update params={}", params);
		int i = periodeDao.deleteperiode(params);
		LOGGER.info("result delete periode>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}
	
	

}
