package com.devsuperior.dscalatog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscalatog.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
