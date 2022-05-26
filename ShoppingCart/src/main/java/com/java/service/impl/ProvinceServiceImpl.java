package com.java.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.entity.Province;
import com.java.repository.ProvinceRepository;
import com.java.service.ProvinceService;

@Service
@Scope("prototype")
public class ProvinceServiceImpl implements ProvinceService {

	private ProvinceRepository repo;

	public ProvinceServiceImpl(ProvinceRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public List<Province> getAll() {
		List<Province> provinces = repo.findAllReverse();
		return provinces;
	}

	@Override
	public List<Province> search(String key) {
		List<Province> provinces = repo.search(key);
		return provinces;
	}

	@Transactional
	@Modifying
	@Override
	public List<Province> delete(Long id) {
		repo.deleteById(id);
		return this.getAll();
	}

	@Transactional
	@Modifying
	@Override
	public Province save(Province entity) {
		Province province1 = repo.save(entity);
		return province1;
	}

	@Override
	public Province find(Long id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Modifying
	@Override
	public void saveAll(List<Province> entity) {
		repo.saveAll(entity);

	}

	@Transactional
	@Modifying
	@Override
	public void update(Province entity) {
		Province province = repo.findById(entity.getId()).get();

		province.setName(entity.getName());
		province.setType(entity.getType());

		repo.save(province);
	}

}
