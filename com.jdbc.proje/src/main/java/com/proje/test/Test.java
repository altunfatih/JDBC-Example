package com.proje.test;

import com.proje.service.BrandService;
import com.proje.service.CategoryService;
import com.proje.service.ProductService;
import com.proje.service.UserService;
import com.proje.service.impl.BrandServiceImpl;
import com.proje.service.impl.CategoryServiceImpl;
import com.proje.service.impl.ProductServiceImpl;
import com.proje.service.impl.UserServiceImpl;

public class Test {

	public static void main(String[] args) {
		
		BrandService brandService = new BrandServiceImpl();
		CategoryService categoryService = new CategoryServiceImpl();
		UserService userService = new UserServiceImpl();
		ProductService productService = new ProductServiceImpl();
				
		//...
	}

}
