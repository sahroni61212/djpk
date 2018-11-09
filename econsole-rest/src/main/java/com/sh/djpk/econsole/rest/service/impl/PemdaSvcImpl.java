package com.sh.djpk.econsole.rest.service.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.djpk.econsole.rest.dao.PemdaDao;
import com.sh.djpk.econsole.rest.service.PemdaSvc;

@Service("pemdaSvc")
@Transactional(readOnly = false)
public class PemdaSvcImpl implements PemdaSvc {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(PemdaSvcImpl.class);
	
	@Autowired PemdaDao pemdaDao;
	
	@Override
	public List<HashMap> getAllPemda() {
		LOGGER.info(
				"try get data ref pemda");
		List<HashMap> lst = pemdaDao.getAllPemda();
		LOGGER.trace("data from db={}", lst);
		return lst;
	}

	@Override
	public List<HashMap> getAllPemdaLkp() {
		LOGGER.info(
				"try get data ref pemda");
		List<HashMap> lstlkp = pemdaDao.getAllPemdaLkp();
		LOGGER.trace("data from db={}", lstlkp);
		return lstlkp;
	}

}
