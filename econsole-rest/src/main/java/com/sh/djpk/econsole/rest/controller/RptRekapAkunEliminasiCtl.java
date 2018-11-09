package com.sh.djpk.econsole.rest.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sh.djpk.econsole.rest.service.PeriodeSvc;
import com.sh.djpk.econsole.rest.service.RptRekapAkunEliminasiSvc;
import com.sh.djpk.econsole.rest.service.UserSvc;
import com.sh.djpk.share.model.RestResponse;

@RestController
@RequestMapping("/rekapakun")
public class RptRekapAkunEliminasiCtl {

	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(RptRekapAkunEliminasiCtl.class);

	@Autowired RptRekapAkunEliminasiSvc rekapSvc;
	
	@RequestMapping(value = "/all/{kodePemda}/{tahunAnggaran}/{period}", method = RequestMethod.GET)
	public RestResponse getAll(
			@PathVariable(value = "kodePemda") String kodePemda,
			@PathVariable(value = "tahunAnggaran") String tahunAnggaran,
			@PathVariable(value = "period") int period
			) {
		LOGGER.info("GET ALL");
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"OK", null);
		List<HashMap>  lst = rekapSvc.getOnPemda(kodePemda,tahunAnggaran,period);
		response.setTotalRecords(lst.size());
		response.setContents(lst);
		return response;
	}
}
