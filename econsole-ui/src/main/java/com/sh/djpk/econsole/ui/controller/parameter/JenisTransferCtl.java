package com.sh.djpk.econsole.ui.controller.parameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;

@ManagedBean
@ViewScoped
public class JenisTransferCtl  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> listJenisTrf;
	
	@PostConstruct
    public void init() {
		
		listJenisTrf = ClientsUtil.callWsListResponse(
				"/jenistrf/all", null, HttpMethod.GET,
				null);
//		System.out.println("========================================="+lst);
		
		
		
		
	}

	public List<Map<String, Object>> getListJenisTrf() {
		return listJenisTrf;
	}

	public void setListJenisTrf(List<Map<String, Object>> listJenisTrf) {
		this.listJenisTrf = listJenisTrf;
	}
	
	

	
	
}
