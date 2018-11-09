package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.TransferDao;
import com.sh.djpk.econsole.rest.service.TransferSvc;

@Service("transferSvc")
@Transactional(readOnly = false)
public class TrasnferSvcImpl implements TransferSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(TrasnferSvcImpl.class);
	
	@Autowired TransferDao transferDao;
	
	@Override
	public List<HashMap> getAllPemda() {
		LOGGER.info(
				"try get data ref pemda");
		List<HashMap> lst = transferDao.getAllTransfer();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	public int insert(Map<String, Object> params) {
		LOGGER.info("try insert params={}", params);
		int i = transferDao.inserttrf(params);
		LOGGER.info("result inser jenis transfer>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

	@Override
	public int update(Map<String, Object> params) {
		LOGGER.info("try upadate params={}", params);
		int i = transferDao.updatetrf(params);
		LOGGER.info("result update jenis transfer>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

	@Override
	public int delete(Map<String, Object> params) {
		LOGGER.info("try delete params={}", params);
		int i = transferDao.deletetrf(params);
		LOGGER.info("result delete jenis transfer>>data from db={}", i);
		LOGGER.info("params={}", params);
		
		return i;
	}

}
