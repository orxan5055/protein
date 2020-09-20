package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.tehlukesizlik.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
