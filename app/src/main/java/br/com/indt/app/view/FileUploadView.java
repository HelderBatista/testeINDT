package br.com.indt.app.view;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.json.simple.JSONObject;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.indt.app.service.libraryAppService;

@Controller
public class FileUploadView {
     
    private UploadedFile file;
 
    @Autowired
    private libraryAppService service;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public libraryAppService getService() {
		return service;
	}

	public void setService(libraryAppService service) {
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	public void upload() throws IOException {
        if(file != null && file.getFileName().toUpperCase().endsWith(".TXT")) {
        	
        	Reader reader = new InputStreamReader(file.getInputstream());
        	
        	@SuppressWarnings("resource")
			Scanner scanner = new Scanner(reader).useDelimiter("\\||\\r\\n");
        	
        	JSONObject json;
    		
    		try {
        	
        	while (scanner.hasNext()) {
        		String type = scanner.next();
        		
        		if (type.toLowerCase().equals("author")) {
            		String firstName = scanner.next();
            		String lastName = scanner.next();
            		
            		json = new JSONObject();
            		
            		json.put("firstName",firstName);
            		json.put("lastName",lastName);
					
            		this.service.inserirAutor(json.toString());
				} else if (type.toLowerCase().equals("book")) {
					String title = scanner.next();
					String authorId = scanner.next();
            		
            		json = new JSONObject();
            		
            		json.put("title",title);
            		json.put("authorId",authorId);
					
            		this.service.inserirLivro(json.toString());
				}
			}
        	
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados cadatrados com Sucesso");
            FacesContext.getCurrentInstance().addMessage(null, message);

			} catch (Exception e) {
				e.printStackTrace();
			}
        } else if (file.getFileName().equals("")) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nenhum arquivo informado.");
            FacesContext.getCurrentInstance().addMessage(null, message);
		} else if (!file.getFileName().toUpperCase().endsWith(".TXT")) {
        	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Arquivo Inválido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
		}
    }
}