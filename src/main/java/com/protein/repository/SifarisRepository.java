package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.Sifaris;

public interface SifarisRepository extends CrudRepository<Sifaris, Long>{
	default Sifaris findOne(Long id) { 
        return (Sifaris) findById(id).orElse(null); 
}
}