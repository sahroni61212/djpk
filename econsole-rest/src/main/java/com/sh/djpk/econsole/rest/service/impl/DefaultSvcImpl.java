package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.controller.DefaultCtl;
import com.sh.djpk.econsole.rest.dao.DefaultDao;
import com.sh.djpk.econsole.rest.service.DefaultSvc;

@Service("defaultSvc")
@Transactional(readOnly = false)
public class DefaultSvcImpl implements DefaultSvc{

	@Autowired DefaultDao defaultDao;
	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DefaultSvcImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getCurrentTImestamp() {
		List<HashMap> list =  defaultDao.getCurrentTImestamp();
		LOGGER.info("result from database : {}", list);
		return list;
	}

	@Override
	public void updateSearchPath() {
		LOGGER.info("update search_path");
		defaultDao.updateSearchPath();
	}
	
	
}
