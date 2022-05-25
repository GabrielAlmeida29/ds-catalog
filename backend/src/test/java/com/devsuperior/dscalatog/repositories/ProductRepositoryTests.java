package com.devsuperior.dscalatog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscalatog.entities.Product;
import com.devsuperior.dscalatog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	
	private long invalidId = 1000L;
	private long validId = 1L;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		invalidId = 1000L;
		validId = 1L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void deleteShouldDeletObjectWhenIdExists() {
		
		repository.deleteById(validId);
		
		Optional<Product> result = repository.findById(validId);
		Assertions.assertFalse(result.isPresent());	
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(invalidId);
		});
	}
	
	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
		
		Optional<Product> result = repository.findById(validId);
		
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
		
		Optional <Product> result = repository.findById(invalidId);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
}