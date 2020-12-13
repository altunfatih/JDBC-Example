package com.proje.service.impl;

import java.util.List;

import com.proje.model.Category;
import com.proje.repository.CategoryRepository;
import com.proje.repository.impl.CategoryRepositoryImpl;
import com.proje.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{
	
	private CategoryRepository categoryRepository = new CategoryRepositoryImpl();

	@Override
	public Category findCategoryById(int id) {
		return categoryRepository.findCategoryById(id);
	}

	@Override
	public List<Category> findCategories() {
		return categoryRepository.findCategories();
	}

}
