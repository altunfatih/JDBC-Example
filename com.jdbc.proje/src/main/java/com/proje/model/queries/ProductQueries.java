package com.proje.model.queries;

public class ProductQueries {
	
	public static final String saveProductQuery = "insert into product(productId, productName, unitPrice, avaible, addDate, updateDate, categoryId, brandId) values(?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String updateProductQuery = "productName = ?, unitPrice = ?, avaible = ?, updateDate = ?, categoryId = ?, brandId = ? where productId = ?";
	
	public static final String deleteUser_ProductQuery = "delete from user_product where productId = ?";
	public static final String deleteProductQuery = "delete from product where porductId = ?";
	
	public static final String findProductByIdQuery = "select * from product p left join category c on(p.categoryId = c.categoryId) left join brand b on(p.brandId = b.brandId) where productId = ?";
	
	public static final String findProductsQuery = "select * from product p left join category c on(p.categoryId = c.categoryId) left join brand b on(p.brandId = b.brandId) where productId = ?";
}
