package com.devsuperior.dscalatog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscalatog.dto.ProductDTO;
import com.devsuperior.dscalatog.services.ProductService;
import com.devsuperior.dscalatog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscalatog.tests.Factory;

@WebMvcTest(ProductResources.class)
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;
	
	private PageImpl<ProductDTO> page;
	private ProductDTO productDTO;
	private long validId;
	private long invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		validId = 1L;
		invalidId = 2L;
		
		when(service.findAllPaged(any())).thenReturn(page);
	
		
		when(service.findById(validId)).thenReturn(productDTO);
		when(service.findById(invalidId)).thenThrow(ResourceNotFoundException.class);
		
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products/{id} ", validId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/products/{id} ", invalidId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		
		
	}
}
