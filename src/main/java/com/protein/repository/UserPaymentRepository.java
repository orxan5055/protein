package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long>{
	default UserPayment findOne(Long id) { 
        return (UserPayment) findById(id).orElse(null); 
}
}