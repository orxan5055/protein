package com.protein.repository;

import org.springframework.data.repository.CrudRepository;

import com.protein.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
