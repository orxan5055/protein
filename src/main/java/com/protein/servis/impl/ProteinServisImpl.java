package com.protein.servis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protein.domain.Protein;
import com.protein.repository.ProteinRepository;
import com.protein.servis.ProteinServis;

@Service
public class ProteinServisImpl implements ProteinServis {
	@Autowired
	private ProteinRepository proteinRepository;

	public List<Protein> findAll() {
		List<Protein> proteinList=(List<Protein>) proteinRepository.findAll();
		List<Protein> activeProteinList = new ArrayList<>();
		
		for (Protein protein : proteinList) {
			if (protein.isAktiv()) {
				activeProteinList.add(protein);
			}
		}
		return activeProteinList;
	}

	public Protein findOne(Long id) {
		return proteinRepository.findOne(id);
	}

	public List<Protein> findByKategoriya(String kategoriya) {
		List<Protein> proteinList = proteinRepository.findByKategoriya(kategoriya);

		List<Protein> activeProteinList = new ArrayList<>();

		for (Protein protein : proteinList) {
			if (protein.isAktiv()) {
				activeProteinList.add(protein);
			}
		}

		return activeProteinList;
	}

	@Override
	public List<Protein> Search(String basliq) {
		
		List<Protein> proteinList = proteinRepository.findByBasliqContaining(basliq);
	
	List<Protein> activeProteinList = new ArrayList<>();

	for (Protein protein : proteinList) {
		if (protein.isAktiv()) {
			activeProteinList.add(protein);
		}
	}

	return activeProteinList;
	}

	


}
