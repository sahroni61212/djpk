package com.sh.djpk.econsole.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;

@ManagedBean
@SessionScoped
public class SidebarTemplate implements Serializable {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(SidebarTemplate.class);
	private static final long serialVersionUID = 1L;
	
	List<Map<String, Object>> menuAkses = new ArrayList<Map<String, Object>>();
	
	public SidebarTemplate() {
		doGetMenuAkses();
		
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
	
	public void doGetMenuAkses(){
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession(true);
		@SuppressWarnings("unchecked")
		Map<String, Object> userSession = (Map<String, Object>) session.getAttribute("user_login");
		if(userSession == null || userSession.isEmpty()){
			session.removeAttribute("user_login");
			redirectLogin();
		}
		List<Map<String, Object>> l = ClientsUtil.callWsListResponse(
				"/user/get_menu_askses", userSession, HttpMethod.POST);
		menuAkses = l;
		LOGGER.info("menu akses = {}", menuAkses);
	}

	public List<Map<String, Object>> getMenuAkses() {
		return menuAkses;
	}

	public void setMenuAkses(List<Map<String, Object>> menuAkses) {
		this.menuAkses = menuAkses;
	}

}
