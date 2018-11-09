package com.sh.djpk.econsole.ui.controller.demo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@ManagedBean
@RequestScoped
public class ShowPdfIs {

	private static final long serialVersionUID = 1L;

	private StreamedContent streamedContent;

	@PostConstruct
	public void init() {
		
show();
	}
	
	public void show(){
		System.out.println("keluar");
		try {
			// ----------------------------------
			Document doc = new Document();

			OutputStream out = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, out);

			doc.open();
			doc.add(new Paragraph("Hello World. ok........"));
			doc.close();
			out.close();

			InputStream in = new ByteArrayInputStream(
					((ByteArrayOutputStream) out).toByteArray());

			streamedContent = new DefaultStreamedContent(in, "application/pdf");
			// -------
			Map<String, Object> session = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			byte[] b = (byte[]) session.get("reportBytes");
			if (b != null) {
				streamedContent = new DefaultStreamedContent(
						new ByteArrayInputStream(b), "application/pdf");
			}
		} catch (Exception e) {
		}
	}

	// ==================================================================
	public StreamedContent getStreamedContent() {
		if (FacesContext.getCurrentInstance().getRenderResponse()) {
			return new DefaultStreamedContent();
		} else {
			return streamedContent;
		}
	}

	// ==================================================================
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	// =====================================================================

}
