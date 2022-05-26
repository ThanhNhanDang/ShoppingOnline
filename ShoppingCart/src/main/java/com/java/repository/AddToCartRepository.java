package com.java.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.AddToCartDto;
import com.java.entity.AddToCart;


@Repository
public interface AddToCartRepository extends JpaRepository<AddToCart, Long>{
	@Query("select sum(cart.price) FROM AddToCart cart WHERE cart.user_id = :user_id")
	double getTotalPriceByUserId (@Param("user_id") long user_id);
	
	@Query("select new com.java.dto.AddToCartDto(cart.id,cart.product_id,cart.quantity, cart.price, cart.added_date, cart.user_id, u.name, p.name, p.price, p.urlImg) from AddToCart cart, User u, Products p WHERE cart.user_id = :user_id and cart.user=u and cart.products=p")
	List<AddToCartDto> getCartByUserId (@Param("user_id") long user_id);

	@Query("select new com.java.dto.AddToCartDto(cart.id,cart.product_id,cart.quantity, cart.price, cart.added_date, cart.user_id, u.name, p.name, p.price,  p.urlImg) from AddToCart cart, User u, Products p WHERE cart.id = :id and cart.user_id = :userId and cart.user=u and cart.products=p")
	AddToCartDto getCartByCartId (@Param("userId")long userId, @Param("id") long id);
	@Query("SELECT cart FROM AddToCart cart WHERE cart.product_id = :product_id and cart.user_id = :user_id")
	Optional<AddToCart> getCartByProductIdAndUserId (@Param("user_id") long user_id, @Param("product_id") long product_id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM AddToCart cart WHERE cart.user_id = :user_id")
	void deleteAllCartByUserId(@Param("user_id")long user_id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM AddToCart cart WHERE cart.id = :id and cart.user_id = :user_id")
	void deleteCartByIdAndUserId(@Param("id")long id, @Param("user_id")long user_id);
	
//	@Modifying
//	@Transactional
//	@Query("UPDATE AddToCart cart SET cart.quantity = :quantity, cart.price = :price WHERE cart.id = :id")
//	void updateQtyByCartId(@Param("id")long id, @Param("price") double price, @Param("quantity")int quantity); 
}
