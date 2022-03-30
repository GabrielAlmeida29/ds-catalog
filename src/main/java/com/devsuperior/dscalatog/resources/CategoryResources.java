package com.devsuperior.dscalatog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscalatog.entities.Category;
import com.devsuperior.dscalatog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResources {

	//END POINT = ROTAS DE RESPOSTA
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
}
