package com.sh.djpk.econsole.ui.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sh.djpk.econsole.ui.controller.pengaturan.UsmanCtl;
import com.sh.djpk.share.model.RestResponse;
import com.sh.djpk.share.model.RestResponse.HttpMethod;
import com.sh.djpk.share.util.ClientsUtil;
import com.sh.djpk.share.util.HashUtils;

@ManagedBean
@ViewScoped
public class LoginCtl implements Serializable {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoginCtl.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txtUserId;
	private String txtPassword;
	private String msg;
	HttpSession session;
	public LoginCtl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		session = request.getSession(true);
		@SuppressWarnings("unchecked")
		Map<String, Object> userSession = (Map<String, Object>) session.getAttribute("user_login");
		if(userSession != null && !userSession.isEmpty()){
			LOGGER.info("user sudah login!, {}", userSession);
			redirectIndex();
		}
	}
	
	private void redirectIndex(){
		LOGGER.info("try redirect to index");
		FacesContext fContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = fContext.getExternalContext();
		try {
			extContext.redirect(extContext.getRequestContextPath() + "/pages/index.xhtml");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	public String btnLogin() {
		Map<String, Object> params = new HashMap<String, Object>();
		msg = "Incorrect username or password.";
		try {
			String passEnc = HashUtils.md5Java(txtPassword);
			params.put("username", txtUserId);
			params.put("password_hash", passEnc);
			RestResponse response = ClientsUtil.callWs("/user/login", params,
					HttpMethod.POST);
			if (response.getStatus() == RestResponse.OK_REST_STATUS) {
				if (response.getContents() != null) {
					Map<String, Object> contents = (Map<String, Object>) response
							.getContents();					
					session.setAttribute("user_login", contents);
					 return "index";
				}else{
					msg = response.getMessage();
				}
			} else {
				msg = response.getMessage();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		// if ("admin".equals(txtUserId) && "admin".equals(txtPassword)) {
		// return "index";
		// } else {
		// txtUserId = "";
		// txtPassword = "";
		// msg = "Incorrect username or password.";
		// }
		return null;
	}

	public String getTxtUserId() {
		return txtUserId;
	}

	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}

	public String getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(String txtPassword) {
		this.txtPassword = txtPassword;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
