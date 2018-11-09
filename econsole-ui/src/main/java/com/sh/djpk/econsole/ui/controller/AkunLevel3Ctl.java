package com.sh.djpk.econsole.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;

@ManagedBean
public class AkunLevel3Ctl {
	
	public List<Map<String, Object>> mapAkun;
	
	@PostConstruct
    public void init() {
		
//		List<Map<String, Object>> 
		mapAkun = ClientsUtil.callWsListResponse(
				"/akrual/akr3", null, HttpMethod.GET,
				null);
//		System.out.println("========================================="+mapAkun);
		
//		for (Map<String, Object> mpl : lst){
//			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
//		}
		
		
	}

	public List<Map<String, Object>> getMapAkun() {
		return mapAkun;
	}

	public void setMapAkun(List<Map<String, Object>> mapAkun) {
		this.mapAkun = mapAkun;
	}

	
	
}
