package com.proje.model.queries;

public class BrandQueries {
	
	public static final String findBrandByIdQuery = "select * from brand where brandId = ?";
	
	public static final String findBrandsQuery = "select * from brand";
	
}
