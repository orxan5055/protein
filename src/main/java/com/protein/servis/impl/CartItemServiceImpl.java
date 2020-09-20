package com.protein.servis.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protein.domain.Protein;
import com.protein.domain.ProteinToCartItem;
import com.protein.domain.CartItem;
import com.protein.domain.Sifaris;
import com.protein.domain.ShoppingCart;
import com.protein.domain.User;
import com.protein.repository.ProteinToCartItemRepository;
import com.protein.repository.CartItemRepository;
import com.protein.servis.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProteinToCartItemRepository proteinToCartItemRepository;
	
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}
	
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = new BigDecimal(cartItem.getProtein().getBizimQiymet()).multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubtotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}
	
	public CartItem addProteinToCartItem(Protein protein, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		
		for (CartItem cartItem : cartItemList) {
			if(protein.getId() == cartItem.getProtein().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				cartItem.setSubtotal(new BigDecimal(protein.getBizimQiymet()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProtein(protein);
		
		cartItem.setQty(qty);
		cartItem.setSubtotal(new BigDecimal(protein.getBizimQiymet()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);
		
		ProteinToCartItem proteinToCartItem = new ProteinToCartItem();
		proteinToCartItem.setProtein(protein);
		proteinToCartItem.setCartItem(cartItem);
		proteinToCartItemRepository.save(proteinToCartItem);
		
		return cartItem;
	}
	
	public CartItem findById(Long id) {
		return cartItemRepository.findOne(id);
	}
	
	public void removeCartItem(CartItem cartItem) {
		proteinToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}
	
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	@Override
	public List<CartItem> findBySifaris(Sifaris sifaris) {
		return cartItemRepository.findBySifaris(sifaris);
	}

}
