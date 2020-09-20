package com.protein.servis;

import com.protein.domain.OdemeAdresi;
import com.protein.domain.Sifaris;
import com.protein.domain.Payment;
import com.protein.domain.CatdirilmaAdresi;
import com.protein.domain.ShoppingCart;
import com.protein.domain.User;

public interface SifarisServis {
	Sifaris createOrder(ShoppingCart shoppingCart,
			CatdirilmaAdresi catdirilmaAdresi,
			OdemeAdresi odemeAdresi,
			Payment payment,
			String catdirilmaMetodu,
			User user);
	
	Sifaris findOne(Long id);
}

