package com.sh.djpk.econsole.ui.controller.pelaporan;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.HeaderTemplate;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.JsonUtil;
import com.sh.djpk.share.util.NumberUtils;
import com.sh.djpk.share.util.SystemUtils;

public class Pelaporan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Logger LOGGER = LoggerFactory
			.getLogger(Pelaporan.class);

	private boolean isDisableCbOthers = true;

	private StreamedContent streamedContent;

	// @ManagedProperty("#{headerTemplate}")
	// private HeaderTemplate headerTemplate;

	List<Map<String, Object>> selectOneJenisLaporan = new ArrayList<Map<String, Object>>();
	String selectedJenisLaporan;

	List<Map<String, Object>> selectOneOthers = new ArrayList<Map<String, Object>>();
	String selectedOthers;

	List<Map<String, Object>> selectOnePeriodeLaporan = new ArrayList<Map<String, Object>>();
	String selectedPeriodeLaporan;

	List<Map<String, Object>> selectOneDownload = new ArrayList<Map<String, Object>>();
	String selectedDownload;

	List<Map<String, Object>> dataPencarian = new ArrayList<Map<String, Object>>();

	Map<String, Object> dataPencarianMap = new HashMap<String, Object>();
	
	private boolean renderListData, renderPanelLpsal, renderPanelLpe;
	
	public Pelaporan() {
		selectOneJenisLaporan = getDataSelectOneJenisLaporan();
		selectOnePeriodeLaporan = getDataSelectOnePeriodeLaporan();
		selectOneDownload = getDataSelectOneDownload();
		renderToFalse();
	}
	
	

	public List<Map<String, Object>> getDataSelectOneJenisLaporan() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/global_param/get_all_data", null, HttpMethod.GET,
				"global_param_parent=JENIS_LAPORAN");
		return l;
	}

	public List<Map<String, Object>> getDataSelectOneDownload() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/global_param/get_all_data", null, HttpMethod.GET,
				"global_param_parent=DOWNLOAD");
		return l;
	}

