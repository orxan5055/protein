package com.protein.servis.impl;

import org.springframework.stereotype.Service;

import com.protein.domain.CatdirilmaAdresi;
import com.protein.domain.UserShipping;
import com.protein.servis.CatdirilmaAdresiServis;

@Service
public class CatdirilmaAdresiServisImpl implements CatdirilmaAdresiServis {
	public CatdirilmaAdresi setByUserShipping(UserShipping userShipping, CatdirilmaAdresi shippingAddress) {
		shippingAddress.setCatdirilmaAdresiAdi(userShipping.getUserShippingName());
		shippingAddress.setCatdirilmaAdresiKuce1(userShipping.getUserShippingStreet1());
		shippingAddress.setCatdirilmaAdresiKuce2(userShipping.getUserShippingStreet2());
		shippingAddress.setCatdirilmaAdresiSeher(userShipping.getUserShippingCity());
		shippingAddress.setCatdirilmaAdresiRegion(userShipping.getUserShippingState());
		shippingAddress.setCatdirilmaAdresiOlke(userShipping.getUserShippingCountry());
		shippingAddress.setCatdirilmaAdresiZipkodu(userShipping.getUserShippingZipcode());
		
		return shippingAddress;
	}
}
