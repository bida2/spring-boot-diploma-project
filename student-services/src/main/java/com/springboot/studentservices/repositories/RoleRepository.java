package com.springboot.studentservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.studentservices.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
