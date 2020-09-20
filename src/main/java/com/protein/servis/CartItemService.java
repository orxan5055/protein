package com.protein.servis;

import java.util.List;

import com.protein.domain.Protein;
import com.protein.domain.CartItem;
import com.protein.domain.Sifaris;
import com.protein.domain.ShoppingCart;
import com.protein.domain.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addProteinToCartItem(Protein protein, User user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	List<CartItem> findBySifaris(Sifaris sifaris);
}
