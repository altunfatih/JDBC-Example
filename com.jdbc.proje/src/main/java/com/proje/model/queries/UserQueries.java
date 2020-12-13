package com.proje.model.queries;

public class UserQueries {
	
	public static final String saveUserQuery = "insert into user(userId, firstName, lastName, birthOfDate, username) values(?, ?, ?, ?, ?)";
	
	public static final String saveUser_ProductQuery = "insert into user_product(userId, productId) values(?, ?)";
	
	public static final String updateUserQuery = "update user set firstName = ?, lastName = ?, birthOfDate = ?, username = ? where userId = ?";
	
	public static final String deleteUser_ProductByIdQuery = "delete from user_product where userId = ?";
	public static final String deleteUserByIdQuery = "delete from user where uderId = ?";
	
	public static final String findUserByIdQuery = "select * from user where userId = ?";
	
	public static final String findUsersQuery = "select * from user";
	
	public static final String findUserProductQuery = "select * from user u" + 
													" left outer join user_product up on(u.userId = up.userId)" + 
													" left join product p on(up.productId = p.productId)" + 
													" left join category c on(p.categoryId = c.categoryId)" + 
													" left join brand b on(p.brandId = b.brandId) where u.userId = ?";
}
