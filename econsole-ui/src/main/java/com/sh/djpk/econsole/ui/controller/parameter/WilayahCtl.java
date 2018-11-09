package com.sh.djpk.econsole.ui.controller.parameter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.periodeCtl;
import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;

@ManagedBean
@SessionScoped
public class WilayahCtl  implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> listWilayah;
	public static final Logger LOGGER = LoggerFactory.getLogger(periodeCtl.class);
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> data1 = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	private String valueButtonSimpan;
	
	
	@PostConstruct
    public void init() {
		
		listWilayah = ClientsUtil.callWsListResponse(
				"/wilayah/all", null, HttpMethod.GET,
				null);
//		System.out.println("========================================="+lst);
		
		
		
		
	}
	
	
	public String add() throws IOException {
		LOGGER.info("<<ADD>>");
		data = new HashMap<String, Object>();
		valueButtonSimpan = "Simpan";
		params.put("action", "ADD");
		return doToDetail();
	}
	
	public String edit() throws IOException{
		LOGGER.info("<<EDIT>>");
		data1 = data;
		LOGGER.info("data={}",data);
		LOGGER.info("data1={}",data1);
		
		System.out.println("++++++++++++++++++++++++++++++"+ data);
		valueButtonSimpan = "Update";
		params.put("action", "EDIT");
		return doToDetail();
	}
	
	public String view() throws IOException{
		LOGGER.info("<<view>>");
//		data = new HashMap<String, Object>();
		LOGGER.info("data={}",data);
		System.out.println("++++++++++++++++++++++++++++++"+ data);
		valueButtonSimpan = "view";
		params.put("action", "VIEW");
		return "_detailview";
	}
	
	public String delete() throws IOException{
		LOGGER.info("<<DELETE>>");
//		data = new HashMap<String, Object>();
		LOGGER.info("data={}",data);
		valueButtonSimpan = "Hapus";
		params.put("action", "DELETE");
		return doToDetail();
	}
	
	public String doToDetail() throws IOException {
		LOGGER.info("<<doToDetail>>");
//		selectOnePemda = getDataSelectPemda();
//		selectOneKdUser = getDataSelectKdUser();
//		selectOneAuthItem = getDataAuthItem();
		// Map<String,Object> options = new HashMap<>();
		// options.put("resizable", false);
		// PrimeFaces.current().dialog().openDynamic("_detail", options, null);

		// FacesContext.getCurrentInstance().getExternalContext().redirect("_detail.xhtml");
		return "_detail";
	}
	
	public String batal() throws Exception {
		LOGGER.info("<<BATAL>>");
		
		return "_index";
	}
	

	public List<Map<String, Object>> getListWilayah() {
		return listWilayah;
	}

	public void setListWilayah(List<Map<String, Object>> listWilayah) {
		this.listWilayah = listWilayah;
	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
	}

	public HashMap<String, Object> getData1() {
		return data1;
	}

	public void setData1(HashMap<String, Object> data1) {
		this.data1 = data1;
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

	public String getValueButtonSimpan() {
		return valueButtonSimpan;
	}

	public void setValueButtonSimpan(String valueButtonSimpan) {
		this.valueButtonSimpan = valueButtonSimpan;
	}
	
	

	
	
}
