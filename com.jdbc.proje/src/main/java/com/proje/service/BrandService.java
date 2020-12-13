package com.proje.service;

import java.util.List;

import com.proje.model.Brand;

public interface BrandService {
	
	Brand findBrandById(int id);
	
	List<Brand> findBrands();
	
}
