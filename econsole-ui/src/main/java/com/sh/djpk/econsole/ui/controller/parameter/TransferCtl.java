package com.sh.djpk.econsole.ui.controller.parameter;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.JsonUtil;

@ManagedBean
@SessionScoped
public class TransferCtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public static final Logger LOGGER = LoggerFactory.getLogger(TransferCtl.class);
	
	List<Map<String, Object>> listTransfer;
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> data1 = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	private String valueButtonSimpan;
	
	@PostConstruct
    public void init() {
		
		listTransfer = ClientsUtil.callWsListResponse(
				"/ref_transfer/listTransfer", null, HttpMethod.GET,
				null);
		
		
	}
	
	public List<Map<String, Object>> getDataList() {
		
		List<Map<String, Object>> lst= ClientsUtil.callWsListResponse(
				"/ref_transfer/listTransfer", null, HttpMethod.GET,
				null);
		
		return lst;
		
		
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
		data1.put("id1", data.get("id"));
		data1.put("jenis_transfer1", data.get("jenis_transfer"));
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
		return "_index";
	}
	
	public void dodelete() throws Exception{
		LOGGER.info("<<Delete>>");
		LOGGER.info(data+"");
		RestResponse response = ClientsUtil.callWs("/ref_transfer/del", data, HttpMethod.POST);
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
		String s = (String) data.get("jenis_transfer");
		data.clear();
		data.put("id", i);
		data.put("jenis_transfer", s);
		System.out.println("================================== isi data : "+data);
		System.out.println("================================== isi data1 : "+data1);
		
		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Tidak ada proses!");
		if ("ADD".equals(params.get("action"))) {
			LOGGER.info("<<Action From Add>>");
			System.out.println("================================== isi data : "+data);
			response = ClientsUtil
					.callWs("/ref_transfer/insert", data, HttpMethod.POST);
		} else if ("EDIT".equals(params.get("action"))) {
			LOGGER.info("<<Action From Edit>>");
			data.put("id1", data1.get("id1"));
			data.put("jenis_transfer1", data1.get("jenis_transfer1"));
			
			 response = ClientsUtil.callWs("/ref_transfer/update", data,
			 HttpMethod.POST);
		} else {
			LOGGER.info("<<Action Not identification>>");
			
			LOGGER.info("===============",Exception.class);
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
		this.listTransfer = getDataList();
	}
	

	public List<Map<String, Object>> getListTransfer() {
		return listTransfer;
	}

	public void setListTransfer(List<Map<String, Object>> listTransfer) {
		this.listTransfer = listTransfer;
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