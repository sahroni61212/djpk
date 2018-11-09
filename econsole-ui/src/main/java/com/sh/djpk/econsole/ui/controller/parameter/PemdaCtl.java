package com.sh.djpk.econsole.ui.controller.parameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;

@ManagedBean
@SessionScoped
public class PemdaCtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Logger LOGGER = LoggerFactory.getLogger(PemdaCtl.class);
	
	List<Map<String, Object>> listPemda;
	
	@PostConstruct
    public void init() {
		
		listPemda = ClientsUtil.callWsListResponse(
				"/ref_pemda/listPemda", null, HttpMethod.GET,
				null);
		
		
	}
	
	public List<Map<String, Object>> getListPemda() {
		return listPemda;
	}

	public void setListPemda(List<Map<String, Object>> listPemda) {
		this.listPemda = listPemda;
	}
}
