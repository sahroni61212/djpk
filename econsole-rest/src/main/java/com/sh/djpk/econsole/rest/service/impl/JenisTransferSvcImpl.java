package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.JenisTransferDao;
import com.sh.djpk.econsole.rest.dao.PeriodeDao;
import com.sh.djpk.econsole.rest.dao.UserDao;
import com.sh.djpk.econsole.rest.service.JenisTransferSvc;
import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.User;

@Service("jenisTransferSvc")
@Transactional(readOnly = false)
public class JenisTransferSvcImpl implements JenisTransferSvc{

	public static final Logger LOGGER = LoggerFactory
			.getLogger(JenisTransferSvcImpl.class);
	
	@Autowired JenisTransferDao transferDao;

	@Override
	@Transactional(readOnly = true)
	public List<HashMap> getAll() {
		LOGGER.info(
				"try get data jenis Transfer");
		List<HashMap> l = transferDao.getJenis();
		LOGGER.trace("data from db={}", l);
		return l;
	}
	
	
	

}
