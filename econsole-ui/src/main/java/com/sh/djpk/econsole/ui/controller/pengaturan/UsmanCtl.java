package com.sh.djpk.econsole.ui.controller.pengaturan;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.pelaporan.Pelaporan;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.User;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.DateUtils;
import com.sh.djpk.share.util.HashUtils;
import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.NumberUtils;
import com.sh.djpk.share.util.StringUtils;

@ManagedBean
@SessionScoped
public class UsmanCtl implements Serializable {
	public static final Logger LOGGER = LoggerFactory.getLogger(UsmanCtl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Map<String, Object>> listUser = new ArrayList<Map<String, Object>>();

	private String searchIndexUsername;

	List<Map<String, Object>> selectOnePemda = new ArrayList<Map<String, Object>>();
	String selectedPemda;

	List<Map<String, Object>> selectOneKdUser = new ArrayList<Map<String, Object>>();
	String selectedKdUser;

	List<Map<String, Object>> selectOneAuthItem = new ArrayList<Map<String, Object>>();
	String selectedAuthItem;

	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();

	private String valueButtonSimpan;

	public UsmanCtl() {
		this.listUser = getDataListUser(null);
		params = new HashMap<String, Object>();
	}

	public List<Map<String, Object>> getDataListUser(String username) {
		Map<String, Object> othersParam = new HashMap<String, Object>();
		othersParam.put("username", StringUtils.surroundString(username, "%%"));
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/user/get_all_paging/0/1000", othersParam, HttpMethod.POST,
				"order_by=", "direction=");
		return l;
	}

