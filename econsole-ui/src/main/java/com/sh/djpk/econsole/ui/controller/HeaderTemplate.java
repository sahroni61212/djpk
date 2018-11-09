package com.sh.djpk.econsole.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.pelaporan.Pelaporan;
import com.sh.djpk.share.util.DateUtils;

@ManagedBean
@SessionScoped
public class HeaderTemplate implements Serializable {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(HeaderTemplate.class);
	private static final long serialVersionUID = 1L;

	public Integer tahunAnggaran;

	private List<Integer> listTahunAnggaran;

	 
	
	public HeaderTemplate() {
		// this.tahunAnggaran = 0;
		checkSession();
		generateTahunAnggaran();

	}
	
	public void checkSession(){
		Map<String, Object> userSession = getDataSessionUserLogin();
		if(userSession != null && !userSession.isEmpty()){
			LOGGER.info("user login!, {}", userSession);
		}else{
			redirectLogin();
		}
	}
	
	Map<String, Object> getDataSessionUserLogin(){		
		@SuppressWarnings("unchecked")
		Map<String, Object> userSession = (Map<String, Object>) getSession().getAttribute("user_login");
		return userSession;
	}
	
	HttpSession getSession(){
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession(true);
		return session;
	}
	
	public void logout(){		
		getSession().removeAttribute("user_login");
		redirectLogin();
	}
	
	private void redirectLogin(){
		LOGGER.info("try redirect to index");
		FacesContext fContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = fContext.getExternalContext();
		try {
			extContext.redirect(extContext.getRequestContextPath() + "/pages/login.xhtml");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	

	private void generateTahunAnggaran() {
		int nowYear = DateUtils.getYears(new Date());
		listTahunAnggaran = new ArrayList<Integer>();
		listTahunAnggaran.add(nowYear - 3);
		listTahunAnggaran.add(nowYear - 2);
		listTahunAnggaran.add(nowYear - 1);
		listTahunAnggaran.add(nowYear);
		listTahunAnggaran.add(nowYear + 1);
		listTahunAnggaran.add(nowYear + 2);
		tahunAnggaran = nowYear - 1;
	}
	
	public void pilihtahun(ValueChangeEvent event){
		
		
		
	}
	public void chooseTahunAnggaran(ValueChangeEvent event) {
		LOGGER.info("<<chooseTahunAnggaran>>");
//		HttpServletRequest request = (HttpServletRequest) FacesContext
//				.getCurrentInstance().getExternalContext().getRequest();
//		HttpSession session = request.getSession(true);
//		session.getAttribute("tahunAnggaran");
	}

	public Integer getTahunAnggaran() {
		return tahunAnggaran;
	}

	public void setTahunAnggaran(Integer tahunAnggaran) {
		this.tahunAnggaran = tahunAnggaran;
	}

	public List<Integer> getListTahunAnggaran() {
		return listTahunAnggaran;
	}

	public void setListTahunAnggaran(List<Integer> listTahunAnggaran) {
		this.listTahunAnggaran = listTahunAnggaran;
	}

}
