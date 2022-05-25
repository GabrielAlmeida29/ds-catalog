package com.devsuperior.dscalatog.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscalatog.dto.ProductDTO;
import com.devsuperior.dscalatog.entities.Category;
import com.devsuperior.dscalatog.entities.Product;
import com.devsuperior.dscalatog.repositories.CategoryRepository;
import com.devsuperior.dscalatog.repositories.ProductRepository;
import com.devsuperior.dscalatog.services.exceptions.DatabaseException;
import com.devsuperior.dscalatog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscalatog.tests.Factory;

//Mock vs. MockBean (testes unitários x teste de integração)

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private  ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long validId;
	private long invalidId;
	private long dependentId;
	private Product product;
	private Category category;
	private PageImpl<Product> page;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		validId = 1L;
		invalidId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		productDTO = Factory.createProductDTO();
		
		doNothing().when(repository).deleteById(validId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(invalidId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		when(repository.findById(validId)).thenReturn(Optional.of(product));
		when(repository.findById(invalidId)).thenReturn(Optional.empty());
		
		when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		when(repository.getOne(validId)).thenReturn(product);
		when(repository.getOne(invalidId)).thenThrow(EntityNotFoundException.class);
		
		when(categoryRepository.getOne(validId)).thenReturn(category);
		when(categoryRepository.getOne(invalidId)).thenThrow(EntityNotFoundException.class);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(validId);
		});
		
		//Mockito.verify(repository, Mockito.never).deleteById(validId);
		verify(repository, times(1)).deleteById(validId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenDoesNotIdExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(invalidId);
		});
		
		verify(repository, times(1)).deleteById(invalidId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenIdExists() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		verify(repository, times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.findById(validId);
		
		Assertions.assertNotNull(result);
		verify(repository).findById(validId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(invalidId);
		});
		
		verify(repository).findById(invalidId);
	}
	
    @Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.update(validId, productDTO); 
		
		Assertions.assertNotNull(result);
		verify(repository).getOne(validId);
	}
    
    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
    	
    	Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(invalidId, productDTO);
		});
    	
    	verify(repository).getOne(invalidId);
    }
}
