package br.com.indt.app.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.indt.app.service.libraryAppService;

@Service
public class libraryAppServiceImpl implements libraryAppService, Serializable {

	private static final long serialVersionUID = 8849003092938718475L;

	private static final String AUTHORS_URL = "https://bibliapp.herokuapp.com/api/authors";

	private static final String BOOKS_URL = "https://bibliapp.herokuapp.com/api/books";
	
	
	@Autowired
	@Qualifier("restTemplateApp")
	private RestTemplate restTemplateApp;

	@Override
	public Boolean inserirAutor(String json) throws Exception {
		String uri = AUTHORS_URL;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type","application/json");

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplateApp.postForEntity(uri, request, String.class);
		
		return response.getStatusCode().value() == 200;
	}

	@Override
	public Boolean inserirLivro(String json) throws Exception {
		String uri = BOOKS_URL;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type","application/json");

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		
		ResponseEntity<String> response = restTemplateApp.postForEntity(uri, request, String.class);
		
		return response.getStatusCode().value() == 200;
	}

}
