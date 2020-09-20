package com.protein.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.protein.domain.ProteinToCartItem;
import com.protein.domain.CartItem;

@Transactional
public interface ProteinToCartItemRepository extends CrudRepository<ProteinToCartItem, Long>{
   void deleteByCartItem(CartItem cartItem);
}
