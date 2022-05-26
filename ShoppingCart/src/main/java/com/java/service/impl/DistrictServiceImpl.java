package com.java.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.java.entity.District;
import com.java.repository.DistrictRepository;
import com.java.service.DistrictService;

@Service
@Scope("prototype")
public class DistrictServiceImpl implements DistrictService{
	private DistrictRepository repo;

	public DistrictServiceImpl(DistrictRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public List<District> findDistrictByProvinceId(long provinceId) {
		List<District> districts = repo.findDistrictByProvinceId(provinceId);
		return districts;
	}

	@Override
	public List<District> getAll() {
		List<District> districts = repo.findAllReverse();
		return districts;
	}

	@Override
	public List<District> search(String key){
		List<District> districts = repo.search(key);
		return districts;
	}

	@Transactional
	@Modifying
	@Override
	public List<District> delete(Long id) {
		repo.deleteById(id);
		return this.getAll();
	}

	@Transactional
	@Modifying
	@Override
	public District save(District entity) {
		District district = repo.save(entity);
		return district;
	}

	@Override
	public District find(Long id) {
		return repo.findById(id).get();
	}

	@Transactional
	@Modifying
	@Override
	public void saveAll(List<District> entity) {
		repo.saveAll(entity);

	}

	@Transactional
	@Modifying
	@Override
	public void update(District entity) {
		District district = repo.findById(entity.getId()).get();

		district.setName(entity.getName());
		district.setType(entity.getType());
		district.setProvinceId(entity.getProvinceId());
		repo.save(district);
	}

}