	public List<Map<String, Object>> getDataSelectPemda() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/lra_report/get_ref_pemda2", params, HttpMethod.POST,
				"kode_laporan=4", "tahun_anggaran="+DateUtils.getYears(new Date()));
		// if (l != null && !l.isEmpty()) {
		// selectedOthers = (String) l.get(0).get("value_cb");
		// }
		return l;
	}

	public List<Map<String, Object>> getDataSelectKdUser() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/usrgrp/all", null, HttpMethod.GET);
		// if (l != null && !l.isEmpty()) {
		// selectedOthers = (String) l.get(0).get("value_cb");
		// }
		return l;
	}

	public List<Map<String, Object>> getDataAuthItem() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/user/get_all_auth_item", null, HttpMethod.GET);
		// if (l != null && !l.isEmpty()) {
		// selectedOthers = (String) l.get(0).get("value_cb");
		// }
		return l;
	}
	
	public String view() throws IOException{
		LOGGER.info("<<VIEW>>");
		LOGGER.info("data={}",data);
		valueButtonSimpan = "Update";
		params.put("action", "VIEW");
		return doToDetail("_view");
	}

	public String add() throws IOException {
		LOGGER.info("<<ADD>>");
		data = new HashMap<String, Object>();
		valueButtonSimpan = "Simpan";
		params.put("action", "ADD");
		return doToDetail("_detail");
	}
	
	public String edit() throws IOException{
		LOGGER.info("<<EDIT>>");
//		data = new HashMap<String, Object>();
		LOGGER.info("data={}",data);
		valueButtonSimpan = "Update";
		params.put("action", "EDIT");
		return doToDetail("_detail");
	}
	
	
	public String deleteOnView() throws Exception{
		LOGGER.info("<<deleteOnView>>");
		doDelete();
		return "_index";
	}
	
	public void delete() throws Exception{
		LOGGER.info("<<delete>>");
	}
	
	
	public void doDelete() throws Exception{
		LOGGER.info("<<doDelete>>");
		LOGGER.info(data+"");
		RestResponse response = ClientsUtil.callWs("/user/delete", data, HttpMethod.POST);
		LOGGER.info("response={}",JsonUtil.getJson(response));
		if(response.getStatus() == RestResponse.OK_REST_STATUS){
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);	        
	        refreshListData();
//	        FacesContext.getCurrentInstance().getExternalContext().dispatch("/foo.xhtml");
//	        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "_index.xhtml");
	        data = new HashMap<>();
		}else{
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
		}
	}
	
	public void confDelete(){
			LOGGER.info("<<confirmDelete>>");
			LOGGER.info("data={}",data);
			Map<String, Object> options = new HashMap<>();
			options.put("resizable", false);options.put("draggable", false);
			 
//			options.put("width", "1000px");
			PrimeFaces.current().dialog().openDynamic("conf_delete", options, null);
		}
		

	public String doToDetail(String uri) throws IOException {
		LOGGER.info("<<doToDetail>>");
		selectOnePemda = getDataSelectPemda();
		selectOneKdUser = getDataSelectKdUser();
		selectOneAuthItem = getDataAuthItem();
		// Map<String,Object> options = new HashMap<>();
		// options.put("resizable", false);
		// PrimeFaces.current().dialog().openDynamic("_detail", options, null);

		// FacesContext.getCurrentInstance().getExternalContext().redirect("_detail.xhtml");
		return uri;
	}

	public void eventChangeUsername(ValueChangeEvent event) {
		LOGGER.info("<<eventChangeUsername>>");
		String val = event.getNewValue().toString();
		LOGGER.info("<<val={}>>", val);
		this.listUser = getDataListUser(val);
	}

	public String indexButtonAdd() {
		System.out.println("masuk sini");
		return "_detail";
	}

	public String create() throws Exception {
		LOGGER.info("<<CREATE>>");
		data.put("auth_key", "auth_key");
		data.put("created_at", Integer.valueOf(DateUtils.dateToString(new Date(), "yyyyMMdd")));
		data.put("updated_at", Integer.valueOf(DateUtils.dateToString(new Date(), "yyyyMMdd")));
		String password = (String) data.get("password_hash");
		if(password == null || password.isEmpty()){
			data.remove("password_hash");
		}else{
			String passEnc = HashUtils.md5Java(password);
			data.put("password_hash", passEnc);
		}
		return doCreate();
	}

	public String doCreate() throws Exception {
		LOGGER.info("<<DO CREATE>>");
		LOGGER.info(data + "");		
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Tidak ada proses!");
		if ("ADD".equals(params.get("action"))) {
			LOGGER.info("<<Action From Add>>");
			
			response = ClientsUtil
					.callWs("/user/insert", data, HttpMethod.POST);
		} else if ("EDIT".equals(params.get("action"))) {
			LOGGER.info("<<Action From Edit>>");			
			 response = ClientsUtil.callWs("/user/update", data,
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
	
	public String cancel(){
		LOGGER.info("<<BATAL>>");
		return "_index";
	}

	public void refreshListData() {
		this.listUser = getDataListUser(null);
	}

	public List<Map<String, Object>> getListUser() {
		return listUser;
	}

	public void setListUser(List<Map<String, Object>> listUser) {
		this.listUser = listUser;
	}

	public String getSearchIndexUsername() {
		return searchIndexUsername;
	}

	public void setSearchIndexUsername(String searchIndexUsername) {
		this.searchIndexUsername = searchIndexUsername;
	}

	public List<Map<String, Object>> getSelectOnePemda() {
		return selectOnePemda;
	}

	public void setSelectOnePemda(List<Map<String, Object>> selectOnePemda) {
		this.selectOnePemda = selectOnePemda;
	}

	public String getSelectedPemda() {
		return selectedPemda;
	}

	public void setSelectedPemda(String selectedPemda) {
		this.selectedPemda = selectedPemda;
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

	public List<Map<String, Object>> getSelectOneKdUser() {
		return selectOneKdUser;
	}

	public void setSelectOneKdUser(List<Map<String, Object>> selectOneKdUser) {
		this.selectOneKdUser = selectOneKdUser;
	}

	public String getSelectedKdUser() {
		return selectedKdUser;
	}

	public void setSelectedKdUser(String selectedKdUser) {
		this.selectedKdUser = selectedKdUser;
	}

	public List<Map<String, Object>> getSelectOneAuthItem() {
		return selectOneAuthItem;
	}

	public void setSelectOneAuthItem(List<Map<String, Object>> selectOneAuthItem) {
		this.selectOneAuthItem = selectOneAuthItem;
	}

	public String getSelectedAuthItem() {
		return selectedAuthItem;
	}

	public void setSelectedAuthItem(String selectedAuthItem) {
		this.selectedAuthItem = selectedAuthItem;
	}

}
