package com.sh.djpk.econsole.ui.controller.pelaporan;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped
public class ShowReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private StreamedContent streamedContent;

	@PostConstruct
	public void init() {
		show();
	}

	// public ShowReport() {
	// super();
	// // TODO Auto-generated constructor stub
	// show();
	// }

	public void show() {
		InputStream in = null;
		try {
			in = getDataIs();
			streamedContent = new DefaultStreamedContent(in, "application/pdf");
			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			byte[] b = (byte[]) session.get("reportBytes");
			if (b != null) {
				streamedContent = new DefaultStreamedContent(
						new ByteArrayInputStream(b), "application/pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			 if (in != null) {
			 try {
			 in.close();
			 } catch (IOException e) {
			 e.printStackTrace();
			 }
			 }
		}
	}

	public Pelaporan getPelaporan() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		Pelaporan pelaporan = (Pelaporan) session.getAttribute("pelaporan");
		return pelaporan;

	}

	public InputStream getDataIs() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		InputStream is = (InputStream) session.getAttribute("dataIs");
//		InputStream is = null;
		return is;

	}

	public StreamedContent getStreamedContent() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			return new DefaultStreamedContent();
		} else {
			return streamedContent;
		}
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

}
