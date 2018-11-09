package com.sh.djpk.econsole.ui.controller.pengaturan;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.NumberUtils;

@ManagedBean
@SessionScoped
public class UserGroupCtl  implements Serializable{
	public static final Logger LOGGER = LoggerFactory.getLogger(UserGroupCtl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Map<String, Object>> listGroup;
	
	List<Map<String, Object>> listMenu;
	
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	HashMap<String, Object> menuAkses = new HashMap<String, Object>();
	
	private String valueButtonSimpan;
	
	@PostConstruct
    public void init() {
		params = new HashMap<String, Object>();
		refreshListData();
		
//		System.out.println("========================================="+lst);
		
	
		
		
	}
	
	public void refreshListData(){
		listGroup = ClientsUtil.callWsListResponse(
				"/usrgrp/all", null, HttpMethod.GET);
	}
	
	public void refreshListMenu(){
		listMenu  = ClientsUtil.callWsListResponse(
				"/usrgrp/get_all_menu/"+data.get("id"), null, HttpMethod.GET);
		LOGGER.info("lisMenu={}", JsonUtil.getJson(listMenu));
	}
	
	public String add() throws IOException{
		LOGGER.info("<<ADD>>");
		data = new HashMap<String, Object>();
		valueButtonSimpan = "Create";
		params.put("action", "ADD");
		return doToDetail();
	}
	
	public String edit() throws IOException{
		LOGGER.info("<<EDIT>>");
//		data = new HashMap<String, Object>();
		LOGGER.info("data={}",data);
		valueButtonSimpan = "Update";
		params.put("action", "EDIT");
		return doToDetail();
	}
	
	public void hakAkses() throws IOException{
		LOGGER.info("<<hakAkses>>");
		LOGGER.info("data={}",data);
		refreshListMenu();
		Map<String, Object> options = new HashMap<>();
		options.put("resizable", false);options.put("draggable", false);
		 
//		options.put("width", "1000px");
		PrimeFaces.current().dialog().openDynamic("hak_akses", options, null);
	}
	
	public void delete() throws Exception{
		LOGGER.info("<<Delete>>");
		LOGGER.info(data+"");
		RestResponse response = ClientsUtil.callWs("/usrgrp/delete", data, HttpMethod.POST);
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
	
	public String doToDetail() throws IOException{
		LOGGER.info("<<doToDetail>>");
//		Map<String,Object> options = new HashMap<>();
//        options.put("resizable", false);
//        PrimeFaces.current().dialog().openDynamic("_detail", options, null);
		
		
//		FacesContext.getCurrentInstance().getExternalContext().redirect("_detail.xhtml");
		return "_detail";
	}
	
	public String create() throws Exception{
		LOGGER.info("<<CREATE>>");
		return doCreate();
	}
	
	public String doCreate() throws Exception{
		LOGGER.info("<<DO CREATE>>");
		LOGGER.info(data+"");
		RestResponse response = new RestResponse(RestResponse.ERROR_REST_STATUS, "Tidak ada proses!");
		if("ADD".equals(params.get("action"))){
			LOGGER.info("<<Action From Add>>");
			response = ClientsUtil.callWs("/usrgrp/insert", data, HttpMethod.POST);		
		}else if("EDIT".equals(params.get("action"))){
			LOGGER.info("<<Action From Edit>>");
			response = ClientsUtil.callWs("/usrgrp/update", data, HttpMethod.POST);		
		}else{
			LOGGER.info("<<Action Not identification>>");
		}
			
		
		if(response.getStatus() == RestResponse.OK_REST_STATUS){
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
	        
//			FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
	        refreshListData();
	        data = new HashMap<String, Object>();
	        return "_index";
		}else{
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
		}
		return null;
		
	}
	
	public void doOpenAdd(){
		LOGGER.info("doOpenAdd");
		doActionSubmit("is_add", "Y");
	}
	
	public void doCloseAdd(){
		LOGGER.info("doCloseAdd");
		doActionSubmit("is_add", "N");
	}
	
	public void doOpenEdit(){
		LOGGER.info("doOpenEdit");
		doActionSubmit("is_edit", "Y");
	}
	
	public void doCloseEdit(){
		LOGGER.info("doCloseAEdit");
		doActionSubmit("is_edit", "N");
	}
	
	public void doOpenDelete(){
		LOGGER.info("doOpenDelete");
		doActionSubmit("is_delete", "Y");
	}
	
	public void doCloseDelete(){
		LOGGER.info("doCloseDelete");
		doActionSubmit("is_delete", "N");
	}
	
	public void doActionSubmit(String field, String value){
		LOGGER.info("doActionSubmit");
		LOGGER.info("menu akses before = {}", menuAkses);
//		clearAction();
		menuAkses.put(field, value);
		LOGGER.info("menu akses after = {}", menuAkses);
		doSubmit(1);
	}
	
	
	public void doOpen(){
		LOGGER.info("OPEN");
		LOGGER.info("menu akses = {}", menuAkses);
		clearAction();
		doSubmit(1);
	}
	
	private void clearAction(){
		menuAkses.remove("is_add");
		menuAkses.remove("is_edit");
		menuAkses.remove("is_delete");
	}
	
	public void doClose(){
		LOGGER.info("CLOSE");
		LOGGER.info("menu akses = {}", menuAkses);
		doSubmit(0);
	}
	
	public void doSubmit(int action){
		try {
//			menuAkses.put("is_add", "true".equals(String.valueOf(menuAkses.get("is_add"))) ? "Y" : "N");
//			menuAkses.put("is_edit", "true".equals(String.valueOf(menuAkses.get("is_edit"))) ? "Y" : "N");
//			menuAkses.put("is_delete", "true".equals(String.valueOf(menuAkses.get("is_delete"))) ? "Y" : "N");
			menuAkses.put("kd_user", NumberUtils.toInteger(data.get("id")));
			LOGGER.info("menu akses before process = {}", menuAkses);
			RestResponse response = ClientsUtil.callWs("/usrgrp/submit_ref_menu/"+action, menuAkses, HttpMethod.POST);
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		refreshListMenu();
	}

	public List<Map<String, Object>> getListGroup() {
		return listGroup;
	}

	public void setListWilayah(List<Map<String, Object>> listGroup) {
		this.listGroup = listGroup;
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

	public List<Map<String, Object>> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<Map<String, Object>> listMenu) {
		this.listMenu = listMenu;
	}

	public HashMap<String, Object> getMenuAkses() {
		return menuAkses;
	}

	public void setMenuAkses(HashMap<String, Object> menuAkses) {
		this.menuAkses = menuAkses;
	}

	
	
	
}
