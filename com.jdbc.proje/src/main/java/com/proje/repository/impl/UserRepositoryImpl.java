package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.model.Product;
import com.proje.model.User;
import com.proje.model.queries.UserQueries;
import com.proje.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

	private final Logger LOGGER = LogManager.getLogger();
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	@Override
	public User saveUser(User user) {
		
		connection = DBConnection.getConnection();
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.saveUserQuery);
			
			preparedStatement.setInt(1, user.getUserId());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setDate(4, user.getBirthOfDate());
			preparedStatement.setString(5, user.getUsername());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.warn(user.getUserId() + " id'li user kaydedilirken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return user;
	}

	@Override
	public boolean saveUserProduct(int userId, int productId) {
		connection = DBConnection.getConnection();
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.saveUser_ProductQuery);
			
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, productId);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.warn("user_product kaydedilirken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return true;
	}

	@Override
	public User updateUser(User user) {
		connection = DBConnection.getConnection();
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.updateUserQuery);
			
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setDate(3, user.getBirthOfDate());
			preparedStatement.setString(4, user.getUsername());
			preparedStatement.setInt(5, user.getUserId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.warn("User güncellenirken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return user;
	}

	@Override
	public boolean removeUser(int id) {
		connection = DBConnection.getConnection();
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.deleteUser_ProductByIdQuery);
			preparedStatement.setInt(1, id);	
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(UserQueries.deleteUserByIdQuery);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.warn("User silinirken hata meydana geldi HATA: " + e);
			return false;
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return true;
	}

	@Override
	public User findUserById(int id) {
		connection = DBConnection.getConnection();
		
		User user = null;
		try {
			preparedStatement = connection.prepareStatement(UserQueries.findUserByIdQuery);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				int userId = resultSet.getInt("userId");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				Date birthOfDate = resultSet.getDate("birthOfDate");
				String username = resultSet.getString("username");
				
				user = new User(userId, firstName, lastName, birthOfDate, username);
			}
			
		} catch (SQLException e) {
			LOGGER.warn("User bulunurken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
				
		return user;
	}

	@Override
	public User findUserProductById(int id) {
		connection = DBConnection.getConnection();
		
		User user = null;
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.findUserProductQuery);
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			boolean flag = true;
			
			List<Product> products = new ArrayList<Product>();
			
			while(resultSet.next()) {
				
				if(flag) {
					int userId = resultSet.getInt("userId");
					String firstName = resultSet.getString("firstName");
					String lastName = resultSet.getString("lastName");
					Date birthOfDate = resultSet.getDate("birthOfDate");
					String username = resultSet.getString("username");
					
					flag = false;
				}
				
				int productId = resultSet.getInt("productId");
				String productName = resultSet.getString("productName");
				double unitPrice = resultSet.getDouble("unitPrice");
				int avaible = resultSet.getInt("avaible");
				Date addDate = resultSet.getDate("addDate");
				Date updateDate = resultSet.getDate("updateDate");
				
				int categoryId = resultSet.getInt("categoryId");
				String categoryName = resultSet.getString("categoryName");
				
				int brandId = resultSet.getInt("brandId");
				String brandName = resultSet.getString("brandName");
				
				Category category = new Category(categoryId, categoryName);
				Brand brand = new Brand(brandId, brandName);
				Product product = new Product(productId, productName, unitPrice, avaible, addDate, updateDate, category, brand);
				
				products.add(product);
			}
			
			user.setProducts(products);
			
		} catch (SQLException e) {
			LOGGER.warn("User ve ürünleri bulunurken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return user;
	}

	@Override
	public List<User> findUsers() {
		connection = DBConnection.getConnection();
		
		List<User> users = new ArrayList<User>();
		
		try {
			preparedStatement = connection.prepareStatement(UserQueries.findUsersQuery);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int userId = resultSet.getInt("userId");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				Date birthOfDate = resultSet.getDate("birthOfDate");
				String username = resultSet.getString("username");
				
				User user = new User(userId, firstName, lastName, birthOfDate, username);
				
				users.add(user);
			}
		} catch (SQLException e) {
			LOGGER.warn("User listesi alınırken hata meydana geldi HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return users;
	}

}
