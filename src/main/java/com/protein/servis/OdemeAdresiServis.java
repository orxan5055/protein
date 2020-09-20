package com.protein.servis;


import com.protein.domain.OdemeAdresi;
import com.protein.domain.UserBilling;

public interface OdemeAdresiServis {
	OdemeAdresi setByUserBilling(UserBilling userBilling, OdemeAdresi odemeAdresi);
}
