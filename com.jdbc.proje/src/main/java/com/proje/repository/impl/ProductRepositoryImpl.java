package com.proje.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.proje.connection.DBConnection;
import com.proje.model.Brand;
import com.proje.model.Category;
import com.proje.model.Product;
import com.proje.model.queries.ProductQueries;
import com.proje.repository.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {

	private final Logger LOGGER = LogManager.getLogger();
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	//insert into product(productId, productName, unitPrice, avaible, addDate, updateDate, categoryId, brandId) values(?, ?, ?, ?, ?, ?, ?, ?)
	@Override
	public Product saveProduct(Product product) {
		connection = DBConnection.getConnection();
		
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			
			preparedStatement = connection.prepareStatement(ProductQueries.saveProductQuery);
			
			preparedStatement.setInt(1, product.getProductId());
			preparedStatement.setString(2, product.getProductName());
			preparedStatement.setDouble(3, product.getUnitPrice());
			preparedStatement.setInt(4, product.getAvaible());
			preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
			preparedStatement.setTimestamp(6, null);
			preparedStatement.setInt(7, product.getCategory().getCategoryId());
			preparedStatement.setInt(8, product.getBrand().getBrandId());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.warn("Ürün eklenirken hata oluştu. HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return product;
	}
	
	@Override
	public boolean saveBatchProduct(List<Product> products) {
		connection = DBConnection.getConnection();
		
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			
			preparedStatement = connection.prepareStatement(ProductQueries.saveProductQuery);
			
			for(Product product : products) {
				preparedStatement.setInt(1, product.getProductId());
				preparedStatement.setString(2, product.getProductName());
				preparedStatement.setDouble(3, product.getUnitPrice());
				preparedStatement.setInt(4, product.getAvaible());
				preparedStatement.setTimestamp(5, Timestamp.valueOf(localDateTime));
				preparedStatement.setTimestamp(6, null);
				preparedStatement.setInt(7, product.getCategory().getCategoryId());
				preparedStatement.setInt(8, product.getBrand().getBrandId());
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.warn("Ürün listesi eklenirken hata oluştu. HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return true;
	}
	
	@Override
	public Product updateProduct(Product product) {
		connection = DBConnection.getConnection();
		
		try {
			LocalDateTime localDateTime = LocalDateTime.now();
			
			preparedStatement = connection.prepareStatement(ProductQueries.updateProductQuery);
			
			preparedStatement.setString(1, product.getProductName());
			preparedStatement.setDouble(2, product.getUnitPrice());
			preparedStatement.setInt(3, product.getAvaible());
			preparedStatement.setTimestamp(4, Timestamp.valueOf(localDateTime));
			preparedStatement.setInt(5, product.getCategory().getCategoryId());
			preparedStatement.setInt(6, product.getBrand().getBrandId());
			preparedStatement.setInt(7, product.getProductId());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.warn("Ürün güncellenirken hata oluştu. HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return product;
	}
	
	@Override
	public boolean removeProduct(int productId) {
		connection = DBConnection.getConnection();
		
		try {
			preparedStatement = connection.prepareStatement(ProductQueries.deleteUser_ProductQuery);
			preparedStatement.setInt(1, productId);
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(ProductQueries.deleteProductQuery);
			preparedStatement.setInt(1, productId);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.warn("Ürün silinirken hata oluştu. HATA: " + e);
			return false;
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return true;
	}
	
	@Override
	public Product findProductById(int id) {
		connection = DBConnection.getConnection();
		
		Product product = null;
		
		try {
			
			preparedStatement = connection.prepareStatement(ProductQueries.findProductByIdQuery);
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
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
				
				product = new Product(productId, productName, unitPrice, avaible, addDate, updateDate, category, brand);
 			}
			
		} catch (SQLException e) {
			LOGGER.warn(id + " id'li ürün aranırken hata oluştu. HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return product;
	}
	
	@Override
	public List<Product> findProducts() {
		connection = DBConnection.getConnection();
		
		List<Product> products = new ArrayList<Product>();
		
		try {
			
			preparedStatement = connection.prepareStatement(ProductQueries.findProductsQuery);
			
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
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
			
		} catch (SQLException e) {
			LOGGER.warn("Ürün listelenirkenn hata oluştu. HATA: " + e);
		} finally {
			DBConnection.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return products;
	}
	
}
