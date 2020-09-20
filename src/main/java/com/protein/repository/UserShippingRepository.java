package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.UserShipping;

public interface UserShippingRepository extends CrudRepository<UserShipping, Long> {
	
	default UserShipping findOne(Long id) { 
        return (UserShipping) findById(id).orElse(null); 

}
}