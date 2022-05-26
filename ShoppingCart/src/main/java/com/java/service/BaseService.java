package com.java.service;

import java.util.List;

//T : return
//E : dto
//K : id
public interface BaseService <T, E, K>{
	List<E> findAll() ;
	E findById(K id) ;
	T save(E dto)  ; 
	T delete (K id) throws Exception ; 
}
