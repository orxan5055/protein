package com.protein.servis.impl;

import org.springframework.stereotype.Service;

import com.protein.domain.OdemeAdresi;
import com.protein.domain.UserBilling;
import com.protein.servis.OdemeAdresiServis;

@Service
public class OdemeAdresiServisImpl implements OdemeAdresiServis{
	
	public OdemeAdresi setByUserBilling(UserBilling userBilling, OdemeAdresi odemeAdresi) {
		odemeAdresi.setOdemeAdresiAdi(userBilling.getUserBillingName());
		odemeAdresi.setOdemeAdresiKuce1(userBilling.getUserBillingStreet1());
		odemeAdresi.setOdemeAdresiKuce2(userBilling.getUserBillingStreet2());
		odemeAdresi.setOdemeAdresiSeher(userBilling.getUserBillingCity());
		odemeAdresi.setOdemeAdresiRegion(userBilling.getUserBillingState());
		odemeAdresi.setOdemeAdresiOlke(userBilling.getUserBillingCountry());
		odemeAdresi.setOdemeAdresiZipkodu(userBilling.getUserBillingZipcode());
		
		return odemeAdresi;
	}

}
