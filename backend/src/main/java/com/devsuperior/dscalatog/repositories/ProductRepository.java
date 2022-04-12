package com.devsuperior.dscalatog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscalatog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
