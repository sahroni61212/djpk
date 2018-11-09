package com.sh.djpk.econsole.ui.controller.managementdata;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.HeaderTemplate;
import com.sh.djpk.econsole.ui.controller.pengaturan.UsmanCtl;
import com.sh.djpk.share.dto.periodeDto;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.DateUtils;
import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.UploadExcel;

@ManagedBean
@SessionScoped
public class DataKonsolidasiCtl implements Serializable {

	/**
	 * 
	 */
	public static final Logger LOGGER = LoggerFactory
			.getLogger(DataKonsolidasiCtl.class);
	private static final long serialVersionUID = 1L;

	public List<Map<String, Object>> headerList;

	HashMap<String, Object> data = new HashMap<String, Object>();
	HashMap<String, Object> data1 = new HashMap<String, Object>();
	HashMap<String, Object> params = new HashMap<String, Object>();
	List<Map<String, Object>> contentfile = new ArrayList<Map<String, Object>>();

	Map<String, Object> userSession;

	List<Map<String, Object>> selectOneJenisLaporan = new ArrayList<Map<String, Object>>();
	String selectedJenisLaporan;
	String namafile;

	private String valueButtonSimpan;

	public UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private String fileContent;
	private String fileName;

	public String getFileContent() {
		return fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public String handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
		namafile = event.getFile().getFileName();
		System.out
				.println("=============================Nama File yang di olah : "
						+ namafile);
		System.out.println("================== jenis laporan ==="
				+ selectedJenisLaporan);
		try {
			System.out.println("=============================masuk listener");
			InputStream file = event.getFile().getInputstream();
			UploadExcel exl = new UploadExcel();
			this.contentfile = exl.readContent(file);
			System.out.println("Isi content " + JsonUtil.getJson(contentfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "_index";
	}

	public DataKonsolidasiCtl() {
		load();

	}

	public void onload() {
		headerList = getDataHeader();

		// if (event.equals("preRenderView")){
		// periodlist = getDataPeriode();
		// }

	}

	@PostConstruct
	public void load() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		// @SuppressWarnings("unchecked")
		this.userSession = (Map<String, Object>) session
				.getAttribute("user_login");
		headerList = getDataHeader();
		selectOneJenisLaporan = getDataSelectOneJenisLaporan();

	}
	
	public void reload() throws IOException {
		load();
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
	}

	public String proses() throws Exception {

		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String strDate = dateFormat.format(date);
		String strDate1 = dateFormat1.format(date);
		int lap_id = Integer.valueOf(selectedJenisLaporan);
		String headId = userSession.get("id") + strDate;
		Map<String, Object> data_1 = new HashMap<String, Object>();
		Map<String, Object> datakirim = new HashMap<String, Object>();

		System.out.println("===================di proses Isi content "
				+ JsonUtil.getJson(contentfile));
		if (!contentfile.isEmpty()) {

			if ((lap_id == 1)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 5);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

				RestResponse response = new RestResponse(
						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
				LOGGER.info("<<Action proses Excel content to database>>");
				response = ClientsUtil.callWs("/datakonsol/inkonsol",
						datakirim, HttpMethod.POST);

				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "INFO",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);

					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
					refreshListData();
					data = new HashMap<String, Object>();
					return "_index";
				} else {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "ERROR",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);
				}
			} else if ((lap_id == 2)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 4);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

				RestResponse response = new RestResponse(
						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
				LOGGER.info("<<Action proses Excel content to database>>");
				response = ClientsUtil.callWs("/datakonsol/inkonsol2",
						datakirim, HttpMethod.POST);

				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "INFO",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);

					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
					refreshListData();
					data = new HashMap<String, Object>();
					return "_index";
				} else {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "ERROR",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);
				}
			} else if ((lap_id == 3)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 4);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

				RestResponse response = new RestResponse(
						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
				LOGGER.info("<<Action proses Excel content to database>>");
				response = ClientsUtil.callWs("/datakonsol/inkonsol3",
						datakirim, HttpMethod.POST);

				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "INFO",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);

					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
					refreshListData();
					data = new HashMap<String, Object>();
					return "_index";
				} else {
					FacesMessage messages = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "ERROR",
							response.getMessage());
					FacesContext.getCurrentInstance()
							.addMessage(null, messages);
				}
			} else if ((lap_id == 4)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 5);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

