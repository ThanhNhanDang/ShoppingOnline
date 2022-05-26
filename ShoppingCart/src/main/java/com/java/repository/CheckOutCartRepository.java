package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.dto.CheckOutCartDto;
import com.java.entity.CheckOutCart;

@Repository
public interface CheckOutCartRepository extends JpaRepository<CheckOutCart, Long>{
	@Query("select new com.java.dto.CheckOutCartDto(checkOut.id, checkOut.productId, checkOut.quantity, checkOut.price, checkOut.orderDate, checkOut.userId, checkOut.orderId, checkOut.paymentType, checkOut.deliveryAddress, u.name, p.name, p.price) from CheckOutCart checkOut, User u, Products p WHERE checkOut.userId = :userId and checkOut.user=u and checkOut.products=p")
	List<CheckOutCartDto> getCheckOutCartByUserId (@Param("userId") long userId);
	
	@Query("select new com.java.dto.CheckOutCartDto(checkOut.id, checkOut.productId, checkOut.quantity, checkOut.price, checkOut.orderDate, checkOut.userId, checkOut.orderId, checkOut.paymentType, checkOut.deliveryAddress, u.name, p.name, p.price) from CheckOutCart checkOut, User u, Products p WHERE checkOut.user=u and checkOut.products=p")
	List<CheckOutCartDto> getAll ();

}
