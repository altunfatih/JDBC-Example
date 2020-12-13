package com.proje.model.queries;

public class CategoryQueries {
	
	public static final String findCategoryByIdQuery = "select * from category where categoryId = ?";
	
	public static final String findCategoriesQuery = "select * from category";
	
	
}
