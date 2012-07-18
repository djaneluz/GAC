package br.com.smartangel.pulseira.view;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class BaseBean {

	private String urlImage = "primefaces-smartangel/images";

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public void setTituloCabecalho(String str) {
		setRequestAttribute("screenTitle", str);
	}
	
	public void setRequestAttribute(String str, Object obj) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.setAttribute(str, obj);		
	}
	
	public String getRequestParameter(String str) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();        
	    return (String) request.getParameter(str);
	}
	
	
    public String getMessageFromBundle(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication()
                .getResourceBundle(context, "messageBundle");
        String message = bundle.getString(key);

        return message;

    }
    
    public void setFacesMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(getMessageFromBundle(key), 
                getMessageFromBundle(key)));        
    }
    
    public void setFacesMessage(String keyTitle, String keyDetail) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(getMessageFromBundle(keyTitle), 
                getMessageFromBundle(keyDetail)));        
    }
    
	
}
