package com.sh.djpk.econsole.ui.controller.demo;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped
public class ShowPdf {

	public void viewPdf() {
		System.out.println("VIEWWWWWWWWWWWWWWWW");
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
//        options.put("width", "100%");
        PrimeFaces.current().dialog().openDynamic("show_pdf_is", options, null);
//        PrimeFaces.current().dialog().openDynamic("show_pdf_is");
    }
	
	public void showEmployees(ActionEvent ae) {
		Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
	      RequestContext.getCurrentInstance()
	                    .openDialog("show_pdf_is");
	  }
	
}
