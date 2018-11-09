package com.sh.djpk.econsole.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.pengaturan.UsmanCtl;
import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.DateUtils;
import com.sh.djpk.share.util.JsonUtil;

@ManagedBean
@SessionScoped
public class periodeCtl  implements Serializable{
	
	/**
	 * 
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(periodeCtl.class);
	private static final long serialVersionUID = 1L;

	public List<Map<String, Object>> periodlist;
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> data1 = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	private String valueButtonSimpan;
	
	
	public periodeCtl(){
		load();
		
	}
	
	public void onload(){
		periodlist = getDataPeriode();
		
//		if (event.equals("preRenderView")){
//		periodlist = getDataPeriode();
//		}
		
	}
	
	@PostConstruct
    public void load(){
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		Map<String, Object> userSession = (Map<String, Object>) session.getAttribute("user_login");
		periodlist = getDataPeriode();
		
	}
	
	
	
	public List<Map<String, Object>> getDataPeriode() {
		
    	if (getSessionTahunAnggaran()!=null){
    	System.out.println("=================================tahun anggaran : "+ getSessionTahunAnggaran());
		}
    	
    	LOGGER.info("<<get data periode all>>");
    	
    	List<Map<String, Object>> lst = ClientsUtil.callWsListResponse(
				"/periode/all", null, HttpMethod.GET,
				null);
		System.out.println("========================================="+lst);
		
//		period = new ArrayList<periodeDto>();
//		for (Map<String, Object> mpl : periodlist){
//			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
//		}
		 FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(periodlist);

		return lst;
	}
	
//	@PostConstruct
//    public void init() {
//		if (getSessionTahunAnggaran()!=null){
//    	System.out.println("=================================tahun anggaran : "+ getSessionTahunAnggaran());
//		}
//		periodlist = new ArrayList<Map<String,Object>>();
//    	LOGGER.info("<<get data periode all>>");
//    	List<Map<String, Object>> lst  = ClientsUtil.callWsListResponse(
//				"/periode/all", null, HttpMethod.GET,
//				null);
//    	 periodlist = lst;
//		System.out.println("========================================="+lst);
////		period = new ArrayList<periodeDto>();
////		for (Map<String, Object> mpl : periodlist){
////			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
////		}
//	}
	
	public Integer getSessionTahunAnggaran(){
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		HeaderTemplate hdrT =  (HeaderTemplate) session.getAttribute("headerTemplate");
		Integer i =  hdrT.getTahunAnggaran();
		return i;
	}
	
	public void reload() throws IOException {
		load();
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	
	public String add() throws IOException {
		LOGGER.info("<<ADD>>");
		this.data = new HashMap<String, Object>();
		valueButtonSimpan = "Simpan";
		params.put("action", "ADD");
		return doToDetail();
	}
	
	public String edit() throws IOException{
		LOGGER.info("<<EDIT>>");
//		data1 = data;
		data1.put("id1", data.get("id"));
		data1.put("name1", data.get("name"));
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
	
	public String delete() throws Exception{
		LOGGER.info("<<DELETE>>");
//		data = new HashMap<String, Object>();
		LOGGER.info("data={}",data);
		valueButtonSimpan = "Hapus";
		params.put("action", "DELETE");
		dodelete();
		reload();
		return "_index";
	}
	
	public void dodelete() throws Exception{
		LOGGER.info("<<Delete>>");
		LOGGER.info(data+"");
		RestResponse response = ClientsUtil.callWs("/periode/del", data, HttpMethod.POST);
		LOGGER.info("response={}",JsonUtil.getJson(response));
		if(response.getStatus() == RestResponse.OK_REST_STATUS){
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);	        
	        refreshListData();
		}else{
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
		}
		
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
	
	public String create() throws Exception {
		LOGGER.info("<<CREATE>>");
		data.put("proc","proses Insert");
		LOGGER.info(data + "");
		int i = Integer.valueOf((String)data.get("id"));
		String s = (String) data.get("name");
		data.clear();
		data.put("id", i);
		data.put("name", s);
		System.out.println("================================== isi data : "+data);
		System.out.println("================================== isi data1 : "+data1);
		
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Tidak ada proses!");
		if ("ADD".equals(params.get("action"))) {
			LOGGER.info("<<Action From Add>>");
			System.out.println("================================== isi data : "+data);
			response = ClientsUtil
					.callWs("/periode/insert", data, HttpMethod.POST);
		} else if ("EDIT".equals(params.get("action"))) {
			LOGGER.info("<<Action From Edit>>");
			data.put("id1", data1.get("id1"));
			data.put("name1", data1.get("name1"));
			
			 response = ClientsUtil.callWs("/periode/update", data,
			 HttpMethod.POST);
		} else {
			LOGGER.info("<<Action Not identification>>");
		}

		if (response.getStatus() == RestResponse.OK_REST_STATUS) {
			FacesMessage messages = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "INFO", response.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, messages);

			// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
			refreshListData();
			data = new HashMap<String, Object>();
			return "_index";
		} else {
			FacesMessage messages = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR", response.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, messages);
		}
		return null;
	}
	
	public String batal() throws Exception {
		LOGGER.info("<<BATAL>>");
		
		return "_index";
	}
	
	public void refreshListData() {
		this.periodlist = getDataPeriode();
	}
	
	public HashMap<String, Object> getData() {
		return data;
	}

	public void setData(HashMap<String, Object> data) {
		this.data = data;
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


	public List<Map<String, Object>> getPeriodlist() {
		return periodlist;
	}


	public void setPeriodlist(List<Map<String, Object>> periodlist) {
		this.periodlist = periodlist;
	}

	public HashMap<String, Object> getData1() {
		return data1;
	}

	public void setData1(HashMap<String, Object> data1) {
		this.data1 = data1;
	}

	
	
}
