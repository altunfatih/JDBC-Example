package com.proje.service;

import java.util.List;

import com.proje.model.Category;

public interface CategoryService {
	
	Category findCategoryById(int id);
	
	List<Category> findCategories();
}
