package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.GlobalParamDao;
import com.sh.djpk.econsole.rest.service.GlobalParamSvc;

@Service("globalParamSvc")
@Transactional(readOnly = false)
public class GlobalParamSvcImpl implements GlobalParamSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalParamSvcImpl.class);

	@Autowired
	GlobalParamDao globalParamDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAllData(String globalParamParent,
			String globalParamValue) {
		LOGGER.info(
				"try get global param globalParamParent={}, globalParamValue={}",
				globalParamParent, globalParamValue);
		List<HashMap> l =globalParamDao.getAllData(globalParamParent, globalParamValue);
		LOGGER.trace("data from db={}", l);
		return l;
	}

}
