package com.devsuperior.dscalatog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscalatog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
