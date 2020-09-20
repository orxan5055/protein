package com.protein.servis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.protein.domain.Protein;

@Service
public interface ProteinServis {
	List<Protein> findAll();
	
	Protein findOne(Long id);
    List<Protein> findByKategoriya(String kategoriya);

	List<Protein> Search(String basliq);
	
	
}
