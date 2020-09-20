package com.protein.servis.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protein.domain.OdemeAdresi;
import com.protein.domain.Protein;
import com.protein.domain.CartItem;
import com.protein.domain.Sifaris;
import com.protein.domain.Payment;
import com.protein.domain.CatdirilmaAdresi;
import com.protein.domain.ShoppingCart;
import com.protein.domain.User;
import com.protein.repository.SifarisRepository;
import com.protein.servis.CartItemService;
import com.protein.servis.SifarisServis;

@Service
public class SifarisServisImpl implements SifarisServis{
	
	@Autowired
	private SifarisRepository sifarisRepository;
	
	@Autowired
	private CartItemService cartItemService;
	
	public synchronized Sifaris createOrder(ShoppingCart shoppingCart,
			CatdirilmaAdresi catdirilmaAdresi,
			OdemeAdresi odemeAdresi,
			Payment payment,
			String catdirilmaMetodu,
			User user) {
		Sifaris sifaris = new Sifaris();
		sifaris.setOdemeAdresi(odemeAdresi);
		sifaris.setSifarisDurumu("created");
		sifaris.setPayment(payment);
		sifaris.setCatdirilmaAdresi(catdirilmaAdresi);
		sifaris.setCatdirilmaMetodu(catdirilmaMetodu);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Protein protein = cartItem.getProtein();
			cartItem.setSifaris(sifaris);
			protein.setStokdakiSay(protein.getStokdakiSay() - cartItem.getQty());
		}
		
		sifaris.setCartItemList(cartItemList);
		sifaris.setSifarisVaxti(Calendar.getInstance().getTime());
		sifaris.setSifarisCemi(shoppingCart.getGrandTotal());
		catdirilmaAdresi.setSifaris(sifaris);
		odemeAdresi.setSifaris(sifaris);
		payment.setSifaris(sifaris);
		sifaris.setUser(user);
		sifaris = sifarisRepository.save(sifaris);
		
		return sifaris;
	}
	
	public Sifaris findOne(Long id) {
		return sifarisRepository.findOne(id);
	}

}
