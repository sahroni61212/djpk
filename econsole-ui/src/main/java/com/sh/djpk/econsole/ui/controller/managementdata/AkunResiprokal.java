package com.sh.djpk.econsole.ui.controller.managementdata;

import java.io.IOException;
import java.util.ArrayList;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
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
@SessionScoped
public class AkunResiprokal {
	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DataKonsolidasiCtl.class);
	private static final long serialVersionUID = 1L;
	
	HashMap<String, Object> data = new HashMap<String, Object>();
	public List<Map<String, Object>> mapAkun;
	HashMap<String, Object> params = new HashMap<String, Object>();
	
	List<Map<String, Object>> selectOneHeader = new ArrayList<Map<String, Object>>();
	String selectedHeader;
	
	List<Map<String, Object>> selectOneJenisLaporan = new ArrayList<Map<String, Object>>();
	String selectedJenisLaporan;
	
	List<Map<String, Object>> selectOneJenisTransfer = new ArrayList<Map<String, Object>>();
	String selectedJenisTransfer;
	
	List<Map<String, Object>> selectOneLevelData = new ArrayList<Map<String, Object>>();
	String selectedLevelData;
	
	List<Map<String, Object>> selectOneLocation = new ArrayList<Map<String, Object>>();
	String selectedLocation;
	
	Map<String, Object> userSession = new HashMap<String, Object>();
	
	int resiId;
	
	String jenisDk;
	
	List<Map<String, Object>> selectOneAkr1 = new ArrayList<Map<String, Object>>();
	String selectedAkr1;
	List<Map<String, Object>> selectOneAkr2 = new ArrayList<Map<String, Object>>();
	String selectedAkr2;
	List<Map<String, Object>> selectOneAkr3 = new ArrayList<Map<String, Object>>();
	String selectedAkr3;
	List<Map<String, Object>> selectOneAkr4 = new ArrayList<Map<String, Object>>();
	String selectedAkr4;
	List<Map<String, Object>> selectOneAkr5 = new ArrayList<Map<String, Object>>();
	String selectedAkr5;
	
	@PostConstruct
    public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
//		List<Map<String, Object>> 
		int thn = getSessionTahunAnggaran();
		mapAkun = ClientsUtil.callWsListResponse(
				"/resiprokal/tahun/"+ String.valueOf(thn), null, HttpMethod.GET,
				null);
//		System.out.println("========================================="+mapAkun);
		
