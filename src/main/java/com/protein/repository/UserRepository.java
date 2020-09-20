package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.User;
import com.protein.domain.UserPayment;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
	default User findOne(Long id) { 
        return (User) findById(id).orElse(null); }
}
