package com.sh.djpk.econsole.ui.controller.konsolidasi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.HeaderTemplate;
import com.sh.djpk.econsole.ui.controller.managementdata.DataKonsolidasiCtl;
import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.JsonUtil;

@ManagedBean
@ViewScoped
public class AkunEliminasi {
	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DataKonsolidasiCtl.class);
	private static final long serialVersionUID = 1L;
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	public List<Map<String, Object>> mapAkun;
	
	public List<Map<String, Object>> elimhead;
	
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	List<Map<String, Object>> selectOneHeader = new ArrayList<Map<String, Object>>();
	String selectedHeader;
	
	List<Map<String, Object>> selectOneJenisLaporan = new ArrayList<Map<String, Object>>();
	String selectedJenisLaporan;
	
//	@PostConstruct
//    public void init() {
//		
////		List<Map<String, Object>> 
//		int thn = getSessionTahunAnggaran();
//		mapAkun = ClientsUtil.callWsListResponse(
//				"/akuneliminasi/tahun/"+ String.valueOf(thn), null, HttpMethod.GET,
//				null);
////		System.out.println("========================================="+mapAkun);
//		
////		for (Map<String, Object> mpl : lst){
////			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
////		}
//		selectOneHeader = getDataHeader();
//		selectOneJenisLaporan = getDataSelectOneJenisLaporan();
//		
//		
//	}
	
	@PostConstruct
    public void init() {
		
//		List<Map<String, Object>> 
		int thn = getSessionTahunAnggaran();
		elimhead = ClientsUtil.callWsListResponse(
				"/akuneliminasi/elimhead/"+ String.valueOf(thn), null, HttpMethod.GET,
				null);
//		System.out.println("========================================="+mapAkun);
		
//		for (Map<String, Object> mpl : lst){
//			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
//		}
		selectOneHeader = getDataHeader();
		selectOneJenisLaporan = getDataSelectOneJenisLaporan();
		
		
	}
	
	public Integer getSessionTahunAnggaran() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		HeaderTemplate hdrT = (HeaderTemplate) session
				.getAttribute("headerTemplate");
		Integer i = hdrT.getTahunAnggaran();
		return i;
	}
	public List<Map<String, Object>> getDataHeader() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/akuneliminasi/head", null, HttpMethod.GET,
				null);
		return l;
	}
	
	public List<Map<String, Object>> getDataSelectOneJenisLaporan() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/global_param/get_all_data", null, HttpMethod.GET,
				"global_param_parent=JENIS_LAPORAN");
		return l;
	}
	
	public String add() throws IOException {
		LOGGER.info("<<ADD>>");
		this.data = new HashMap<String, Object>();
//		valueButtonSimpan = "Simpan";
		params.put("action", "ADD");
		return doToDetail();
	}
	
	public String doToDetail() throws IOException {
		LOGGER.info("<<doToDetail>>");

		// selectOnePemda = getDataSelectPemda();
		// selectOneKdUser = getDataSelectKdUser();
		// selectOneAuthItem = getDataAuthItem();
		// Map<String,Object> options = new HashMap<>();
		// options.put("resizable", false);
		// PrimeFaces.current().dialog().openDynamic("_detail", options, null);

		// FacesContext.getCurrentInstance().getExternalContext().redirect("_detail.xhtml");
		return "_detail";
	}
	
	
	
	public String proses() throws Exception {
		
		System.out.println("==================== header ID == " + selectedHeader);
		System.out.println("==================== jenis Data  == " + selectedJenisLaporan);
		Map<String, Object> snd = new HashMap<String, Object>();
		snd.put("laporanId", selectedJenisLaporan);
		snd.put("headerId", selectedHeader);
		snd.put("tahun", getSessionTahunAnggaran().toString());
		
		LOGGER.info("<<proses>>");
		LOGGER.info(snd+"");
		RestResponse response = ClientsUtil.callWs("/akuneliminasi/proses", snd, HttpMethod.POST);
		LOGGER.info("response={}",JsonUtil.getJson(response));
		if(response.getStatus() == RestResponse.OK_REST_STATUS){
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);	        
	        init();
		}else{
			FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",  response.getMessage());
	        FacesContext.getCurrentInstance().addMessage(null, messages);
		}
		
		
		
		
		
		return "_index";
		
	}

	public List<Map<String, Object>> getMapAkun() {
		return mapAkun;
	}

	public void setMapAkun(List<Map<String, Object>> mapAkun) {
		this.mapAkun = mapAkun;
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

	public List<Map<String, Object>> getSelectOneHeader() {
		return selectOneHeader;
	}

	public void setSelectOneHeader(List<Map<String, Object>> selectOneHeader) {
		this.selectOneHeader = selectOneHeader;
	}

	public String getSelectedHeader() {
		return selectedHeader;
	}

	public void setSelectedHeader(String selectedHeader) {
		this.selectedHeader = selectedHeader;
	}

	public List<Map<String, Object>> getSelectOneJenisLaporan() {
		return selectOneJenisLaporan;
	}

	public void setSelectOneJenisLaporan(
			List<Map<String, Object>> selectOneJenisLaporan) {
		this.selectOneJenisLaporan = selectOneJenisLaporan;
	}

	public String getSelectedJenisLaporan() {
		return selectedJenisLaporan;
	}

	public void setSelectedJenisLaporan(String selectedJenisLaporan) {
		this.selectedJenisLaporan = selectedJenisLaporan;
	}

	public List<Map<String, Object>> getElimhead() {
		return elimhead;
	}

	public void setElimhead(List<Map<String, Object>> elimhead) {
		this.elimhead = elimhead;
	}

	
	
}
