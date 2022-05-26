package com.java.service;

import java.util.List;

//T : return
//E : entity
//K : id

public interface BaseUnitService <T, E, K>{
	List<T> getAll();
	List<T> search(String key);
	List<T> delete(K id);
	T save(E entity);
	T find(K id);
	void saveAll(List<E> entity);
	void update(E entity);
}
