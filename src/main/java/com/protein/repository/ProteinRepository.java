package com.protein.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.protein.domain.Protein;

public interface ProteinRepository extends CrudRepository<Protein, Long> {
	
	List<Protein> findByKategoriya(String kategoriya);
	
	List<Protein> findByBasliqContaining(String basliq);

	default Protein findOne(Long id) {
		return (Protein) findById(id).orElse(null);

	}

}