//		for (Map<String, Object> mpl : lst){
//			period.add(new periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
//		}
		
		selectOneJenisLaporan = getDataSelectOneJenisLaporan();
		setjenistrf();
		setjenislevel();
		setjenisloc();
		userSession = (Map<String, Object>) session.getAttribute("user_login");
		
		
		selectOneAkr1 = getDataAkr1a("1");
		
		
		
	}
	
	public void reload() throws IOException {
		init();
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}
	
	public void setjenistrf(){
		
		Map<String, Object> trf = new HashMap<String, Object>();
		trf.put("id", "1");
		trf.put("jenis_transfer", "Regional Dalam Provinsi");
		selectOneJenisTransfer.add(trf);
		trf = new HashMap<String, Object>();
		trf.put("id", "2");
		trf.put("jenis_transfer", "Antar Provinsi Dalam Wilayah");
		selectOneJenisTransfer.add(trf);
		trf = new HashMap<String, Object>();
		trf.put("id", "3");
		trf.put("jenis_transfer", "Antar Provinsi Luar Wilayah dan Pusat");
		selectOneJenisTransfer.add(trf);
		
	}
	
	public void setjenislevel(){
		
		Map<String, Object> lvl = new HashMap<String, Object>();
		lvl.put("id", "3");
		lvl.put("nama", "level 3");
		selectOneLevelData.add(lvl);
		lvl = new HashMap<String, Object>();
		lvl.put("id", "4");
		lvl.put("nama", "level 4");
		selectOneLevelData.add(lvl);
		lvl = new HashMap<String, Object>();
		lvl.put("id", "5");
		lvl.put("nama", "level 5");
		selectOneLevelData.add(lvl);
		
	}
	
	public void setjenisloc(){
		
		Map<String, Object> loc = new HashMap<String, Object>();
		loc.put("id", "0");
		loc.put("nama", "Nasional");
		selectOneLocation.add(loc);
		loc = new HashMap<String, Object>();
		loc.put("id", "1");
		loc.put("nama", "Wilayah");
		selectOneLocation.add(loc);
		loc = new HashMap<String, Object>();
		loc.put("id", "2");
		loc.put("nama", "Provinsi");
		selectOneLocation.add(loc);
		loc = new HashMap<String, Object>();
		loc.put("id", "3");
		loc.put("nama", "Pemkab / Pemkot");
		selectOneLocation.add(loc);
		
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
	
	public void getakr1bylap(AjaxBehaviorEvent event){
		
//		String st = (String) e.getNewValue();
		System.out.println("===================== laporan ID == "+ selectedJenisLaporan);
		selectOneAkr1 = getDataAkr1a(selectedJenisLaporan);
	}
	
	public void getakr2(AjaxBehaviorEvent event){
		
//		String st = (String) e.getNewValue();
		selectOneAkr2 = getDataAkr2(selectedAkr1);
	}
	public void getakr3(AjaxBehaviorEvent event){
		
//		String st = (String) e.getNewValue();
		selectOneAkr3 = getDataAkr3(selectedAkr1, selectedAkr2);
	}
	public void getakr4(AjaxBehaviorEvent event){
	
//	String st = (String) e.getNewValue();
	selectOneAkr4 = getDataAkr4(selectedAkr1, selectedAkr2, selectedAkr3);
	}
	public void getakr5(AjaxBehaviorEvent event){
	
//	String st = (String) e.getNewValue();
	selectOneAkr5 = getDataAkr5(selectedAkr1, selectedAkr2, selectedAkr3, selectedAkr4);
	}
	
	public List<Map<String, Object>> getDataAkr1() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr1", null, HttpMethod.GET,
				null);
		return l;
	}
	public List<Map<String, Object>> getDataAkr2(String akr1) {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr2/"+akr1, null, HttpMethod.GET,
				null);
		return l;
	}
	public List<Map<String, Object>> getDataAkr3(String akr1, String akr2) {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr3/"+akr1+"/"+akr2, null, HttpMethod.GET,
				null);
		return l;
	}
	public List<Map<String, Object>> getDataAkr4(String akr1, String akr2,String akr3) {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr4/"+akr1+"/"+akr2+"/"+akr3, null, HttpMethod.GET,
				null);
		return l;
	}
	public List<Map<String, Object>> getDataAkr5(String akr1, String akr2,String akr3, String akr4) {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr5/"+akr1+"/"+akr2+"/"+akr3+"/"+akr4, null, HttpMethod.GET,
				null);
		return l;
	}
	
	public List<Map<String, Object>> getDataAkr1a(String lap) {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/resiprokal/akr1/"+lap, null, HttpMethod.GET,
				null);
		return l;
	}
	
	
	public List<Map<String, Object>> getDataSelectOneJenisLaporan() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/global_param/get_all_data", null, HttpMethod.GET,
				"global_param_parent=JENIS_LAPORAN");
		l.remove(5);
		l.remove(4);
		l.remove(3);
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
	
	public List<Map<String, Object>> getDataHeader() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/akuneliminasi/head", null, HttpMethod.GET,
				null);
		return l;
	}
	
	public String proses() throws Exception {
		Map<String, Object> kirim = new HashMap<String, Object>();
		
		kirim.put("tahun", getSessionTahunAnggaran());
		
		kirim.put("d_k", jenisDk);
		kirim.put("laporan_id", Integer.valueOf(selectedJenisLaporan));
		kirim.put("transfer_id", Integer.valueOf(selectedJenisTransfer));
		kirim.put("resiprokal_id", resiId);
		kirim.put("level_data", Integer.valueOf(selectedLevelData));
		kirim.put("location_account", Integer.valueOf(selectedLocation));
		kirim.put("id", userSession.get("id"));
		if (selectedLevelData.equals("4")){
			kirim.put("kd_rek1", selectedAkr1);
			kirim.put("kd_rek2", selectedAkr2);
			kirim.put("kd_rek3", selectedAkr3);
			kirim.put("kd_rek4", selectedAkr4);
			kirim.put("kd_rek5", "00");
		}
		if (selectedLevelData.equals("4")){
			kirim.put("kd_rek1", selectedAkr1);
			kirim.put("kd_rek2", selectedAkr2);
			kirim.put("kd_rek3", selectedAkr3);
			kirim.put("kd_rek4", selectedAkr4);
			kirim.put("kd_rek5", selectedAkr4);
		}
		
		LOGGER.info("<<insert resiprokal>>");
		LOGGER.info(kirim+"");
		RestResponse response = ClientsUtil.callWs("/resiprokal/inresi", kirim, HttpMethod.POST);
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

	public List<Map<String, Object>> getSelectOneAkr1() {
		return selectOneAkr1;
	}

	public void setSelectOneAkr1(List<Map<String, Object>> selectOneAkr1) {
		this.selectOneAkr1 = selectOneAkr1;
	}

	public String getSelectedAkr1() {
		return selectedAkr1;
	}

	public void setSelectedAkr1(String selectedAkr1) {
		this.selectedAkr1 = selectedAkr1;
	}

	public List<Map<String, Object>> getSelectOneAkr2() {
		return selectOneAkr2;
	}

	public void setSelectOneAkr2(List<Map<String, Object>> selectOneAkr2) {
		this.selectOneAkr2 = selectOneAkr2;
	}

	public String getSelectedAkr2() {
		return selectedAkr2;
	}

	public void setSelectedAkr2(String selectedAkr2) {
		this.selectedAkr2 = selectedAkr2;
	}

	public List<Map<String, Object>> getSelectOneAkr3() {
		return selectOneAkr3;
	}

	public void setSelectOneAkr3(List<Map<String, Object>> selectOneAkr3) {
		this.selectOneAkr3 = selectOneAkr3;
	}

	public String getSelectedAkr3() {
		return selectedAkr3;
	}

	public void setSelectedAkr3(String selectedAkr3) {
		this.selectedAkr3 = selectedAkr3;
	}

	public List<Map<String, Object>> getSelectOneAkr4() {
		return selectOneAkr4;
	}

	public void setSelectOneAkr4(List<Map<String, Object>> selectOneAkr4) {
		this.selectOneAkr4 = selectOneAkr4;
	}

	public String getSelectedAkr4() {
		return selectedAkr4;
	}

	public void setSelectedAkr4(String selectedAkr4) {
		this.selectedAkr4 = selectedAkr4;
	}

	public List<Map<String, Object>> getSelectOneAkr5() {
		return selectOneAkr5;
	}

	public void setSelectOneAkr5(List<Map<String, Object>> selectOneAkr5) {
		this.selectOneAkr5 = selectOneAkr5;
	}

	public String getSelectedAkr5() {
		return selectedAkr5;
	}

	public void setSelectedAkr5(String selectedAkr5) {
		this.selectedAkr5 = selectedAkr5;
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

	public List<Map<String, Object>> getSelectOneJenisTransfer() {
		return selectOneJenisTransfer;
	}

	public void setSelectOneJenisTransfer(
			List<Map<String, Object>> selectOneJenisTransfer) {
		this.selectOneJenisTransfer = selectOneJenisTransfer;
	}

	public String getSelectedJenisTransfer() {
		return selectedJenisTransfer;
	}

	public void setSelectedJenisTransfer(String selectedJenisTransfer) {
		this.selectedJenisTransfer = selectedJenisTransfer;
	}

	public List<Map<String, Object>> getSelectOneLevelData() {
		return selectOneLevelData;
	}

	public void setSelectOneLevelData(List<Map<String, Object>> selectOneLevelData) {
		this.selectOneLevelData = selectOneLevelData;
	}

	public String getSelectedLevelData() {
		return selectedLevelData;
	}

	public void setSelectedLevelData(String selectedLevelData) {
		this.selectedLevelData = selectedLevelData;
	}

	public List<Map<String, Object>> getSelectOneLocation() {
		return selectOneLocation;
	}

	public void setSelectOneLocation(List<Map<String, Object>> selectOneLocation) {
		this.selectOneLocation = selectOneLocation;
	}

	public String getSelectedLocation() {
		return selectedLocation;
	}

	public void setSelectedLocation(String selectedLocation) {
		this.selectedLocation = selectedLocation;
	}

	public int getResiId() {
		return resiId;
	}

	public void setResiId(int resiId) {
		this.resiId = resiId;
	}

	public String getJenisDk() {
		return jenisDk;
	}

	public void setJenisDk(String jenisDk) {
		this.jenisDk = jenisDk;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	
	
}
