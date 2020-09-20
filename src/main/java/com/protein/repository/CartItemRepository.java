package com.protein.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.protein.domain.CartItem;
import com.protein.domain.Sifaris;
import com.protein.domain.ShoppingCart;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long>{
	default CartItem findOne(Long id) { 
        return (CartItem) findById(id).orElse(null); }
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	List<CartItem> findBySifaris(Sifaris sifaris);
	
}
