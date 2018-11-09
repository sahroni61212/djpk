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
import com.sh.djpk.econsole.rest.dao.WilayahDao;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.econsole.rest.service.WilayahSvc;
import com.sh.djpk.share.model.User;

@Service("wilayahSvc")
@Transactional(readOnly = false)
public class WilayahSvcImpl implements WilayahSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(WilayahSvcImpl.class);
	
	@Autowired WilayahDao wilDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAll() {
		LOGGER.info(
				"try get data Wilayah");
		List<HashMap> l = wilDao.getWilayah();
		LOGGER.trace("data from db={}", l);
		return l;
	}

	@Override
	public int insert(Map<String, Object> params) {
		LOGGER.info("try insert params={}", params);
		int i = wilDao.insertwil(params);
		LOGGER.info("result insert wilayah>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

	@Override
	public int update(Map<String, Object> params) {
		LOGGER.info("try update params={}", params);
		int i = wilDao.updatewil(params);
		LOGGER.info("result update wilayah>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

	@Override
	public int delete(Map<String, Object> params) {
		LOGGER.info("try delete params={}", params);
		int i = wilDao.deletewil(params);
		LOGGER.info("result delete wilayah>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}
	
	
	

}