//	public String getPathReport() {
//		String osName = SystemUtils.OS_NAME;
//		String globalParamValue = "NON_IDEN";
//		String path = "";
//		if (osName.toUpperCase().startsWith("WINDOWS")) {
//			globalParamValue = "WINDOWS";
//		} else {
//			globalParamValue = "LINUX";
//		}
//
//		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
//				"/global_param/get_all_data", null, HttpMethod.GET,
//				"global_param_parent=PATH_REPORT", "global_param_value="
//						+ globalParamValue);
//		if (l != null && !l.isEmpty()) {
//			path = (String) l.get(0).get("global_param_label");
//		}
//		return path;
//	}
	
	public String getPathReport() {
		
		String path = "";
		path =   FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		path = path + "/report/";
		LOGGER.info("path report : {}",path);
		return path;
	}

	public List<Map<String, Object>> getDataSelectOnePeriodeLaporan() {
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/lra_report/get_periode", null, HttpMethod.GET);
		return l;
	}

	
	public void renderToFalse(){
		renderListData = false;
		renderPanelLpe = false;
		renderPanelLpsal = false;
	}
	
	@SuppressWarnings("unchecked")
	public void ambilData(int kodeLaporan) {
		Map<String, Object> othersParam = new HashMap<String, Object>();
		othersParam.put("value_cb", selectedOthers);
//		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
//				"/lra_report/get_lra_report_data", othersParam,
//				HttpMethod.POST, "tahun_anggaran=" + getSessionTahunAnggaran(),
//				"periode_id=" + selectedPeriodeLaporan, "kode_laporan="
//						+ kodeLaporan);
		renderToFalse();
		try {
			RestResponse response = ClientsUtil.callWs(
					"/pelaporan/get_all_report", othersParam,
					HttpMethod.POST, "tahun_anggaran=" + getSessionTahunAnggaran(),
					"periode_id=" + selectedPeriodeLaporan, "kode_laporan="
							+ kodeLaporan, "jenis_laporan="+selectedJenisLaporan);
			if (RestResponse.OK_REST_STATUS == response.getStatus()) {
				if (response.getContents() != null) {
					if("5".equals(selectedJenisLaporan) || "6".equals(selectedJenisLaporan)){
						dataPencarianMap = (Map<String, Object>) response.getContents();
						if("5".equals(selectedJenisLaporan)){
							renderPanelLpe = true;
						}else{
							renderPanelLpsal = true;
						}
					}else if("4".equals(selectedJenisLaporan)){
						dataPencarian = (List<Map<String, Object>>) response.getContents();
						renderListData = true;
					}else{
						dataPencarian = (List<Map<String, Object>>) response.getContents();
						renderListData = true;
					}
				}
			}else{
				LOGGER.error(response.getMessage());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		
		
	}

	// public Map<String, Object> ambilData() {
	// Map<String, Object> othersParam = new HashMap<String, Object>();
	// othersParam.put("value_cb", selectedOthers);
	// Map<String, Object> l = ClientsUtil.callWsMapResponse(
	// "/lra_report/get_lra_report_data", othersParam,
	// HttpMethod.POST, "tahun_anggaran=2016", "periode_id="
	// + selectedPeriodeLaporan, "kode_laporan="
	// + selectedJenisLaporan);
	// return l;
	// }

	// public void chooseJenisLaporan(AjaxBehaviorEvent event){
	public void chooseJenisLaporan(ValueChangeEvent event) {
		LOGGER.info("<<chooseJenisLaporan>>");
		// LOGGER.info("<<tahun Anggaran>> " +
		// headerTemplate.getTahunAnggaran());
		String val = event.getNewValue().toString();
		LOGGER.info("<<val={}>>", val);
		// System.out.println(getSessionTahunAnggaran());
		int kodeLaporan = NumberUtils.toInteger(val);
		selectOneOthers = new ArrayList<Map<String, Object>>();
		dataPencarian = new ArrayList<Map<String, Object>>();
		if (kodeLaporan == 2 || kodeLaporan == 3 || kodeLaporan == 4) {
			selectOneOthers = getDataSelectOthers(kodeLaporan);
		}
	}

	

	public int getSessionTahunAnggaran() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		HeaderTemplate hdrT = (HeaderTemplate) session
				.getAttribute("headerTemplate");
		int i = hdrT.getTahunAnggaran();
		return i;
	}

	// TODO memilih download
	public void chooseDownload(ValueChangeEvent event) {
		LOGGER.info("<<chooseDownload>>");
		String val = event.getNewValue().toString();
		LOGGER.info("<<val={}>>", val);
		if ("JSON".equals(val)) {
			// InputStream stream =
			// FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/boromir.jpg");
			// InputStream stream = new
			// StreamedContent file = new DefaultStreamedContent(stream,
			// "image/jpg", "downloaded_boromir.jpg");
		}
	}

	public List<Map<String, Object>> getDataSelectOthers(int jenisLaporan) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/lra_report/get_ref_pemda2", params, HttpMethod.POST,
				"kode_laporan=" + jenisLaporan, "tahun_anggaran="
						+ getSessionTahunAnggaran());
		// if (l != null && !l.isEmpty()) {
		// selectedOthers = (String) l.get(0).get("value_cb");
		// }
		return l;
	}

	public void cari() {
		LOGGER.warn("<<CARI TIDAK DI IDENTIFIKASI>>");
	}

	JasperPrint jasperPrint;

	public void doPrint() throws JRException, UnsupportedEncodingException {
		// JRBeanCollectionDataSource beanCollectionDataSource = new
		// JRBeanCollectionDataSource(
		// dataPencarian);
		InputStream stream = new ByteArrayInputStream(JsonUtil.getJson(
				getDataReport()).getBytes("UTF-8"));
		JsonDataSource dataSource = new JsonDataSource(stream);
		Map<String, Object> params = new HashMap<String, Object>();		
		String pathReport = getPathReport();		
		params.put("TITLE_SATU", getTitle().toUpperCase());
		params.put("DATE_ANGGARAN", getDateReport().toUpperCase());
		params.put("SUBREPORT_DIR", pathReport);
		jasperPrint = JasperFillManager.fillReport(pathReport
				+ SystemUtils.PATH_SEPARATOR + getJasperName(), params,
				dataSource);
	}
	
	public String getJasperName(){
		String jasperName = "";
		if("1".equals(selectedJenisLaporan)){
			jasperName = "LRA_Report.jasper";
		}else if("2".equals(selectedJenisLaporan)){
			jasperName = "Neraca_Report.jasper";
		}else if("3".equals(selectedJenisLaporan)){
			jasperName = "LO_Report.jasper";
		}else if("4".equals(selectedJenisLaporan)){
			jasperName = "LAK_Report.jasper";
		}else if("5".equals(selectedJenisLaporan)){
			jasperName = "LPE_Report.jasper";
		}else if("6".equals(selectedJenisLaporan)){
			jasperName = "LPSAL_Report.jasper";
		}
		return jasperName;
	}
	
	public String getTitle(){
		String selectedName = getSelectOthersName();		
		String title = "LAPORAN REALISASI ANGGARAN PEMDA " + selectedName;
		return title;
	}
	
	public DateFormat getDateFormat(){
		Locale inLocale = new Locale("id", "ID");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG,
				inLocale);
		return dateFormat;
	}
	
	public String getDateReport(){		
		Date now = new Date();
		now.setDate(31);
		now.setMonth(11);
		now.setYear(getSessionTahunAnggaran() - 1900);
		
		String dateAnggaran = getDateFormat().format(now);
		if("5".equals(selectedJenisLaporan) || "6".equals(selectedJenisLaporan)){
			dateAnggaran = dateAnggaran + " Dan " + (getSessionTahunAnggaran() -1 );
		}
		return dateAnggaran;
	}
	
	public String getSelectOthersName(){
		String selectedName = "";
		for (Map<String, Object> map : selectOneOthers) {
			if (map.get("value_cb").equals(selectedOthers)) {
				selectedName = ((String) map.get("label_cb")).trim();
				selectedName = selectedName.replaceAll(selectedOthers.trim(), "");
				break;
			}
		}
		return selectedName;
	}

	List<String> listIdSurplus = new ArrayList<String>(
			Arrays.asList(new String[] { "4", "5", "6" }));
	List<String> listIdBelanjaTransfer = new ArrayList<String>(
			Arrays.asList(new String[] { "5", "6" }));
	List<String> listIdBiaya = new ArrayList<String>(
			Arrays.asList(new String[] { "7" }));

	private Map<String, Object> getDataReport() {
		if("5".equals(selectedJenisLaporan) || "6".equals(selectedJenisLaporan)){
			return dataPencarianMap;
		}
		
		if("2".equals(selectedJenisLaporan) || "3".equals(selectedJenisLaporan) || "4".equals(selectedJenisLaporan)){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("list_tingkat1", dataPencarian);
			result.put("tahun_sekarang", getSessionTahunAnggaran());
			result.put("tahun_sebelum", (getSessionTahunAnggaran() - 1));
			return result;
			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> surplus = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> biaya = new ArrayList<Map<String, Object>>();
		BigDecimal valBelanjaTransfer = new BigDecimal(0);
		BigDecimal valSurplus = new BigDecimal(0);
		BigDecimal valBiaya = new BigDecimal(0);
		// BigDecimal valSilpa = new BigDecimal(0);

		if (dataPencarian != null) {
			for (Map<String, Object> data : dataPencarian) {
				String kdRek1 = (String) data.get("kd_rek_1");
				if (listIdSurplus.contains(kdRek1)) {
					surplus.add(data);
					if (listIdBelanjaTransfer.contains(kdRek1)) {
						valBelanjaTransfer = valBelanjaTransfer
								.add(NumberUtils.toBigdecimal(data
										.get("sum_setelah_kd_rek_1")));
						valSurplus = valSurplus
								.subtract(NumberUtils.toBigdecimal(data
										.get("sum_setelah_kd_rek_1")));
					} else {
						valSurplus = valSurplus
								.add(NumberUtils.toBigdecimal(data
										.get("sum_setelah_kd_rek_1")));
					}
				} else if (listIdBiaya.contains(kdRek1)) {
					biaya.add(data);
					valBiaya = valBiaya.add(NumberUtils.toBigdecimal(data
							.get("sum_setelah_kd_rek_1")));
				}
			}
		}
		result.put("surplus", surplus);
		result.put("biaya", biaya);
		result.put("valBelanjaTransfer", valBelanjaTransfer);
		result.put("valSurplus", valSurplus);
		result.put("valBiaya", valBiaya);
		result.put("valSilpa", (valSurplus.add(valBiaya)));
		return result;
	}

	public void viewCar() {
		Map<String, Object> options = new HashMap<>();
		options.put("resizable", false);
		PrimeFaces.current().dialog().openDynamic("viewCars", options, null);
		// PrimeFaces.current().dialog().openDynamic(outcome, options, params);
	}

	public void printPdf() throws JRException, IOException {
		doPrint();
//		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
//				.getCurrentInstance().getExternalContext().getResponse();
//		ServletOutputStream servletOutputStream = httpServletResponse
//				.getOutputStream();
		FacesContext facesContext = FacesContext.getCurrentInstance();
	     HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
	     
	     ServletOutputStream servletOutputStream = response.getOutputStream();
	     response.setContentType("application/pdf");
	     facesContext.responseComplete();
	     
		JasperExportManager.exportReportToPdfStream(jasperPrint,
				servletOutputStream);
		servletOutputStream.flush();
	    servletOutputStream.close();
	}

	public void showReport() throws JRException, IOException {
		doPrint();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
		is = new ByteArrayInputStream(bos.toByteArray());
		bos.close();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("dataIs", is);
		Map<String, Object> options = new HashMap<>();
		options.put("resizable", false);
		PrimeFaces.current().dialog().openDynamic("show_report", options, null);
		// is.close();
	}

	InputStream is;

	public void jasperToIs() throws JRException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
		is = new ByteArrayInputStream(bos.toByteArray());
		if (bos != null) {
			try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Map<String, Object>> getSelectOneJenisLaporan() {
		return selectOneJenisLaporan;
	}

	public void setSelectOneJenisLaporan(
			List<Map<String, Object>> selectOneJenisLaporan) {
		this.selectOneJenisLaporan = selectOneJenisLaporan;
	}

	public boolean isDisableCbOthers() {
		return isDisableCbOthers;
	}

	public void setDisableCbOthers(boolean isDisableCbOthers) {
		this.isDisableCbOthers = isDisableCbOthers;
	}

	public List<Map<String, Object>> getSelectOneOthers() {
		return selectOneOthers;
	}

	public void setSelectOneOthers(List<Map<String, Object>> selectOneOthers) {
		this.selectOneOthers = selectOneOthers;
	}

	public List<Map<String, Object>> getSelectOnePeriodeLaporan() {
		return selectOnePeriodeLaporan;
	}

	public void setSelectOnePeriodeLaporan(
			List<Map<String, Object>> selectOnePeriodeLaporan) {
		this.selectOnePeriodeLaporan = selectOnePeriodeLaporan;
	}

	public String getSelectedJenisLaporan() {
		return selectedJenisLaporan;
	}

	public void setSelectedJenisLaporan(String selectedJenisLaporan) {
		this.selectedJenisLaporan = selectedJenisLaporan;
	}

	public String getSelectedOthers() {
		return selectedOthers;
	}

	public void setSelectedOthers(String selectedOthers) {
		this.selectedOthers = selectedOthers;
	}

	public String getSelectedPeriodeLaporan() {
		return selectedPeriodeLaporan;
	}

	public void setSelectedPeriodeLaporan(String selectedPeriodeLaporan) {
		this.selectedPeriodeLaporan = selectedPeriodeLaporan;
	}

	public List<Map<String, Object>> getSelectOneDownload() {
		return selectOneDownload;
	}

	public void setSelectOneDownload(List<Map<String, Object>> selectOneDownload) {
		this.selectOneDownload = selectOneDownload;
	}

	public String getSelectedDownload() {
		return selectedDownload;
	}

	public void setSelectedDownload(String selectedDownload) {
		this.selectedDownload = selectedDownload;
	}

	public List<Map<String, Object>> getDataPencarian() {
		return dataPencarian;
	}

	public void setDataPencarian(List<Map<String, Object>> dataPencarian) {
		this.dataPencarian = dataPencarian;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public StreamedContent getStreamedContent() {
		try {
			doPrint();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
			is = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
			streamedContent = new DefaultStreamedContent(is, "application/pdf");
		} catch (Exception e) {
			e.printStackTrace();

		}

		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}



	public Map<String, Object> getDataPencarianMap() {
		return dataPencarianMap;
	}



	public void setDataPencarianMap(Map<String, Object> dataPencarianMap) {
		this.dataPencarianMap = dataPencarianMap;
	}



	public boolean isRenderListData() {
		return renderListData;
	}



	public void setRenderListData(boolean renderListData) {
		this.renderListData = renderListData;
	}



	public boolean isRenderPanelLpsal() {
		return renderPanelLpsal;
	}



	public void setRenderPanelLpsal(boolean renderPanelLpsal) {
		this.renderPanelLpsal = renderPanelLpsal;
	}



	public boolean isRenderPanelLpe() {
		return renderPanelLpe;
	}



	public void setRenderPanelLpe(boolean renderPanelLpe) {
		this.renderPanelLpe = renderPanelLpe;
	}

}