//				RestResponse response = new RestResponse(
//						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
//				LOGGER.info("<<Action proses Excel content to database>>");
//				response = ClientsUtil.callWs("/datakonsol/inkonsol",
//						datakirim, HttpMethod.POST);
//
//				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_INFO, "INFO",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//
//					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
//					refreshListData();
//					data = new HashMap<String, Object>();
//					return "_index";
//				} else {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_ERROR, "ERROR",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//				}
			} else if ((lap_id == 5)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 5);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

//				RestResponse response = new RestResponse(
//						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
//				LOGGER.info("<<Action proses Excel content to database>>");
//				response = ClientsUtil.callWs("/datakonsol/inkonsol",
//						datakirim, HttpMethod.POST);
//
//				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_INFO, "INFO",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//
//					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
//					refreshListData();
//					data = new HashMap<String, Object>();
//					return "_index";
//				} else {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_ERROR, "ERROR",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//				}
			} else if ((lap_id == 6)) {

				data_1 = contentfile.get(0);

				datakirim.put("content", contentfile);
				datakirim.put("id", headId);
				datakirim.put("tahun", data_1.get("tahun"));
				datakirim.put("period_id", data_1.get("periode_id"));
				datakirim.put("laporan_id", lap_id);
				datakirim.put("level", 5);
				datakirim.put("tgl_kirim", strDate1);
				datakirim.put("user_id", userSession.get("id"));

				System.out.println("================== jenis laporan ==="
						+ selectedJenisLaporan);
				System.out.println("================== Header ID ===" + headId);

				System.out.println("===================di proses Isi dikirim "
						+ JsonUtil.getJson(datakirim));

//				RestResponse response = new RestResponse(
//						RestResponse.OK_REST_STATUS, "Tidak ada proses!");
//				LOGGER.info("<<Action proses Excel content to database>>");
//				response = ClientsUtil.callWs("/datakonsol/inkonsol",
//						datakirim, HttpMethod.POST);
//
//				if (response.getStatus() == RestResponse.OK_REST_STATUS) {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_INFO, "INFO",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//
//					// FacesContext.getCurrentInstance().getExternalContext().redirect("_index.xhtml");
//					refreshListData();
//					data = new HashMap<String, Object>();
//					return "_index";
//				} else {
//					FacesMessage messages = new FacesMessage(
//							FacesMessage.SEVERITY_ERROR, "ERROR",
//							response.getMessage());
//					FacesContext.getCurrentInstance()
//							.addMessage(null, messages);
//				}
			}

		}

		return "_index";

	}

	public List<Map<String, Object>> getDataHeader() {

		if (getSessionTahunAnggaran() != null) {
			System.out
					.println("=================================tahun anggaran : "
							+ getSessionTahunAnggaran());
		}

		LOGGER.info("<<get data konsol header all>>");

		List<Map<String, Object>> lst = ClientsUtil.callWsListResponse(
				"/datakonsol/all", null, HttpMethod.GET, null);
		System.out.println("=========================================" + lst);

		// period = new ArrayList<periodeDto>();
		// for (Map<String, Object> mpl : periodlist){
		// period.add(new
		// periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
		// }
		FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				.remove(headerList);

		return lst;
	}

	// @PostConstruct
	// public void init() {
	// if (getSessionTahunAnggaran()!=null){
	// System.out.println("=================================tahun anggaran : "+
	// getSessionTahunAnggaran());
	// }
	// headerList = new ArrayList<Map<String,Object>>();
	// LOGGER.info("<<get data periode all>>");
	// List<Map<String, Object>> lst = ClientsUtil.callWsListResponse(
	// "/datakonsol/all", null, HttpMethod.GET,
	// null);
	// headerList = lst;
	// System.out.println("========================================="+lst);
	// // period = new ArrayList<periodeDto>();
	// // for (Map<String, Object> mpl : periodlist){
	// // period.add(new
	// periodeDto(mpl.get("id").toString(),mpl.get("name").toString()));
	// // }
	// }

	public Integer getSessionTahunAnggaran() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		HeaderTemplate hdrT = (HeaderTemplate) session
				.getAttribute("headerTemplate");
		Integer i = hdrT.getTahunAnggaran();
		return i;
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
		valueButtonSimpan = "Simpan";
		params.put("action", "ADD");
		return doToDetail();
	}

	public String edit() throws IOException {
		LOGGER.info("<<EDIT>>");
		// data1 = data;
		data1.put("id1", data.get("id"));
		data1.put("name1", data.get("name"));
		LOGGER.info("data={}", data);
		LOGGER.info("data1={}", data1);

		System.out.println("++++++++++++++++++++++++++++++" + data);
		valueButtonSimpan = "Update";
		params.put("action", "EDIT");
		return doToDetail();
	}

	public String view() throws IOException {
		LOGGER.info("<<view>>");
		// data = new HashMap<String, Object>();
		LOGGER.info("data={}", data);
		System.out.println("++++++++++++++++++++++++++++++" + data);
		valueButtonSimpan = "view";
		params.put("action", "VIEW");
		return "_detailview";
	}

	public String delete() throws Exception {
		LOGGER.info("<<DELETE>>");
		// data = new HashMap<String, Object>();
		LOGGER.info("data={}", data);
		valueButtonSimpan = "Hapus";
		params.put("action", "DELETE");
		dodelete();
		return "_index";
	}

	public void dodelete() throws Exception {
		LOGGER.info("<<Delete>>");
		LOGGER.info(data + "");
		// RestResponse response = ClientsUtil.callWs("/periode/del", data,
		// HttpMethod.POST);
		// LOGGER.info("response={}",JsonUtil.getJson(response));
		// if(response.getStatus() == RestResponse.OK_REST_STATUS){
		// FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "INFO", response.getMessage());
		// FacesContext.getCurrentInstance().addMessage(null, messages);
		// refreshListData();
		// }else{
		// FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,
		// "ERROR", response.getMessage());
		// FacesContext.getCurrentInstance().addMessage(null, messages);
		// }

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

	public String create() throws Exception {
		LOGGER.info("<<CREATE>>");
		data.put("proc", "proses Insert");
		LOGGER.info(data + "");
		int i = Integer.valueOf((String) data.get("id"));
		String s = (String) data.get("name");
		data.clear();
		data.put("id", i);
		data.put("name", s);
		System.out.println("================================== isi data : "
				+ data);
		System.out.println("================================== isi data1 : "
				+ data1);

		RestResponse response = new RestResponse(RestResponse.OK_REST_STATUS,
				"Tidak ada proses!");
		if ("ADD".equals(params.get("action"))) {
			LOGGER.info("<<Action From Add>>");
			System.out.println("================================== isi data : "
					+ data);
			response = ClientsUtil.callWs("/periode/insert", data,
					HttpMethod.POST);
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
		this.headerList = getDataHeader();
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

	public List<Map<String, Object>> getHeaderlist() {
		return headerList;
	}

	public void setHeaderlist(List<Map<String, Object>> headerList) {
		this.headerList = headerList;
	}

	public HashMap<String, Object> getData1() {
		return data1;
	}

	public void setData1(HashMap<String, Object> data1) {
		this.data1 = data1;
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
		System.out.println("================== jenis laporan ==="
				+ selectedJenisLaporan);
	}

	public String getNamafile() {
		System.out
				.println("================== masuuuuuuuuuuukkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk  ===");

		return namafile;
	}

	public void setNamafile(String namafile) {
		this.namafile = namafile;
	}

	public List<Map<String, Object>> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<Map<String, Object>> headerList) {
		this.headerList = headerList;
	}

	public Map<String, Object> getUserSession() {
		return userSession;
	}

	public void setUserSession(Map<String, Object> userSession) {
		this.userSession = userSession;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
